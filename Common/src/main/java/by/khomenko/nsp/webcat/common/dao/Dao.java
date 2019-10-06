package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Entity;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

import java.sql.Connection;

public interface Dao<T extends Entity> extends AutoCloseable {

    Integer create(T entity) throws PersistentException;

    T read(Integer identity) throws PersistentException;

    void update(T entity) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    void setConnection(Connection connection);
}
