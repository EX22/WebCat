package by.khomenko.nsp.webcat.common.dao.mysql;

import by.khomenko.nsp.webcat.common.dao.pool.ConnectionPool;
import by.khomenko.nsp.webcat.common.dao.pool.PooledConnection;
import by.khomenko.nsp.webcat.common.dao.Dao;
import by.khomenko.nsp.webcat.common.entity.Entity;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

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
