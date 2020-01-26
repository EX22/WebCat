package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.OrderDao;
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

public class OrderStatusCommand implements BaseCommand {

    private static final Logger LOGGER
            = LogManager.getLogger(OrderStatusCommand.class);

    private Map<String, Object> loadOrders(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();


        try (OrderDao orderDao = DaoFactory.getInstance().createDao(OrderDao.class)) {

            List<Order> customerOrdersList = orderDao.readOrdersByCustomerId(customerId);

            map.put("customerOrdersList", customerOrdersList);

        } catch (Exception e) {
            LOGGER.error("Loading order status profile page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> ordersMap = loadOrders((Integer)request.getSession().getAttribute("customerId"));
            for (String key : ordersMap.keySet()) {
                request.setAttribute(key, ordersMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/orderstatus.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in OrderStatusCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
