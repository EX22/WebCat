package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.ContactsDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Contacts;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.common.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactsCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ContactsCommand.class);


    private Map<String, Object> loadCustomerContacts(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class)) {

            Contacts contacts = contactsDao.read(customerId);

            map.put("contacts", contacts);

        } catch (Exception e) {
            LOGGER.error("Loading customer's contacts in ContactsCommand class an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    public void updateCustomerContacts(Integer currentCustomerId,
                                       String newCustomerLastName, String newCustomerPhone,
                                       String newCustomerAddress, String newCustomerCountry,
                                       String newCustomerState, String newCustomerZipCode)
            throws PersistentException, ValidationException {


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {




        } catch (Exception e) {
            LOGGER.error("Updating customer's contacts in ContactsCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String newCustomerLastName = request.getParameter("customerLastName");
            String newCustomerPhone = request.getParameter("customerPhone");
            String newCustomerAddress = request.getParameter("customerAddress");
            String newCustomerCountry = request.getParameter("customerCountry");
            String newCustomerState = request.getParameter("customerState");
            String newCustomerZipCode = request.getParameter("customerZipCode");
            String newCustomerEmail = request.getParameter("customerEmail");


            updateCustomerContacts(customerId, newCustomerLastName, newCustomerPhone,
                    newCustomerAddress, newCustomerCountry, newCustomerState, newCustomerZipCode);

            Map<String, Object> profileMap = loadCustomerContacts(customerId);
            for (String key : profileMap.keySet()) {
                request.setAttribute(key, profileMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/settings.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in ContactsCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
