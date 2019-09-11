package by.khomenko.nsp.webcat.dao.myql;

import by.khomenko.nsp.webcat.dao.StockDao;
import by.khomenko.nsp.webcat.entity.Stock;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(StockDaoImpl.class);


    StockDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Stock entity) throws PersistentException {
        return null;
    }

    @Override
    public Stock read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Stock entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
