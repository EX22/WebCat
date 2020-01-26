package by.khomenko.nsp.webcat.client.servlet;

import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.pool.ConnectionPool;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.client.servlet.command.*;
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
                "/orderstatus.html", "/personaldata.html", "/product.html", "/profile.html",
                "/registration.html", "/settings.html", "/signin.html", "/cart.html", "/checkout.html"})

public class DispatcherServlet extends HttpServlet {

    //TODO Possible to use Descriptor file instead of constants.
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

    private static Map<String, Class<? extends BaseCommand>> commandGet = new ConcurrentHashMap<>();

    static {

        commandGet.put("/", StarterPageCommand.class);
        commandGet.put("/blog.html", BlogCommand.class);
        commandGet.put("/cart.html", CartCommand.class);
        commandGet.put("/catalog.html", CatalogCommand.class);
        commandGet.put("/category.html", CategoryCommand.class);
        commandGet.put("/checkout.html", CheckOutCommand.class);

        commandGet.put("/product.html", ProductCommand.class);
        commandGet.put("/profile.html", ProfileCommand.class);
        commandGet.put("/registration.html", ShowRegistrationCommand.class);
        commandGet.put("/signin.html", SignInCommand.class);
        commandGet.put("/starterpage.html", StarterPageCommand.class);
        commandGet.put("/orderstatus.html", OrderStatusCommand.class);
        commandGet.put("/personaldata.html", PersonalDataCommand.class);
        commandGet.put("/settings.html", SettingsCommand.class);

    }

    private static Map<String, Class<? extends BaseCommand>> commandPost = new ConcurrentHashMap<>();

    static {

        commandPost.put("/registration.html", RegistrationCommand.class);
        commandPost.put("/signin.html", SignInCommand.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends BaseCommand> T getCommandByUrl(String s, Map<String, Class<? extends BaseCommand>> command) {

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

        BaseCommand baseCommand = getCommandByUrl(s, commandGet);

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

        BaseCommand baseCommand = getCommandByUrl(s, commandPost);

        if(baseCommand != null) {
            baseCommand.execute(request, response);
        }


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
