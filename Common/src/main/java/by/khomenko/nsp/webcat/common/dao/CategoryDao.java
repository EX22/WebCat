package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Category;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

import java.util.List;

public interface CategoryDao extends Dao<Category> {

    List<Category> readAll() throws PersistentException;
}
