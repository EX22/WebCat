package by.khomenko.nsp.webcat.dao.myql;

import by.khomenko.nsp.webcat.dao.Dao;
import by.khomenko.nsp.webcat.dao.pool.ConnectionPool;
import by.khomenko.nsp.webcat.dao.pool.PooledConnection;
import by.khomenko.nsp.webcat.entity.Entity;
import by.khomenko.nsp.webcat.exception.PersistentException;

import java.sql.Connection;

public abstract class BaseDaoImpl <T extends Entity> implements Dao<T> {

    protected Connection connection;

    BaseDaoImpl() throws PersistentException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    public void setConnection(final Connection connectionVal) {
        this.connection = connectionVal;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        ConnectionPool.getInstance().freeConnection((PooledConnection) this.connection);
    }
}
