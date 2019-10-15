package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Order;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

import java.util.List;

public interface OrderDao extends Dao<Order> {

    List<Order> readOrdersByCustomerId(Integer customerId) throws PersistentException;

}
