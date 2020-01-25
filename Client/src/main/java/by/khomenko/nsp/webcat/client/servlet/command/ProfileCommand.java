package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.OrderDao;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.entity.Order;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ProfileCommand.class);

    private Map<String, Object> load(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             OrderDao orderDao = DaoFactory.getInstance().createDao(OrderDao.class);
             CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class)) {

            Customer customer = customerDao.read(customerId);
            List<Order> customerOrdersList = orderDao.readOrdersByCustomerId(customerId);
            Cart customerCart = cartDao.readCartByCustomerId(customerId);

            map.put("customer", customer);
            map.put("customerOrdersList", customerOrdersList);
            map.put("customerCart", customerCart);

        } catch (Exception e) {
            LOGGER.error("Loading profile page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> profileMap = load((Integer)request.getSession().getAttribute("customerId"));
            for (String key : profileMap.keySet()) {
                request.setAttribute(key, profileMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/profile.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in ProfileCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}