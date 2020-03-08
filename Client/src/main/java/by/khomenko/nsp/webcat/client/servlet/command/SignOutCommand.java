package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(SignInCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            request.getSession().removeAttribute("customerId");
            response.sendRedirect("signin.html");

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in SignOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
