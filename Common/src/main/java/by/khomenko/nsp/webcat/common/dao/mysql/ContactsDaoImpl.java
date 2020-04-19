package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.ContactsDao;
import by.khomenko.nsp.webcat.common.entity.Contacts;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactsDaoImpl extends BaseDaoImpl<Contacts> implements ContactsDao {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ContactsDaoImpl.class);

    public ContactsDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Contacts contacts) throws PersistentException {
        String sql = "INSERT INTO customer_contacts (customer_id, last_name,"
                + " shipping_address, country, state, zip_code)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {


            statement.setInt(1, contacts.getCustomerId());
            statement.setString(2, contacts.getLastName());
            statement.setString(3, contacts.getShippingAddress());
            statement.setString(4, contacts.getCountry());
            statement.setString(5, contacts.getState());
            statement.setString(6, contacts.getZipCode());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `customer_contacts`");
                    throw new PersistentException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Creating entry in 'customer_contacts' table an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Contacts read(Integer identity) throws PersistentException {
        String sql = "SELECT customer_id, last_name, shipping_address, country,"
                + " state, zip_code FROM customer_contacts  WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            Contacts contacts = null;

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    contacts = new Contacts();
                    contacts.setCustomerId(identity);
                    contacts.setLastName(resultSet.getString("last_name"));
                    contacts.setShippingAddress(resultSet.getString("shipping_address"));
                    contacts.setCountry(resultSet.getString("country"));
                    contacts.setState(resultSet.getString("state"));
                    contacts.setZipCode(resultSet.getString("zip_code"));

                }
            }
            return contacts;
        } catch (SQLException e) {
            LOGGER.error("Reading table `customer_contacts` by customer id an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    public List<Contacts> readList(Integer identity) throws PersistentException {
        String sql = "SELECT id, customer_id, last_name, shipping_address, country,"
                + " state, zip_code FROM customer_contacts  WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            Contacts contacts;
            List<Contacts> contactsList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    contacts = new Contacts();
                    contacts.setId(resultSet.getInt("id"));
                    contacts.setCustomerId(identity);
                    contacts.setLastName(resultSet.getString("last_name"));
                    contacts.setShippingAddress(resultSet.getString("shipping_address"));
                    contacts.setCountry(resultSet.getString("country"));
                    contacts.setState(resultSet.getString("state"));
                    contacts.setZipCode(resultSet.getString("zip_code"));

                    contactsList.add(contacts);
                }
            }
            return contactsList;
        } catch (SQLException e) {
            LOGGER.error("Reading table `customer_contacts` by customer id in readList method"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Contacts contacts) throws PersistentException {

        String sql = "UPDATE customer_contacts SET last_name = ?,"
                + " shipping_address = ?, country = ?, state = ?, zip_code = ?"
                + " WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contacts.getLastName());
            statement.setString(2, contacts.getShippingAddress());
            statement.setString(3, contacts.getCountry());
            statement.setString(4, contacts.getState());
            statement.setString(5, contacts.getZipCode());
            statement.setInt(6, contacts.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating 'customer_contacts' table an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {

        String sql = "DELETE FROM customer_contacts WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting entry in 'customer_contacts' an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }
}
