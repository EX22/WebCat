package by.khomenko.nsp.webcat.common.dao;

import by.khomenko.nsp.webcat.common.entity.Contacts;
import by.khomenko.nsp.webcat.common.exception.PersistentException;

import java.util.List;

public interface ContactsDao extends Dao<Contacts> {

    List<Contacts> readList(Integer identity) throws PersistentException;
    Contacts readByContactsId(Integer identity) throws PersistentException;
}
