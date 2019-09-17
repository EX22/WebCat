package by.khomenko.nsp.webcat.dao;

import by.khomenko.nsp.webcat.exception.PersistentException;
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

            classes.put(AdministrationDao.class, by.khomenko.nsp.webcat.dao.mysql.AdministrationDaoImpl.class);
            classes.put(CategoryDao.class, by.khomenko.nsp.webcat.dao.mysql.CategoryDaoImpl.class);
            classes.put(CustomerDao.class, by.khomenko.nsp.webcat.dao.mysql.CategoryDaoImpl.class);
            classes.put(OrderDao.class, by.khomenko.nsp.webcat.dao.mysql.OrderDaoImpl.class);
            classes.put(OrderDetailsDao.class, by.khomenko.nsp.webcat.dao.mysql.OrderDetailsDaoImpl.class);
            classes.put(ProductDao.class, by.khomenko.nsp.webcat.dao.mysql.ProductDaoImpl.class);
            classes.put(ProductsCategoryDao.class, by.khomenko.nsp.webcat.dao.mysql.ProductsCategoryDaoImpl.class);
            classes.put(StockDao.class, by.khomenko.nsp.webcat.dao.mysql.StockDaoImpl.class);

        } else {
            throw new PersistentException("Implementation for database connection does not exist");
        }

    }

    @SuppressWarnings("unchecked")
    public <T extends Dao<?>> T createDao(Class<T> key) throws PersistentException {
        Class value = classes.get(key);
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
