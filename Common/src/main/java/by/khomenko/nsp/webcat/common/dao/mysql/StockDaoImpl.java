package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.StockDao;
import by.khomenko.nsp.webcat.common.entity.Stock;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(StockDaoImpl.class);


    public StockDaoImpl() throws PersistentException {
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
