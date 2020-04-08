package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.ProductsCategoryDao;
import by.khomenko.nsp.webcat.common.entity.ProductsCategory;
import by.khomenko.nsp.webcat.common.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductsCategoryDaoImpl extends BaseDaoImpl<ProductsCategory> implements ProductsCategoryDao {

    /**
     * Instance of logger that provides logging capability for this class
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ProductsCategoryDaoImpl.class);


    public ProductsCategoryDaoImpl() throws PersistentException {
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
