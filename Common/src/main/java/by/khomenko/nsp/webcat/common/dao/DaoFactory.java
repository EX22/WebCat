package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.dao.mysql.*;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DaoFactory {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(DaoFactory.class);

    private static DaoFactory instance = new DaoFactory();

    public static DaoFactory getInstance() {
        return instance;
    }

    private Map<Class<? extends Dao<?>>, Class> classes = new ConcurrentHashMap<>();

    public void init(String implementation) throws PersistentException {

        if ("mysql".equals(implementation)){

            classes.put(AdministrationDao.class, AdministrationDaoImpl.class);
            classes.put(CartDao.class, CartDaoImpl.class);
            classes.put(CategoryDao.class, CategoryDaoImpl.class);
            classes.put(CustomerDao.class, CustomerDaoImpl.class);
            classes.put(OrderDao.class, OrderDaoImpl.class);
            classes.put(OrderDetailsDao.class, OrderDetailsDaoImpl.class);
            classes.put(ProductDao.class, ProductDaoImpl.class);
            classes.put(ProductsCategoryDao.class, ProductsCategoryDaoImpl.class);
            classes.put(StockDao.class, StockDaoImpl.class);

        } else {
            throw new PersistentException("Implementation for database connection does not exist");
        }

    }

    @SuppressWarnings("unchecked")
    public <T extends Dao<?>> T createDao(Class<T> key) throws PersistentException {
        Class value = classes.get(key);
        /*LOGGER.error(key);
        LOGGER.error(classes);*/
        if(value != null) {
            try {
                Object dao = value.newInstance();
                return (T)dao;
            } catch(InstantiationException | IllegalAccessException e) {
                LOGGER.error("It is impossible to create data access object", e);
                throw new PersistentException(e);
            }
        }
        return null;
    }
}
