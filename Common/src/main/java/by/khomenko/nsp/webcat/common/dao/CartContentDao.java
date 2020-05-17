package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.CartContent;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

public interface CartContentDao extends Dao<CartContent> {

    void deleteByProductId(Integer productId) throws PersistentException;


}
