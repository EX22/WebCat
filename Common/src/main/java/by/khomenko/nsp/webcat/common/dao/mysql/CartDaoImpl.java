package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao {


    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CartDaoImpl.class);


    public CartDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Cart cart) throws PersistentException {
        String sql = "INSERT INTO cart (customer_id, product_Id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cart.getCustomerId());
            //statement.setInt(2, cart.getProductId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Creating cart an exception occurred. ", e);
            throw new PersistentException(e);
        }

        return 0;
    }

    @Override
    public Cart read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public Cart readCartByCustomerId(Integer customerId) throws PersistentException {
        String sql = "SELECT product_id FROM cart WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            Cart cart = null;
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    //cart = new Cart(customerId, resultSet.getInt("product_id"));

                }
            }
            return cart;
        } catch (SQLException e) {
            LOGGER.error("Reading cart by customer id an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }


    @Override
    public void update(Cart cart) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
