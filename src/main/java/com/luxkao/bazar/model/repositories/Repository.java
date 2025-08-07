package com.luxkao.bazar.model.repositories;

import java.sql.SQLException;
import java.util.List;

public interface Repository<K, E> {

    public void create(E entity) throws SQLException;
    public void update(E entity) throws SQLException;
    public E read(K key) throws SQLException;
    public void delete(K key) throws SQLException;
    public List<E> readAll() throws SQLException;

}