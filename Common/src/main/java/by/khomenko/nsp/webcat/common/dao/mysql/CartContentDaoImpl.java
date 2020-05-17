package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.CartContentDao;
import by.khomenko.nsp.webcat.common.entity.CartContent;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CartContentDaoImpl extends BaseDaoImpl<CartContent> implements CartContentDao {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CartContentDaoImpl.class);

    public CartContentDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(CartContent cartContent) throws PersistentException {

        String sql = "INSERT INTO cart_content (customer_id, product_id, product_count)"
                + " VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Map.Entry<Integer, Integer> pair : cartContent.getProducts().entrySet()){
                statement.setInt(1, cartContent.getCustomerId());
                statement.setInt(2, pair.getKey());
                statement.setInt(3, pair.getValue());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.error("Creating 'cart_content' table an exception occurred. ", e);
            throw new PersistentException(e);
        }
        return 0;
    }

    @Override
    public CartContent read(Integer customerId) throws PersistentException {

        String sql = "SELECT customer_id, product_id, product_count "
                + "FROM cart_content WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            CartContent cartContent = new CartContent();
            while (resultSet.next()){

                cartContent.setCustomerId(customerId);
                cartContent.addProduct(resultSet.getInt("product_id"),
                        resultSet.getInt("product_count"));
            }
            return cartContent;
        } catch (SQLException e) {
            LOGGER.error("Reading 'cart_content' table an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(CartContent cartContent) throws PersistentException {

        String sql = "INSERT INTO cart_content (customer_id, product_id, product_count)"
                + " VALUES (?, ?, ?)"
                + " ON DUPLICATE KEY UPDATE product_count = ?";

        if (cartContent.getCustomerId()==null){
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Map.Entry<Integer, Integer> pair : cartContent.getProducts().entrySet()){
                statement.setInt(1, cartContent.getCustomerId());
                statement.setInt(2, pair.getKey());
                statement.setInt(3, pair.getValue());
                statement.setInt(4, pair.getValue());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.error("Updating 'cart_content' table an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public void deleteByProductId(Integer productId) throws PersistentException {

        String sql = "DELETE FROM cart_content WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Deleting from cart_content table by "
                    + "product_id an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer customerId) throws PersistentException {

        String sql = "DELETE FROM cart_content WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Deleting cart_content an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }
}
