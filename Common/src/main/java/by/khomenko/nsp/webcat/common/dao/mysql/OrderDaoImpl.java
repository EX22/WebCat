package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.OrderDao;
import by.khomenko.nsp.webcat.common.entity.Order;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(OrderDaoImpl.class);


    public OrderDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Order order) throws PersistentException {
        String sql = "INSERT INTO orders (customer_id, order_price,"
                + " order_status, order_date, shipping_address) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getCustomerId());
            statement.setDouble(2, order.getOrderPrice());
            statement.setString(3, order.getOrderStatus());
            statement.setString(4, order.getOrderDate());
            statement.setString(5, order.getShippingAddress());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    LOGGER.error("There is no autoincremented index after"
                            + " trying to add record into table `orders`");
                    throw new PersistentException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Creating order an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public Order read(Integer identity) throws PersistentException {
        String sql = "SELECT customer_id, order_price, order_status,"
                + " order_date, shipping_address"
                + " FROM orders WHERE order_id = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, identity);

            Order order = null;
            if (resultSet.next()) {
                order = new Order();
                order.setOrderId(identity);
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setOrderPrice(resultSet.getDouble("order_price"));
                order.setOrderStatus(resultSet.getString("order_status"));
                order.setOrderDate(resultSet.getString("order_date"));
                order.setShippingAddress(resultSet.getString("shipping_address"));

            }
            return order;
        } catch (SQLException e) {
            LOGGER.error("Reading from table `orders`"
                    + " an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Order> readOrdersByCustomerId(Integer customerId) throws PersistentException {
        String sql = "SELECT order_id, order_price, order_status,"
                + " order_date, shipping_address"
                + " FROM orders WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            List<Order> ordersList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Order order
                            = new Order(resultSet.getInt("order_id"),
                            customerId,
                            resultSet.getDouble("order_price"),
                            resultSet.getString("order_status"),
                            resultSet.getString("order_date"),
                            resultSet.getString("shipping_address"));
                    ordersList.add(order);
                }
            }
            return ordersList;
        } catch (SQLException e) {
            LOGGER.error("Reading orders by customer id an exception occurred. ", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Order order) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
