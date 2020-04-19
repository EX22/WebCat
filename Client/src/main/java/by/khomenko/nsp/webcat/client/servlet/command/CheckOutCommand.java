package by.khomenko.nsp.webcat.client.servlet.command;

import by.khomenko.nsp.webcat.common.dao.*;
import by.khomenko.nsp.webcat.common.entity.*;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import by.khomenko.nsp.webcat.common.exception.ValidationException;
import by.khomenko.nsp.webcat.common.service.PasswordGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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


    public Integer createNewCustomerAccount(String customerLogin)
            throws ValidationException, PersistentException {

        RegistrationCommand registrationCommand = new RegistrationCommand();
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        String randomlyGeneratedNewCustomerPassword = passwordGenerator.generateRandomPassword(15);

        return registrationCommand.createCustomer(customerLogin,
                randomlyGeneratedNewCustomerPassword, randomlyGeneratedNewCustomerPassword);

    }

    public Contacts setCustomerContacts(Integer customerId, String customerFirstName,
                                       String customerLastName, String customerPhone,
                                       String customerAddress, String customerCountry,
                                       String customerState, String customerZipCode)
            throws PersistentException {


        try (CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class);
             ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class)) {

            if ((customerFirstName != null) && (!"".equals(customerFirstName))) {

                customerDao.updateCustomerName(customerId, customerFirstName);
            }

            Contacts contacts = null;
            if (customerId != null) {
                contacts = new Contacts(customerId, customerLastName, customerAddress,
                        customerCountry, customerState, customerZipCode);

                contacts.setId(contactsDao.create(contacts));

            }
            return contacts;

        } catch (Exception e) {
            LOGGER.error("Setting customer's contacts in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }
    }

    public void createNewOrder(Cart cart, Contacts contacts)
            throws PersistentException {

        try(OrderDao orderDao = DaoFactory.getInstance().createDao(OrderDao.class)) {

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            Order newCustomerOrder = new Order(null, cart.getCustomerId(), cart.getCartId(),
                    countOrderPrice(cart), Order.STATUS_NEW, dateTimeFormatter.format(localDateTime),
                    contacts.toString());

            orderDao.create(newCustomerOrder);

        } catch (Exception e) {
            LOGGER.error("Creating new order in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }
    }

    public double countOrderPrice(Cart cart) throws PersistentException {
        try(CartDao cartDao = DaoFactory.getInstance().createDao(CartDao.class)) {

            Cart cart1 = cartDao.loadCartProductInfo(cart);
            double orderPrice = 0.0;
            for (Map.Entry<Integer, Integer> entry : cart1.getProducts().entrySet()) {
                Integer productId = entry.getKey();
                  Integer productCount = entry.getValue();
                Product product = cart.getProductInfo().get(productId);
                orderPrice = orderPrice + product.getProductPrice()*productCount;
            }
            return orderPrice;

        } catch (Exception e) {
            LOGGER.error("Counting order price in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String action = request.getParameter("action");
            Contacts contacts;

            if ("newAddress".equals(action)) {

                String customerFirstName = request.getParameter("customerFirstName");
                String customerLastName = request.getParameter("customerLastName");
                String customerEmail = request.getParameter("customerEmail");
                String customerPhone = request.getParameter("customerPhone");
                String customerAddress = request.getParameter("customerShippingAddress");
                String customerCountry = request.getParameter("customerCountry");
                String customerState = request.getParameter("customerState");
                String customerZipCode = request.getParameter("customerZipCode");


                if (customerEmail == null) {
                    //TODO Check other fields
                    LOGGER.warn("One of the form fields is null.");
                    response.sendRedirect("error.html");
                    return;
                }

                //TODO Check if customer exists in order to not creating new customer in DB, and entry in contacts table.

                if (customerId == null) {

                    customerId = createNewCustomerAccount(customerEmail);

                    //TODO Send login and password to customer by email.

                }

                 contacts = setCustomerContacts(customerId, customerFirstName, customerLastName, customerPhone,
                        customerAddress, customerCountry, customerState, customerZipCode);

            } else {
                response.sendRedirect("settings.html");
                return;
            }

            Map<String, Object> customerCart = load(customerId);
            Cart cart = (Cart) customerCart.get("customerCart");
            createNewOrder(cart, contacts);

            response.sendRedirect("thankyoupage.html");

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }

}
