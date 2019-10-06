package by.khomenko.nsp.webcat.common.servlet.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CatalogCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            request.getRequestDispatcher("WEB-INF/jsp/catalog.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CatalogCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
