package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CategoryDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Category;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CatalogCommand.class);

    private Map<String, Object> load() throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CategoryDao categoryDao = DaoFactory.getInstance().createDao(CategoryDao.class)) {

            List<Category> categoryList = categoryDao.readAll();

            map.put("categories", categoryList);

        } catch (Exception e) {
            LOGGER.error("Loading catalog page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> catalogMap = load();
            for (String key : catalogMap.keySet()) {
                request.setAttribute(key, catalogMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/catalog.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CatalogCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
