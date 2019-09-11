package by.khomenko.nsp.webcat.dao.myql;

import by.khomenko.nsp.webcat.dao.CustomerDao;
import by.khomenko.nsp.webcat.entity.Customer;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CustomerDaoImpl.class);


    CustomerDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Customer customer) throws PersistentException {
        String sql = "INSERT INTO customers (login, password)"
                + " VALUES (?, ?)";

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
        return null;
    }

    @Override
    public void update(Customer customer) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
