package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartContentDao;
import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.entity.CartContent;
import by.khomenko.nsp.webcat.common.entity.Product;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */

    private static final Logger LOGGER
            = LogManager.getLogger(CartCommand.class);

    private Map<String, Object> load(CartContent cartContent) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            List<Product> products = productDao.readProductsById(cartContent.getProducts().keySet());
            cartContent.setProductInfo(products);

            map.put("cartContent", cartContent);

        } catch (Exception e) {
            LOGGER.error("Loading cart page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    private <T>T getObjectFromSession(Class<T> cartContentClass,
                                      String attributeName, HttpServletRequest request) {

        Object cartObj = request.getSession().getAttribute(attributeName);
        T result = null;

        if (cartObj != null){
            result = (T) cartObj;
        }
        return result;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (CartContentDao cartContentDao = DaoFactory.getInstance().createDao(CartContentDao.class)){

            CartContent cartContent = getObjectFromSession(CartContent.class,
                    "cartContent", request);

            Integer customerId = getObjectFromSession(Integer.class,
                    "customerId", request);

            if (cartContent == null){

                if (customerId == null){
                    cartContent = new CartContent();
                } else {
                    cartContent = cartContentDao.read(customerId);
                }

                request.getSession().setAttribute("cartContent", cartContent);

            }

            String productId = request.getParameter("id");

            if(productId != null){

                Integer pId = Integer.parseInt(productId);

                cartContent.addProduct(pId);
                cartContentDao.update(cartContent);

                response.getWriter().print(cartContent.getProducts().entrySet().size());
                return;

            }
            Map<String, Object> cartMap = load(cartContent);

            for (String key : cartMap.keySet()) {
                request.setAttribute(key, cartMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/cart.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CartCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }



}
