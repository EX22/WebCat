package by.khomenko.nsp.webcat.common.servlet.command;

import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Product;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarterPageCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(StarterPageCommand.class);


    private Map<String, Object> load() throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            List<Product> productsList = productDao.readAll();

            map.put("products", productsList);

        } catch (Exception e) {
            LOGGER.error("Loading starter page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> productsMap = load();
            for (String key : productsMap.keySet()) {
                request.setAttribute(key, productsMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/starterpage.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in StarterPageCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
