package dao;

import pojo.Person;

import java.net.UnknownHostException;
import java.sql.*;

import java.util.Map;

public class DaoImplementation<T extends Person, U> implements IDaoInterface<T, U> {
    MysqlDatabaseOperation<T> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();

    @Override
    public int create(T t, U u, Map<String, String> data) throws SQLException, ClassNotFoundException {
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
    public Map<String, String> retrieve(T t, U u, Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException {
        Map<String, String> viewData;
        if (u instanceof MysqlDatabaseOperation) {
            viewData = mysqlDatabaseOperation.retrieveFromDatabase(t,data);
        }  else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
        return viewData;
    }

    @Override
    public int update(T t, U u, Map<String, String> data, String columnName) throws SQLException, ClassNotFoundException, UnknownHostException {
        int status = 0;
        if (u instanceof MysqlDatabaseOperation) {
           status =  mysqlDatabaseOperation.updateInDatabase(t,data,columnName);
        }
        return status;
    }

    @Override
    public void delete(T t, U u, Map<String, String> data) throws SQLException, ClassNotFoundException, UnknownHostException {
        if (u instanceof MysqlDatabaseOperation) {
            mysqlDatabaseOperation.deleteFromDatabase(t,data);
        } else {
            throw new UnsupportedOperationException("Invalid Operation");
        }
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
