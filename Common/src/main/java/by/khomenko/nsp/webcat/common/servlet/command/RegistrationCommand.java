package by.khomenko.nsp.webcat.common.servlet.command;

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
import java.util.List;
import java.util.Map;

public class RegistrationCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(RegistrationCommand.class);

    /*private Map<String, Object> load() throws PersistentException {

        Map<String, Object> map = new HashMap<>();


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            Customer customer = customerDao.create();


            map.put("customer", customer);


        } catch (Exception e) {
            LOGGER.error("Loading registration page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }*/

    private Integer createCustomer(String login, String pass, String confirmPass)
            throws PersistentException, ValidationException {
        Integer userId;

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            if (!pass.equals(confirmPass)) {
                throw new ValidationException("Password and confirmPassword"
                        + " are not equal");
            }

            /*if (customerDao.isUserExist(login)) {
                throw new ValidationException("User already exists");
            }*/
            Customer customer = new Customer();
            customer.setLogin(login);
            customer.setPassword(pass);
            userId = customerDao.create(customer);

        } catch (ValidationException e) {
            LOGGER.error("Creating customer an exception occurred. ", e);
            throw new ValidationException(e.getMessage());
        }catch (Exception e) {
            LOGGER.error("Creating customer an exception occurred. ", e);
            throw new PersistentException(e);
        }

        return userId;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String rps = request.getParameter("login");
            String rpps = request.getParameter("password");
            String rpcps = request.getParameter("confirmPassword");


            Integer rUserId;
            try {
                rUserId = createCustomer(rps, rpps, rpcps);
                request.getSession().setAttribute("userId", rUserId);
                response.sendRedirect("profile.html");

            } catch (ValidationException e) {

                request.setAttribute("errorMessage", e.getMessage());
                request.setAttribute("regLogin", rps);
                request.getRequestDispatcher("WEB-INF/jsp/registration.jsp")
                        .forward(request, response);
            }

            request.getRequestDispatcher("WEB-INF/jsp/registration.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in RegistrationCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
