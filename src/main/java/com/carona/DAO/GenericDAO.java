package com.carona.DAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<E> extends Serializable {

    void insert(E e) throws SQLException;

    void update(E e) throws SQLException;

    void remove(E e) throws SQLException;

    E readById(E e) throws SQLException;

    List<E> readAll() throws SQLException;
}
