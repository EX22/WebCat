package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.ContactsDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.entity.Contacts;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.common.exception.ValidationException;
import by.khomenko.nsp.webcat.common.service.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CheckOutCommand.class);

    private Map<String, Object> load(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class);
             CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class)) {

            Cart customerCart = cartDao.readCartByCustomerId(customerId);
            Customer customer = customerDao.read(customerId);
            Contacts contacts = contactsDao.read(customerId);


            map.put("customerCart", customerCart);
            map.put("customer", customer);
            map.put("contacts", contacts);

        } catch (Exception e) {
            LOGGER.error("Loading checkout page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    public void createNewCustomerAccount(String customerLogin)
            throws ValidationException, PersistentException {

        RegistrationCommand registrationCommand = new RegistrationCommand();
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        String randomlyGeneratedNewCustomerPassword = passwordGenerator.generateRandomPassword(15);

        registrationCommand.createCustomer(customerLogin, randomlyGeneratedNewCustomerPassword,
                randomlyGeneratedNewCustomerPassword);

    }

    public void setCustomerContacts(Integer currentCustomerId, String customerFirstName,
                                       String customerLastName, String customerPhone,
                                       String customerAddress, String customerCountry,
                                       String customerState, String customerZipCode)
            throws PersistentException {


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class)) {

            if ((customerFirstName != null) && (!"".equals(customerFirstName))) {

                customerDao.updateCustomerName(currentCustomerId, customerFirstName);
            }





        } catch (Exception e) {
            LOGGER.error("Setting customer's contacts in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String customerFirstName = request.getParameter("customerFirstName");
            String customerLastName = request.getParameter("customerLastName");
            String customerEmail = request.getParameter("customerEmail");
            String customerPhone = request.getParameter("customerPhone");
            String customerAddress = request.getParameter("customerAddress");
            String customerCountry = request.getParameter("customerCountry");
            String customerState = request.getParameter("customerState");
            String customerZipCode = request.getParameter("customerZipCode");

            setCustomerContacts(customerId, customerFirstName, customerLastName, customerPhone,
                    customerAddress, customerCountry, customerState, customerZipCode);

            //TODO Send login and password to customer by email.
            if (customerEmail != null) {
                createNewCustomerAccount(customerEmail);
            }

            Object customerIdObj = request.getSession().getAttribute("customerId");
            Map<String, Object> checkOutMap = new HashMap<>();

            if (customerIdObj != null) {
                checkOutMap = load((Integer)customerIdObj);
            }

            for (String key : checkOutMap.keySet()) {
                request.setAttribute(key, checkOutMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
