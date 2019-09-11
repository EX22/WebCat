package by.khomenko.nsp.webcat.dao.myql;

import by.khomenko.nsp.webcat.dao.CategoryDao;
import by.khomenko.nsp.webcat.entity.Category;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(CategoryDaoImpl.class);


    CategoryDaoImpl() throws PersistentException {
    }


    @Override
    public Integer create(Category entity) throws PersistentException {
        return null;
    }

    @Override
    public Category read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Category entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
