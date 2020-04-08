package by.khomenko.nsp.webcat.client.servlet.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowRegistrationCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ShowRegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/registration.jsp")
                    .forward(request, response);
        } catch (ServletException e) {
            LOGGER.error("An exception in execute method in ShowRegistrationCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
