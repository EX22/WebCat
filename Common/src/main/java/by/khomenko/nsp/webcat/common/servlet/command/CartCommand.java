package by.khomenko.nsp.webcat.common.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CartCommand.class);

    private Map<String, Object> load(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class)) {

            Cart customerCart = cartDao.readCartByCustomerId(customerId);

            map.put("customerCart", customerCart);

        } catch (Exception e) {
            LOGGER.error("Loading cart page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String productId = request.getParameter("id");

            if(productId != null){
                //TODO Cart's item quantity
                response.getWriter().print("100");
                return;
            }

            Map<String, Object> cartMap = load(Integer.parseInt(request.getParameter("customerId")));
            for (String key : cartMap.keySet()) {
                request.setAttribute(key, cartMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/cart.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CartCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
