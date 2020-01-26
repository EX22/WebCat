package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Cart;
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
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CartCommand.class);

    private Map<String, Object> load(Cart cart) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

            map.put("cart", cart);

        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            List<Product> products = productDao.readProductsById(cart.getProducts().keySet());

            map.put("products", products);

        } catch (Exception e) {
            LOGGER.error("Loading cart page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Object cartObj = request.getSession().getAttribute("cart");
            Cart cart = null;

            if (cartObj != null){
                cart = (Cart)cartObj;
            }

            String productId = request.getParameter("id");

            if(productId != null){

                Integer pId = Integer.parseInt(productId);

                 if (cart == null){
                    cart = new Cart();
                    request.getSession().setAttribute("cart", cart);
                }

                cart.addProduct(pId);

                //TODO Cart's item quantity
                response.getWriter().print("100");
                return;
            }

            Map<String, Object> cartMap = null;

            if (cart == null){

                Object customerIdObj = request.getSession().getAttribute("customerId");

                if (customerIdObj != null){

                    cart = new Cart();
                    cart.setCustomerId((Integer)customerIdObj);
                    request.getSession().setAttribute("cart", cart);

                } else {

                    cart = new Cart();
                    request.getSession().setAttribute("cart", cart);

                }

            }

            cartMap = load(cart);

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
