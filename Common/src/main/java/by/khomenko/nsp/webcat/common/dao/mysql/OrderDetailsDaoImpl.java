package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.OrderDetailsDao;
import by.khomenko.nsp.webcat.common.entity.Order;
import by.khomenko.nsp.webcat.common.entity.OrderDetails;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDetailsDaoImpl extends BaseDaoImpl<OrderDetails> implements OrderDetailsDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(OrderDetailsDaoImpl.class);


    public OrderDetailsDaoImpl() throws PersistentException {
    }


    @Override
    public Integer create(OrderDetails orderDetails) throws PersistentException {
        String sql = "INSERT INTO order_details (order_id, product_id,"
                + " product_price, product_amount, discount) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderDetails.getOrderId());
            statement.setInt(2, orderDetails.getProductId());
            statement.setDouble(3, orderDetails.getProductPrice());
            statement.setInt(4, orderDetails.getProductAmount());
            statement.setDouble(5, orderDetails.getDiscount());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Creating order details an exception occurred. ", e);
            throw new PersistentException(e);
        }
        return 0;
    }

    @Override
    public OrderDetails read(Integer identity) throws PersistentException {
        String sql = "SELECT product_id, product_price,"
                + " product_amount, discount"
                + " FROM order_details WHERE order_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, identity);

            OrderDetails orderDetails = null;
            if (resultSet.next()) {
                orderDetails = new OrderDetails();
                orderDetails.setOrderId(identity);
                orderDetails.setProductId(resultSet.getInt("product_id"));
                orderDetails.setProductPrice(resultSet.getDouble("product_price"));
                orderDetails.setProductAmount(resultSet.getInt("product_amount"));
                orderDetails.setDiscount(resultSet.getDouble("discount"));

            }
            return orderDetails;
        } catch (SQLException e) {
            LOGGER.error("Reading from table `order_details`"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(OrderDetails entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
