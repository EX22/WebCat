package by.khomenko.nsp.webcat.dao.mysql;

import by.khomenko.nsp.webcat.dao.OrderDetailsDao;
import by.khomenko.nsp.webcat.entity.OrderDetails;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderDetailsDaoImpl extends BaseDaoImpl<OrderDetails> implements OrderDetailsDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(OrderDetailsDaoImpl.class);


    OrderDetailsDaoImpl() throws PersistentException {
    }


    @Override
    public Integer create(OrderDetails entity) throws PersistentException {
        return null;
    }

    @Override
    public OrderDetails read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(OrderDetails entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
