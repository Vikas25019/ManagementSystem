package dao;

import java.net.UnknownHostException;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

public interface IDaoInterface<T, U> {
    int create(T t, U u, Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException;

    Map<String, String> retrieve(T t, U u, Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException;

    int update(T t, U u, Map<String, String> data ,String columnName) throws SQLException, ClassNotFoundException, UnknownHostException;

    void delete(T t, U u ,Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException;

    <P,E> boolean isIdPresent(P p, E e, Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException;

    List<List<String>> retrieveAll(T t,U u) throws SQLException, ClassNotFoundException;
}
