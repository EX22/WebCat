package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.CartDao;
import by.khomenko.nsp.webcat.common.dao.DaoFactory;
import by.khomenko.nsp.webcat.common.dao.ProductDao;
import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.entity.Product;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao {


    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CartDaoImpl.class);


    public CartDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Cart cart) throws PersistentException {
        String sql = "INSERT INTO cart (customer_id, cart_status) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, cart.getCustomerId());
            statement.setString(2, cart.getCartStatus());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `cart`");
                    throw new PersistentException();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Creating cart an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public Cart read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public Cart readCartByCustomerId(Integer customerId) throws PersistentException {

        String sql = "SELECT cart_id, cart_status FROM cart WHERE customer_id = ?";
        String sql2 = "SELECT product_id, product_count FROM cart_content WHERE cart_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             PreparedStatement statement2 = connection.prepareStatement(sql2)) {

            statement.setInt(1, customerId);

            Cart cart = null;
            Map<Integer, Integer> products = null;

            try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {

                    cart = new Cart();
                    cart.setCustomerId(customerId);
                    cart.setCartId(resultSet.getInt("cart_id"));
                    cart.setCartStatus(resultSet.getString("cart_status"));

                }
            }

            if (cart != null) {

                statement2.setInt(1, cart.getCartId());

                try (ResultSet resultSet = statement2.executeQuery()) {

                    if (resultSet.next()) {

                        products.put(resultSet.getInt("product_id"),
                                resultSet.getInt("product_count"));
                        cart.setProducts(products);
                    }
                }
            }

            return cart;

        } catch (SQLException e) {
            LOGGER.error("Reading cart by customer id an exception occurred. ", e);
            throw new PersistentException(e);
        }

    }

    @Override
    public Cart loadCartProductInfo(Cart cart) throws PersistentException {

        String sql = "SELECT product_id FROM cart_content WHERE cart_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cart.getCartId());

            Map<Integer, Product> productInfo = null;

            try (ResultSet resultSet = statement.executeQuery();
                 ProductDao productDao = DaoFactory.getInstance().createDao(ProductDao.class)) {

                while (resultSet.next()) {

                    productInfo.put(resultSet.getInt("product_id"),
                            productDao.read(resultSet.getInt("product_id")));

                    cart.setProductInfo(productInfo);

                }
            }

            return cart;

        } catch (Exception e) {
            LOGGER.error("Loading cartProductInfo an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Cart cart) throws PersistentException {

        //TODO Use INSERT ... ON DUPLICATE KEY UPDATE Statement
    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
