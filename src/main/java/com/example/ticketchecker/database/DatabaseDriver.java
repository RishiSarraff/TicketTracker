package com.example.ticketchecker.database;

import com.example.ticketchecker.model.Member;

import java.sql.*;
import java.util.List;

public class  DatabaseDriver {

    private final String sqliteDB;

    private static DatabaseDriver db;

    private Connection connection;

    public boolean isConnected = false;

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
                                  "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
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


    public void createBoardTable() {
        try{
            String createBoardString = "CREATE TABLE IF NOT EXISTS Board(" +
                    "MemberID INTEGER PRIMARY KEY NOT NULL," +
                    "UserID INTEGER, " +
                    "PermissionLevel TEXT NOT NULL, " +
                    "ChairPosition TEXT NOT NULL," +
                    "FirstName TEXT NOT NULL," +
                    "LastName TEXT NOT NULL," +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID)" +
                    ")";
            
            Statement statement = connection.createStatement();
            statement.execute(createBoardString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoBoardTable(List<Member> boardList) throws SQLException {

        try{
            for(int i = 0; i<boardList.size(); i++){
                Member currMember = boardList.get(i);
                String memberPermissionLevel = currMember.getPermissionLevel();
                String memberPosition = currMember.getBoardPosition();
                String firstName = currMember.getFirstName();
                String lastName = currMember.getLastName();
                String boardInfo = String.format("""
                                                INSERT INTO Board(PermissionLevel, ChairPosition, FirstName, LastName)
                                                values ('%s', '%s', '%s', '%s');
                                                """, memberPermissionLevel, memberPosition, firstName, lastName);

                Statement statement = connection.createStatement();
                statement.executeUpdate(boardInfo);
            }

        }
        catch(SQLException e){
            rollback();
            throw e;
        }
    }

    public String getPermissionLevel(String firstName, String lastName) throws SQLException {
        try{
            String pLevel = String.format("""
                                          SELECT PermissionLevel FROM Board WHERE FirstName = '%s' AND LastName = '%s'
                                          """, firstName, lastName);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(pLevel);

            String answer = rs.getString("PermissionLevel");
            return answer;

        }
        catch(SQLException e){
            throw e;
        }
    }

    public String getChairPosition(String firstName, String lastName) throws SQLException {
        try{
            String pLevel = String.format("""
                                          SELECT ChairPosition FROM Board WHERE FirstName = '%s' AND LastName = '%s'
                                          """, firstName, lastName);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(pLevel);

            String answer = rs.getString("ChairPosition");
            return answer;

        }
        catch(SQLException e){
            throw e;
        }
    }

    public void clearTable(String tableName) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String deletionQuery = "DROP TABLE " + tableName;
            statement.executeUpdate(deletionQuery);

        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }

    public void setBoardID(String username, String password, String firstName, String lastName) {
        try{
            // we get the user id from users table and change board user id to the user id value.
            String setter = String.format("""
                                          UPDATE Board SET UserID =
                                          (SELECT UserID FROM Users WHERE Username = '%s' AND Password = '%s')
                                          WHERE FirstName = '%s' AND LastName = '%s'
                                          """, username, password, firstName, lastName);

            Statement statement = connection.createStatement();
            statement.executeUpdate(setter);

        }
        catch(SQLException e){

        }
    }

}
