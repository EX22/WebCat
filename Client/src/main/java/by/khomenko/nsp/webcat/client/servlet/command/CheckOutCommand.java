package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.common.exception.ValidationException;
import by.khomenko.nsp.webcat.common.service.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CheckOutCommand.class);

    private Map<String, Object> load(Integer customerId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class)) {

            Cart customerCart = cartDao.readCartByCustomerId(customerId);

            map.put("customerCart", customerCart);

        } catch (Exception e) {
            LOGGER.error("Loading checkout page an exception occurred.", e);
            throw new PersistentException(e);
        }

        return map;
    }

    public void createNewCustomerAccount(String customerLogin)
            throws ValidationException, PersistentException {

        RegistrationCommand registrationCommand = new RegistrationCommand();
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        String randomlyGeneratedNewCustomerPassword = passwordGenerator.generateRandomPassword(15);

        registrationCommand.createCustomer(customerLogin, randomlyGeneratedNewCustomerPassword,
                randomlyGeneratedNewCustomerPassword);

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            String newCustomerFirstName = request.getParameter("customerFirstName");
            String newCustomerLastName = request.getParameter("customerLastName");
            String newCustomerEmail = request.getParameter("customerEmail");
            String newCustomerPhone = request.getParameter("customerPhone");
            String newCustomerAddress = request.getParameter("customerAddress");
            String newCustomerCountry = request.getParameter("customerCountry");
            String newCustomerState = request.getParameter("customerState");
            String newCustomerZipCode = request.getParameter("customerZipCode");

            //TODO Send login and password by email to customer.
            createNewCustomerAccount(newCustomerEmail);

            Object customerIdObj = request.getSession().getAttribute("customerId");
            Map<String, Object> checkOutMap = new HashMap<>();

            if (customerIdObj != null) {
                checkOutMap = load((Integer)customerIdObj);
            }

            for (String key : checkOutMap.keySet()) {
                request.setAttribute(key, checkOutMap.get(key));
            }

            request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
