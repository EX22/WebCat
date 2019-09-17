package by.khomenko.nsp.webcat.servlet;

import by.khomenko.nsp.webcat.dao.DaoFactory;
import by.khomenko.nsp.webcat.dao.pool.ConnectionPool;
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

    //TODO Probable se Descriptor file instead of constants.
    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/web_cat?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "web_cat_user";
    private static final String DB_PASSWORD = "web_cat_password";
    private static final int DB_POOL_START_SIZE = 10;
    private static final int DB_POOL_MAX_SIZE = 1000;
    private static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

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

                case "/blog.html":

                    request.getRequestDispatcher("WEB-INF/jsp/blog.jsp")
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

        try {
            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER,
                    DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE,
                    DB_POOL_CHECK_CONNECTION_TIMEOUT);
            DaoFactory.getInstance().init("mysql");
        } catch (PersistentException e) {
            LOGGER.error("It is impossible to initialize application", e);
            destroy();
        }

    }

}
