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

public class RegistrationCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(RegistrationCommand.class);


    protected Integer createCustomer(String login, String pass, String confirmPass)
            throws PersistentException, ValidationException {

        Integer customerId;

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            if (!pass.equals(confirmPass)) {
                throw new ValidationException("Password and confirmPassword"
                        + " are not equal");
            }

            if (customerDao.isCustomerExist(login)) {
                throw new ValidationException("Customer already exists");
            }
            Customer customer = new Customer();
            customer.setLogin(login);
            customer.setPassword(pass);
            customerId = customerDao.create(customer);

        } catch (ValidationException e) {
            LOGGER.error("Creating customer an exception in RegistrationCommand class occurred. ", e);
            throw new ValidationException(e.getMessage());
        }catch (Exception e) {
            LOGGER.error("Creating customer an exception in RegistrationCommand class occurred. ", e);
            throw new PersistentException(e);
        }

        return customerId;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (login == null && password == null && confirmPassword == null) {
                //TODO Verify how it works.
                LOGGER.warn("One of the three registration parameters is null.");
                response.sendRedirect("error.html");
                return;
            }

            Integer registeredCustomerId;
            try {

                registeredCustomerId = createCustomer(login, password, confirmPassword);
                request.getSession().setAttribute("customerId", registeredCustomerId);
                response.sendRedirect("profile.html");

            } catch (ValidationException e) {

                //TODO Check error handling. Handle existing email input.
                request.setAttribute("errorMessage", e.getMessage());
                request.setAttribute("regLogin", login);
                request.getRequestDispatcher("WEB-INF/jsp/registration.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in RegistrationCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
