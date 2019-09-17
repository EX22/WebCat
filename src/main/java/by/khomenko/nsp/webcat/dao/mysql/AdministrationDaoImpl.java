package by.khomenko.nsp.webcat.dao.mysql;

import by.khomenko.nsp.webcat.dao.AdministrationDao;
import by.khomenko.nsp.webcat.entity.Administration;
import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdministrationDaoImpl extends BaseDaoImpl<Administration> implements AdministrationDao {

    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(AdministrationDaoImpl.class);


    AdministrationDaoImpl() throws PersistentException {
    }

    @Override
    public Integer create(Administration entity) throws PersistentException {
        return null;
    }

    @Override
    public Administration read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Administration entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
