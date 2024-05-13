package com.example.ticketchecker;

import java.sql.*;

public class  DatabaseDriver {

    private final String sqliteDB;

    private static DatabaseDriver db;

    private Connection connection;

    boolean isConnected = false;

    public DatabaseDriver(String dbName){
        this.sqliteDB = dbName;
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            isConnected = false;
            throw new IllegalStateException("Connection is already created");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteDB);
        String fKey = "PRAGMA foreign_keys = ON";
        Statement foreignKeyStatement = connection.createStatement();
        foreignKeyStatement.execute(fKey);

        connection.setAutoCommit(false);
        isConnected = true;
    }

    public static synchronized DatabaseDriver getInstance(String databaseName){
        if(db == null){
            db = new DatabaseDriver(databaseName);
        }
        return db;
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() throws SQLException {
        connection.close();
        isConnected = false;
    }

    public void createUserTable() throws SQLException{
        try{

            String userTableDb = "CREATE TABLE IF NOT EXISTS Users(" +
                                  "ID INTEGER PRIMARY KEY NOT NULL," +
                                  "Username TEXT NOT NULL," +
                                  "Password TEXT NOT NULL" +
                                  ")";

            Statement statement = connection.createStatement();
            statement.executeUpdate(userTableDb);
        }
        catch(SQLException e){
            rollback();
            throw e;
        }
    }

    public void insertUser(String user, String pass) throws SQLException{
        try{
                String insertString = String.format("""
                    INSERT into Users(Username, Password)
                     values ('%s', '%s');
                     """, user, pass);
                var statement = connection.createStatement();
            statement.executeUpdate(insertString);
        }
        catch(SQLException e){
            rollback();
            throw e;
        }
    }

    public boolean findUser(String user, String pass) throws SQLException{
        try{
            String findString = String.format("""
                    SELECT Username, Password From Users WHERE Username = '%s' AND Password = '%s'
                     """, user, pass);
            var statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(findString);

            return rs.next();
        }
        catch(SQLException e){
            rollback();
            throw e;
        }
    }






}
