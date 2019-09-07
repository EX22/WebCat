package by.khomenko.nsp.webcat.dao;

import by.khomenko.nsp.webcat.entity.Product;
import by.khomenko.nsp.webcat.exception.PersistentException;

public interface ProductDao extends Dao<Product> {

    int count() throws PersistentException;

}
