package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Customer;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

public interface CustomerDao extends Dao<Customer> {

    Customer read(String login, String password) throws PersistentException;
    void updateCustomerNameLastNamePhone(Integer customerId, String name,
                                         String lastName, String customerPhone)
            throws PersistentException;
    void updateCustomerPass(Integer customerId, String password) throws PersistentException;
    boolean isCustomerExist(String login) throws PersistentException;

}

