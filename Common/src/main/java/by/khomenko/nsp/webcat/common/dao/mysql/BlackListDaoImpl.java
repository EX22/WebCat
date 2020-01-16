package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.BlackListDao;
import by.khomenko.nsp.webcat.common.entity.BlackList;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlackListDaoImpl extends BaseDaoImpl<BlackList> implements BlackListDao {

    private static final Logger LOGGER
            = LogManager.getLogger(AdministrationDaoImpl.class);

    BlackListDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(BlackList blackList) throws PersistentException {

        String sql = "INSERT INTO blacklist (customer_id) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, blackList.getCustomerId());
            statement.executeUpdate();


        } catch (SQLException e) {
            LOGGER.error("Creating blacklist entry "
                    + "an exception occurred. ", e);
            throw new PersistentException(e);
        }
        return 0;
    }

    @Override
    public BlackList read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(BlackList entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

        String sql = "DELETE FROM blacklist WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, identity);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Deleting blacklist entry "
                    + "an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public boolean isCustomerInBlacklist(Customer customer) throws PersistentException {

        String sql = "SELECT customer_id FROM blacklist WHERE customer_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customer.getId());

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            LOGGER.error("Reading blacklist an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }
}
