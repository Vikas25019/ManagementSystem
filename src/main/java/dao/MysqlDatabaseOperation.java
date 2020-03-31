package dao;

import java.sql.*;
import java.util.*;

public class MysqlDatabaseOperation<T> {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; //JDBC Driver Name
    final String DB_URL = "jdbc:mysql://localhost/client_database?autoReconnect=true&useSSL=false"; // JDBC DataBase Url
    final String USER = "root";
    final String PASS = "root";

    final String insertQuery = "INSERT INTO %s values ( %s )";
    final String selectQuery = "SELECT * FROM %s WHERE %s = %s";
    final String deleteQuery = "DELETE FROM %s where %s = %s";
    final String updateQuery = "UPDATE %s SET %s where %s = %s";
    final String checkQuery = "SELECT count(*) from %s WHERE %s = %s";

    private static MysqlDatabaseOperation jdbc;

    private MysqlDatabaseOperation() {

    }

    public static MysqlDatabaseOperation getInstance() {
        if (jdbc == null) {
            jdbc = new MysqlDatabaseOperation();
        }

        return jdbc;
    }

    private Connection mySqlDbConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        //Step 1 Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //Step 2 Open a connection
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

        return connection;
    }

    int insertIntoDatabase(T t, Map<String, String> data) throws SQLException, ClassNotFoundException {

        String tableName = t.getClass().getSimpleName().toLowerCase();
        Connection connection = this.mySqlDbConnection();
        StringBuilder insertValues = new StringBuilder();

        String select = "select * from %s";
        Statement statement = connection.createStatement();
        String query = String.format(select, tableName);
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData md = rs.getMetaData();

        int col = md.getColumnCount();
        List<String> listOfColumns = new ArrayList<>();
        for (int i = 1; i <= col; i++) {
            String columnName = md.getColumnName(i);
            listOfColumns.add(columnName);
        }

        if (data != null && data.size() > 0) {
            insertValues.append("?");
        }
        for (int i = 1; i < data.size(); i++) {
            insertValues.append(",?");
        }

        String insertSql = String.format(insertQuery, tableName, insertValues);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

        int i = 1;
        for (String result : listOfColumns) {
            preparedStatement.setString(i, data.get(result));
            i++;
        }

        int status = preparedStatement.executeUpdate();
        return status;

    }

    void retrieveFromDatabase(T t, Map<String, String> data) throws SQLException, ClassNotFoundException {
        String columnName = "";
        String select = "select * from %s";
        String tableName = t.getClass().getSimpleName().toLowerCase();
        String query = String.format(select, tableName);

        Set<String> columnsSet = data.keySet();
        for (String name : columnsSet) {
            columnName = name;
        }
        String id = data.get(columnName); //search element
        Connection conn = this.mySqlDbConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        ResultSetMetaData md = rs.getMetaData();
        int col = md.getColumnCount();
        columnsSet = new HashSet<>();
        for (int i = 1; i <= col; i++) {
            String column = md.getColumnName(i);
            columnsSet.add(column);
        }

        String selectSql = String.format(selectQuery, tableName, columnName, id);
        ResultSet results = statement.executeQuery(selectSql);

        while (results.next()) {
            columnsSet.forEach(columns -> System.out.format("%-20s", columns));
            System.out.println();
            for (String columns : columnsSet) {
                String result = results.getString(columns);
                System.out.format("%-20s", result);
            }
            System.out.println("\n");
        }

    }

    int updateInDatabase(T t, Map<String, String> data, String columnName) throws SQLException, ClassNotFoundException {
        String tableName = t.getClass().getSimpleName().toLowerCase();
        StringBuilder insertColumns = new StringBuilder();
        Set<String> listOfColumns = data.keySet();

        Iterator<String> columns = listOfColumns.iterator();
        insertColumns.append(columns.next() + "= ?");
        while (columns.hasNext()) {
            insertColumns.append(" , " + columns.next() + "= ? ");
        }

        Connection conn = this.mySqlDbConnection();

        String id = data.get(columnName);

        String updateSql = String.format(updateQuery, tableName, insertColumns, columnName, "?");

        PreparedStatement preparedStatement = conn.prepareStatement(updateSql);

        int i = data.size() + 1;
        preparedStatement.setString(i, id);

        int j = 1;
        for (String result : listOfColumns) {
            preparedStatement.setString(j, data.get(result));
            j++;
        }

        int status = preparedStatement.executeUpdate();
        return status;
    }

    void deleteFromDatabase(T t, Map<String, String> data) throws SQLException, ClassNotFoundException {
        String columnName = "";
        Set<String> columns = data.keySet();

        for (String name : columns) {
            columnName = name;
        }
        String id = data.get(columnName);
        String tableName = t.getClass().getSimpleName().toLowerCase();
        Connection conn = this.mySqlDbConnection();
        Statement statement = conn.createStatement();

        String deleteSql = String.format(deleteQuery, tableName, columnName, id);
        statement.executeUpdate(deleteSql);
        System.out.println("Data deleted...");

    }


    <P> boolean checkIdMysql(P p, Map<String, String> data) throws SQLException, ClassNotFoundException {
        String columnName = "";
        Set<String> column = data.keySet();
        for (String name : column) {
            columnName = name;
        }

        String tableName = p.getClass().getSimpleName().toLowerCase();
        Connection connection = this.mySqlDbConnection();

        String id = data.get(columnName);
        final String queryCheck = String.format(checkQuery, tableName, columnName, "?");
        final PreparedStatement preparedStatement = connection.prepareStatement(queryCheck);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            final int count = resultSet.getInt(1);
            if (count != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}