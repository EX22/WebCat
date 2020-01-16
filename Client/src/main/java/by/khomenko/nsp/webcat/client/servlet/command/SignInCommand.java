package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.BlackListDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(SignInCommand.class);

    public Customer logInCustomer(String login, String password)
            throws PersistentException {

        Customer loggedCustomer;

        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             BlackListDao blackListDao = DaoFactory.getInstance().createDao(BlackListDao.class)) {

            loggedCustomer = customerDao.read(login, password);
            if (blackListDao.isCustomerInBlacklist(loggedCustomer)){
                return null;
            }


        } catch (Exception e) {
            LOGGER.error("Checking customer in `customers` table "
                    + "an exception occurred. ", e);
            throw new PersistentException(e);
        }

        return loggedCustomer;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (login == null && password == null) {

                request.setAttribute("errorMessage", "Incorrect login or password.");
                request.getRequestDispatcher("WEB-INF/jsp/signin.jsp")
                        .forward(request, response);
                return;
            }

            Customer loggedInCustomer = logInCustomer(login, password);

            if (loggedInCustomer != null) {
                request.getSession().setAttribute("customerId", loggedInCustomer.getCustomerId());
                response.sendRedirect("profile.html");
            }


        } catch (Exception e) {
            LOGGER.error("An exception in execute method in SignInCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
