package ru.vsu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T>{
    Optional<T> get(int id);
    List<T> getAll();
    void create(T model);
    void update(T model);
    void delete(T model);
    //void setConnection(Connection connection);
}
