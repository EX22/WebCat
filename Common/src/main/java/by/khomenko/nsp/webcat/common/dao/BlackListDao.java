package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.BlackList;
import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

public interface BlackListDao extends Dao<BlackList> {

    boolean isCustomerInBlacklist(Customer customer) throws PersistentException;

}
