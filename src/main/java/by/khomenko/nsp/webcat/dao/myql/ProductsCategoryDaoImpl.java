package by.khomenko.nsp.webcat.dao.myql;

import by.khomenko.nsp.webcat.dao.ProductsCategoryDao;
import by.khomenko.nsp.webcat.entity.ProductsCategory;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductsCategoryDaoImpl extends BaseDaoImpl<ProductsCategory> implements ProductsCategoryDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ProductsCategoryDaoImpl.class);


    ProductsCategoryDaoImpl() throws PersistentException {
    }


    @Override
    public Integer create(ProductsCategory entity) throws PersistentException {
        return null;
    }

    @Override
    public ProductsCategory read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(ProductsCategory entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
