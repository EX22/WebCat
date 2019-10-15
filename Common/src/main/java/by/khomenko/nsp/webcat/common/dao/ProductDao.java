package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Category;
import by.khomenko.nsp.webcat.common.entity.Product;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

import java.util.List;


public interface ProductDao extends Dao<Product> {


    List<Product> readAll() throws PersistentException;

    List<Product> readProductsByCategoryId(Integer categoryId) throws PersistentException;

}
