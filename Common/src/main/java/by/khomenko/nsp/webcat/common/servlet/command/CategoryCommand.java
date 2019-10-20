package by.khomenko.nsp.webcat.common.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CategoryDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Category;
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

public class CategoryCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CategoryCommand.class);

    private Map<String, Object> load(Integer categoryId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class);
             CategoryDao categoryDao = DaoFactory.getInstance().createDao(CategoryDao.class)) {

            List<Product> productsList = productDao.readProductsByCategoryId(categoryId);
            Category currentCategory = categoryDao.read(categoryId);
            List<Category> categoriesList = categoryDao.readAll();

            map.put("categories", categoriesList);
            map.put("products", productsList);
            map.put("currentCategory", currentCategory);

        } catch (Exception e) {
            LOGGER.error("Loading catalog page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {

            Map<String, Object> categoryMap
                    = load(Integer.parseInt(request.getParameter("categoryId")));
            for (String key : categoryMap.keySet()) {
                request.setAttribute(key, categoryMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/category.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CategoryCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
