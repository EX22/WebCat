package by.khomenko.nsp.webcat.dao.mysql;

import by.khomenko.nsp.webcat.dao.OrderDao;
import by.khomenko.nsp.webcat.entity.Order;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(OrderDaoImpl.class);


    OrderDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Order entity) throws PersistentException {
        return null;
    }

    @Override
    public Order read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Order entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
