package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.ContactsDao;
import by.khomenko.nsp.webcat.common.dao.CustomerDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CustomerDaoImpl.class);


    public CustomerDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Customer customer) throws PersistentException {
        String sql = "INSERT INTO customers (login, password)"
                + " VALUES (?, MD5(?))";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getLogin());
            statement.setString(2, customer.getPassword());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `customers`");
                    throw new PersistentException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Creating customer an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Customer read(Integer identity) throws PersistentException {
        String sql = "SELECT customer_id, login, password, name, phone_number, "
                + "email, ip, location, customer_status, discount FROM customers WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            Customer customer = null;

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer();
                    customer.setCustomerId(identity);
                    customer.setLogin(resultSet.getString("login"));
                    customer.setPassword(resultSet.getString("password"));
                    customer.setName(resultSet.getString("name"));
                    customer.setPhoneNumber(resultSet.getString("phone_number"));
                    customer.setIp(resultSet.getString("ip"));
                    customer.setLocation(resultSet.getString("location"));
                    customer.setStatus(resultSet.getString("customer_status"));
                    customer.setDiscount(resultSet.getDouble("discount"));
                    ContactsDao contactsDao = DaoFactory.getInstance().createDao(ContactsDao.class);
                    customer.setContactsList(contactsDao.readList(identity));

                }
            }
            return customer;
        } catch (SQLException e) {
            LOGGER.error("Reading table `customers` by customer id an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Customer read(String login, String password) throws PersistentException {
        String sql = "SELECT customer_id, login, password, name, phone_number, "
                + "email, ip, location, customer_status, discount "
                + "FROM customers WHERE login = ? AND password = MD5(?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            Customer customer = null;

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customer_id"));
                    customer.setLogin(resultSet.getString("login"));
                    customer.setPassword(resultSet.getString("password"));
                    customer.setName(resultSet.getString("name"));
                    customer.setPhoneNumber(resultSet.getString("phone_number"));
                    customer.setIp(resultSet.getString("ip"));
                    customer.setLocation(resultSet.getString("location"));
                    customer.setStatus(resultSet.getString("customer_status"));
                    customer.setDiscount(resultSet.getDouble("discount"));

                }
            }
            return customer;
        } catch (SQLException e) {
            LOGGER.error("Reading table `customers` an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void updateCustomerName(Integer customerId, String name) throws PersistentException {

        String sql = "UPDATE customers SET name = ? WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, customerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Updating customer's name an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void updateCustomerPass(Integer customerId, String password) throws PersistentException {

        String sql = "UPDATE customers SET password = MD5(?) WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, password);
            statement.setInt(2, customerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Updating customer's password an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public boolean isCustomerExist(String login) throws PersistentException {

        String sql = "SELECT customer_id FROM customers WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {

                return resultSet.next();
            }
        } catch (SQLException e) {
            LOGGER.error("Checking if customer exists"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Customer customer) throws PersistentException {

        String sql = "UPDATE customers SET login = ?, password = ?, name = ?,"
                + " phone_number = ?, ip = ?, location = ?, customer_status = ?,"
                + " discount = ?, role = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getLogin());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getName());
            statement.setString(4, customer.getPhoneNumber());
            statement.setString(5, customer.getIp());
            statement.setString(6, customer.getLocation());
            statement.setString(7, customer.getStatus());
            statement.setDouble(8, customer.getDiscount());
            statement.setInt(9, customer.getRole().getIdentity());
            statement.setInt(10, customer.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Updating customer an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

        String sql = "DELETE FROM customers WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting customer an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }
}
