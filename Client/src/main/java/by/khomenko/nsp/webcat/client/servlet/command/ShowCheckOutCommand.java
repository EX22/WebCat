package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.*;
import by.khomenko.nsp.webcat.common.entity.*;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowCheckOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ShowCheckOutCommand.class);

    private Map<String, Object> load(Customer customer, CartContent cartContent) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            List<Product> products = productDao.readProductsById(cartContent.getProducts().keySet());
            cartContent.setProductInfo(products);

            map.put("cartContent", cartContent);
            map.put("customer", customer);

        } catch (Exception e) {
            LOGGER.error("Loading checkout page an exception occurred.", e);
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

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             CartContentDao cartContentDao = DaoFactory.getInstance().createDao(CartContentDao.class)){

            CartContent cartContent = getObjectFromSession(CartContent.class,
                    "cartContent", request);
            Integer customerId = getObjectFromSession(Integer.class,
                    "customerId", request);
            String  productId = request.getParameter("productId");
            Customer customer = null;

            if (customerId != null) {
                customer = customerDao.read(customerId);
                request.getSession().setAttribute("customer", customer);
            }
            if (cartContent == null){
                if (customerId == null){
                    cartContent = new CartContent();
                } else {
                    cartContent = cartContentDao.read(customerId);
                }
                request.getSession().setAttribute("cartContent", cartContent);
            }
            if (productId != null){
                cartContent.addProduct(Integer.parseInt(productId));
                cartContentDao.update(cartContent);
            }
            if ((customerId != null) && (productId != null)) {
                cartContent.setCustomerId(customerId);
            }
            if (!cartContent.getProducts().isEmpty()) {

                Map<String, Object> checkOutMap = load(customer, cartContent);

                for (String key : checkOutMap.keySet()) {
                    request.setAttribute(key, checkOutMap.get(key));
                }

                request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp")
                        .forward(request, response);
            } else {
                response.sendRedirect("cart.html");
            }

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in ShowCheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
