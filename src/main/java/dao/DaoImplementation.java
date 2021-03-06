package dao;

import pojo.Person;

import java.net.UnknownHostException;
import java.sql.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DaoImplementation<T , U> implements IDaoInterface<T, U> {
    MysqlDatabaseOperation<T> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();

    @Override
    public int create(T t, U u, LinkedHashMap<String, String> data) throws SQLException, ClassNotFoundException {
        int status = 0;
        if (u instanceof MysqlDatabaseOperation) {
            status = mysqlDatabaseOperation.insertIntoDatabase(t,data);
        }
        else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
        return status;
    }

    @Override
    public LinkedHashMap<String, String> retrieve(T t, U u, LinkedHashMap<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException {
        LinkedHashMap<String, String> viewData;
        if (u instanceof MysqlDatabaseOperation) {
            viewData = mysqlDatabaseOperation.retrieveFromDatabase(t,data);
        }  else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
        return viewData;
    }

    @Override
    public int update(T t, U u, LinkedHashMap<String, String> data, String columnName) throws SQLException, ClassNotFoundException, UnknownHostException {
        int status = 0;
        if (u instanceof MysqlDatabaseOperation) {
           status =  mysqlDatabaseOperation.updateInDatabase(t,data,columnName);
        }
        return status;
    }

    @Override
    public void delete(T t, U u, LinkedHashMap<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException {
        if (u instanceof MysqlDatabaseOperation) {
            mysqlDatabaseOperation.deleteFromDatabase(t,data);
        } else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
    }

    @Override
    public List<LinkedHashMap<String,String>> retrieveAll(T t, U u) throws SQLException, ClassNotFoundException {
        List<LinkedHashMap<String,String>> data;
        if (u instanceof MysqlDatabaseOperation) {
            data = mysqlDatabaseOperation.retrieveAll(t);
        } else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
        return data;
    }
    @Override
    public <P,E> boolean isIdPresent(P p,E e ,Map<String , String> data ) throws SQLException, ClassNotFoundException, UnknownHostException {
        if (e instanceof MysqlDatabaseOperation) {
            return mysqlDatabaseOperation.checkIdMysql(p,data);
        } else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
    }

}
