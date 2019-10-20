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
import java.util.Map;

public class ProductCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ProductCommand.class);

    private Map<String, Object> load(Integer productId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();


        try (ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            Product product = productDao.read(productId);
            map.put("product", product);

        } catch (Exception e) {
            LOGGER.error("Loading product page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, Object> productMap = load(Integer.parseInt(request.getParameter("id")));
            for (String key : productMap.keySet()) {
                request.setAttribute(key, productMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/product.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in ProductCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
