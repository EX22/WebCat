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

public class SettingsCommand implements BaseCommand {

    private static final Logger LOGGER
            = LogManager.getLogger(SettingsCommand.class);

    private Map<String, Object> loadProfileSettings(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            Customer customer = customerDao.read(customerId);

            map.put("customer", customer);

        } catch (Exception e) {
            LOGGER.error("Loading settings profile page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    public void updateProfileSettings(Integer currentCustomerId, String customerName,
                                      String currentPass, String newPass, String confirmPass,
                                      String customerLastName, String customerEmail, String customerPhone,
                                      String customerAddress, String customerCountry, String customerState,
                                      String customerZipCode, String contactsIdToDelete)
            throws PersistentException, ValidationException {

        Customer loggedCustomer;

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class)) {

            if (isAllNotEmpty(customerName,customerLastName,customerPhone)) {

                customerDao.updateCustomerNameLastNamePhone(currentCustomerId,
                        customerName, customerLastName, customerPhone);
            }
            if (isAllNotEmpty(customerAddress, customerCountry,
                    customerState, customerZipCode)) {

                Contacts contacts = new Contacts(currentCustomerId,
                        customerAddress, customerCountry, customerState, customerZipCode);
                contactsDao.create(contacts);
            }
            if (isAllNotEmpty(contactsIdToDelete)){
                contactsDao.delete(Integer.parseInt(contactsIdToDelete));
            }
            if (isAllNotEmpty(newPass)) {
                if (newPass.equals(confirmPass)) {

                    loggedCustomer = customerDao.read(customerDao.read(currentCustomerId)
                            .getLogin(), currentPass);

                    if (loggedCustomer != null) {
                        customerDao.updateCustomerPass(currentCustomerId, newPass);
                    } else {
                        throw new ValidationException("Current password is"
                                + " false.");
                    }
                } else {
                    throw new ValidationException("Password not equals to"
                            + " confirm password.");
                }
            }
        } catch (ValidationException e) {
            LOGGER.error("Updating customer's profile settings an "
                    + "exception occurred.", e);
            throw new ValidationException(e);
        } catch (Exception e) {
            LOGGER.error("Updating customer's profile settings an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }

    }

    public static boolean isAllNotEmpty(String... customerData) {

        for(String oneFieldOfData : customerData) {
            if (oneFieldOfData  == null || "".equals(oneFieldOfData .trim())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String customerFirstName = request.getParameter("customerFirstName");
            String customerCurrentPass = request.getParameter("customerCurrentPassword");
            String customerNewPass = request.getParameter("customerNewPassword");
            String customerConfirmPass = request.getParameter("customerConfirmNewPassword");
            String customerLastName = request.getParameter("customerLastName");
            String customerEmail = request.getParameter("customerEmail");
            String customerPhone = request.getParameter("customerPhone");
            String customerAddress = request.getParameter("customerAddress");
            String customerCountry = request.getParameter("customerCountry");
            String customerState = request.getParameter("customerState");
            String customerZipCode = request.getParameter("customerZipCode");
            String contactsIdToDelete = request.getParameter("contactsIdToDelete");

            updateProfileSettings(customerId, customerFirstName, customerCurrentPass,
                    customerNewPass, customerConfirmPass, customerLastName, customerEmail,
                    customerPhone, customerAddress, customerCountry, customerState, customerZipCode,
                    contactsIdToDelete);

            Map<String, Object> profileMap = loadProfileSettings(customerId);
            for (String key : profileMap.keySet()) {
                request.setAttribute(key, profileMap.get(key));
            }
            request.getRequestDispatcher("WEB-INF/jsp/settings.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            LOGGER.error("An exception in execute method in SettingsCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
