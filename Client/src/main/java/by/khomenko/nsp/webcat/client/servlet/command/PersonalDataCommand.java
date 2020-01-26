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
import java.util.Map;

public class PersonalDataCommand implements BaseCommand {

    private static final Logger LOGGER
            = LogManager.getLogger(PersonalDataCommand.class);

    private Map<String, Object> loadPersonalData(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            Customer customer = customerDao.read(customerId);

            map.put("customer", customer);

        } catch (Exception e) {
            LOGGER.error("Loading personal data profile page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> personalDataMap
                    = loadPersonalData((Integer)request.getSession().getAttribute("customerId"));
            for (String key : personalDataMap.keySet()) {
                request.setAttribute(key, personalDataMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/personaldata.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in PersonalDataCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
