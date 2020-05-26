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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutCommand implements BaseCommand {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CheckOutCommand.class);

    private Map<String, Object> load(Integer orderId) throws PersistentException {

        Map<String, Object> map = new HashMap<>();

        try (OrderDao orderDao = DaoFactory.getInstance().createDao(OrderDao.class)) {

            Order order = orderDao.read(orderId);

            map.put("order", order);

        } catch (Exception e) {
            LOGGER.error("Loading checkout page an exception"
                    + " in load method occurred.", e);
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

                customerDao.updateCustomerNameLastNamePhone(customerId, customerFirstName,
                        customerLastName, customerPhone);
            }
            Contacts contacts = null;
            if (customerId != null) {
                contacts = new Contacts(customerId, customerAddress,
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

    public Integer createNewOrder(CartContent cartContent, Contacts contacts)
            throws PersistentException {

        try(OrderDao orderDao = DaoFactory.getInstance().createDao(OrderDao.class);
        OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().createDao(OrderDetailsDao.class)) {

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            Order newCustomerOrder = new Order(null, cartContent.getCustomerId(),
                        countOrderPrice(cartContent), Order.STATUS_NEW,
                    dateTimeFormatter.format(localDateTime),contacts.toString());

            Integer orderId = orderDao.create(newCustomerOrder);
            for (Map.Entry<Integer, Integer> entry: cartContent.getProducts().entrySet()){
                Integer productId = entry.getKey();
                Integer productCount = entry.getValue();
                OrderDetails orderDetails = new OrderDetails(orderId, productId,
                        cartContent.getProductInfo().get(productId).getProductPrice(),
                        productCount, 0.0);
                orderDetailsDao.create(orderDetails);
            }
            return  orderId;
        } catch (Exception e) {
            LOGGER.error("Creating new order in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }
    }

    public double countOrderPrice(CartContent cartContent) throws PersistentException {

        try(ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

            List<Product> products = productDao.readProductsById(cartContent.getProducts().keySet());
            cartContent.setProductInfo(products);

            double orderPrice = 0.0;
            for (Map.Entry<Integer, Integer> entry : cartContent.getProducts().entrySet()) {
                Integer productId = entry.getKey();
                  Integer productCount = entry.getValue();
                Product product = cartContent.getProductInfo().get(productId);
                orderPrice = orderPrice + product.getProductPrice()*productCount;
            }
            return orderPrice;

        } catch (Exception e) {
            LOGGER.error("Counting order price in CheckOutCommand class an "
                    + "exception occurred.", e);
            throw new PersistentException(e);
        }

    }

    private <T>T getObjectFromSession(Class<T> cartContentClass,
                                      String attributeName, HttpServletRequest request) {

        Object cartObj = request.getSession().getAttribute(attributeName);
        T result = null;

        if (cartObj != null){
            result = (T) cartObj;
        }
        return result;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (CartContentDao cartContentDao = DaoFactory.getInstance().createDao(CartContentDao.class);
             ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class);
             CustomerDao customerDao = DaoFactory.getInstance().createDao(CustomerDao.class)) {

            Integer customerId = (Integer)request.getSession().getAttribute("customerId");
            String action = request.getParameter("action");
            String selectedContactsId = request.getParameter("selectedContactsId");
            String customerFirstName = request.getParameter("customerFirstName");
            String customerLastName = request.getParameter("customerLastName");
            String customerEmail = request.getParameter("customerEmail");
            String customerPhone = request.getParameter("customerPhone");
            String customerAddress = request.getParameter("customerShippingAddress");
            String customerCountry = request.getParameter("customerCountry");
            String customerState = request.getParameter("customerState");
            String customerZipCode = request.getParameter("customerZipCode");

            Contacts contacts;
            CartContent cartContent;

            cartContent = getObjectFromSession(CartContent.class,
                        "cartContent", request);

            if ("newAddress".equals(action)) {

                if (customerEmail == null) {
                    //TODO Check other fields
                    LOGGER.warn("One of the form fields is null.");
                    response.sendRedirect("error.html");
                    return;
                }
                if ((customerId == null)&&(customerDao.isCustomerExist(customerEmail))) {
                    //TODO Show message that customer has an account and should log in.
                    response.sendRedirect("signin.html");
                    return;
                }
                    if (customerId == null) {

                        customerId = createNewCustomerAccount(customerEmail);
                        cartContent.setCustomerId(customerId);
                        cartContentDao.create(cartContent);
                        request.getSession().setAttribute("customerId", customerId);

                        //TODO Send login and password to customer by email.

                        contacts = setCustomerContacts(customerId, customerFirstName, customerLastName,
                                customerPhone, customerAddress, customerCountry,
                                customerState, customerZipCode);
                    } else {
                        cartContent.setCustomerId(customerId);
                        if (selectedContactsId != null) {
                            contacts = contactsDao.readByContactsId(Integer.parseInt(selectedContactsId));
                        } else {
                            contacts = setCustomerContacts(customerId, customerFirstName, customerLastName,
                                    customerPhone, customerAddress, customerCountry,
                                    customerState, customerZipCode);
                        }
                    }

            } else {
                cartContent.setCustomerId(customerId);
                if (selectedContactsId != null) {
                    contacts = contactsDao.readByContactsId(Integer.parseInt(selectedContactsId));
                } else {
                    contacts = setCustomerContacts(customerId, customerFirstName, customerLastName,
                            customerPhone, customerAddress, customerCountry,
                            customerState, customerZipCode);
                }
            }
            Integer createdOrderId = createNewOrder(cartContent, contacts);
            Map<String, Object> orderMap = load(createdOrderId);

            for (String key : orderMap.keySet()) {
                request.setAttribute(key, orderMap.get(key));
            }
            cartContentDao.delete(customerId);
            request.getSession().removeAttribute("cartContent");

            response.sendRedirect("thankyoupage.html");

        } catch (Exception e) {
            LOGGER.error("An exception in execute method in CheckOutCommand class occurred.", e);
            response.sendRedirect("error.html");
        }
    }
}
