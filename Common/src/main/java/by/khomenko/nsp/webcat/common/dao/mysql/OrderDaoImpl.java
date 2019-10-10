package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.OrderDao;
import by.khomenko.nsp.webcat.common.entity.Order;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        return null;
    }

    @Override
    public Order read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Order order) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
