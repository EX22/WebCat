package by.khomenko.nsp.webcat.servlet;

import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(
        name = "DispatcherServlet",
        urlPatterns = {"/starterpage.html", "/catalog.html", "/category.html",
                "/product.html", "/profile.html", "/registration.html",
                "/signin.html", "/cart.html", "/checkout.html"})

public class DispatcherServlet extends HttpServlet {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int length = request.getContextPath().length();
        String s = request.getRequestURI().substring(length);

        try {

            switch (s) {

                case "/starterpage.html":

                    request.getRequestDispatcher("WEB-INF/jsp/starterpage.jsp")
                            .forward(request, response);
                    break;

                case "/catalog.html":

                    request.getRequestDispatcher("WEB-INF/jsp/catalog.jsp")
                            .forward(request, response);
                    break;

                case "//category.html":

                    request.getRequestDispatcher("WEB-INF/jsp//category.jsp")
                            .forward(request, response);
                    break;

                case "/product.html":

                    request.getRequestDispatcher("WEB-INF/jsp/product.jsp")
                            .forward(request, response);
                    break;

                case "/profile.html":

                    request.getRequestDispatcher("WEB-INF/jsp/profile.jsp")
                            .forward(request, response);
                    break;

                case "/registration.html":

                    request.getRequestDispatcher("WEB-INF/jsp/registration.jsp")
                            .forward(request, response);
                    break;

                case "/signin.html":

                    request.getRequestDispatcher("WEB-INF/jsp/signin.jsp")
                            .forward(request, response);
                    break;

                case "/cart.html":

                    request.getRequestDispatcher("WEB-INF/jsp/cart.jsp")
                            .forward(request, response);
                    break;

                case "/checkout.html":

                    request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp")
                            .forward(request, response);
                    break;

            }

            //TODO PersistentException should be caught here
        } catch (Exception e) {
            LOGGER.error("An exception in doGet method occurred.", e);
            response.sendRedirect("error.html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        super.init();

    }

}
