package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Cart;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

public interface CartDao extends Dao<Cart> {

    Cart readCartByCustomerId(Integer customerId) throws PersistentException;
    Cart loadCartProductInfo(Cart cart) throws PersistentException;
}
