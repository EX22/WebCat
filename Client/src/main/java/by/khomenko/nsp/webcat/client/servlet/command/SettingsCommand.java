package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
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

    public void updateProfileSettings(Integer currentCustomerId,
                                      String customerName, String currentPass,
                                      String newPass, String confirmPass)
            throws PersistentException, ValidationException {

        Customer loggedCustomer;

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            if ((customerName != null) && (!"".equals(customerName))) {

                customerDao.updateCustomerName(currentCustomerId, customerName);
            }

            if ((newPass != null) && (!"".equals(newPass))) {
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


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String newCustomerFirstName = request.getParameter("customerFirstName");
            String newCustomerLastName = request.getParameter("customerLastName");
            String newCustomerPhone = request.getParameter("customerPhone");
            String newCustomerAddress = request.getParameter("customerAddress");
            String newCustomerCountry = request.getParameter("customerCountry");
            String newCustomerState = request.getParameter("customerState");
            String newCustomerZipCode = request.getParameter("customerZipCode");
            String newCustomerEmail = request.getParameter("customerEmail");
            String customerCurrentPass = request.getParameter("customerCurrentPassword");
            String customerNewPass = request.getParameter("customerNewPassword");
            String customerConfirmPass = request.getParameter("customerConfirmNewPassword");

            updateProfileSettings(customerId, newCustomerFirstName, customerCurrentPass,
                    customerNewPass, customerConfirmPass);

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
