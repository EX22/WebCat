package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.ContactsDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.entity.Contacts;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShowCheckOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ShowCheckOutCommand.class);

    private Map<String, Object> load(Integer customerId, Integer productId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class);
             CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            Cart customerCart = cartDao.readCartByCustomerId(customerId);
            Customer customer = customerDao.read(customerId);

            if (customerCart == null){
                customerCart = new Cart(customerId);
            }

            customerCart.addProduct(productId);
            cartDao.update(customerCart);

            map.put("customerCart", customerCart);
            map.put("customer", customer);

        } catch (Exception e) {
            LOGGER.error("Loading checkout page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String  productId = request.getParameter("productId");

            Object customerIdObj = request.getSession().getAttribute("customerId");
            Map<String, Object> checkOutMap = new HashMap<>();

            if (customerIdObj != null) {
                checkOutMap = load((Integer)customerIdObj, Integer.parseInt(productId));
            }

            for (String key : checkOutMap.keySet()) {
                request.setAttribute(key, checkOutMap.get(key));
            }


            request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp")
                    .forward(request, response);

        } catch (ServletException | PersistentException e) {
            LOGGER.error("An exception in execute method in ShowCheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
