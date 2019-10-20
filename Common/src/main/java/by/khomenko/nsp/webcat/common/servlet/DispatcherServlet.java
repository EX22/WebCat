package by.khomenko.nsp.webcat.common.servlet;

import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.pool.ConnectionPool;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.common.servlet.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@MultipartConfig
@WebServlet(
        name = "DispatcherServlet",
        urlPatterns = {"/", "/starterpage.html", "/catalog.html", "/blog.html", "/category.html",
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

    private static Map<String, Class<? extends BaseCommand>> command = new ConcurrentHashMap<>();

    static {

        command.put("/", StarterPageCommand.class);
        command.put("/blog.html", BlogCommand.class);
        command.put("/cart.html", CartCommand.class);
        command.put("/catalog.html", CatalogCommand.class);
        command.put("/category.html", CategoryCommand.class);
        command.put("/checkout.html", CheckOutCommand.class);

        command.put("/product.html", ProductCommand.class);
        command.put("/profile.html", ProfileCommand.class);
        command.put("/registration.html", RegistrationCommand.class);
        command.put("/signin.html", SignInCommand.class);
        command.put("/starterpage.html", StarterPageCommand.class);

    }

    @SuppressWarnings("unchecked")
    private <T extends BaseCommand> T getCommandByUrl(String s) {

        Class value = command.get(s);
        if (value != null){
            try {
                Object obj = value.newInstance();
                return (T)obj;
            } catch (InstantiationException | IllegalAccessException e){
                LOGGER.error("It is impossible to create Command object", e);

            }
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int length = request.getContextPath().length();
        String s = request.getRequestURI().substring(length);

        BaseCommand baseCommand = getCommandByUrl(s);

        if(baseCommand != null) {
            baseCommand.execute(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int length = request.getContextPath().length();
        String s = request.getRequestURI().substring(length);



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
