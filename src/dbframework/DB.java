/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbframework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auliayf
 */
public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/ukom2";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection conn = null;

    private static final String INSERT_START = "INSERT INTO ";
    private static final String INSERT_MID = " VALUES ";

    private static final String DELETE_OPEN = "DELETE";

    private static final String UPDATE = "UPDATE ";

    private static final String QUERY_OPEN = "(";
    private static final String QUERY_MID = ", ";
    private static final String QUERY_CLOSE = ")";
    private static final String QUERY_FROM = " FROM ";
    private static final String QUERY_WHERE = " WHERE ";
    private static final String QUERY_SET = " SET ";
    private static final String QUERY_AND = " AND ";
    private static final String QUERY_OR = " OR ";

    /**
     * Get MySQL Connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASS);
            } catch (Exception ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    /**
     * Insert data to database
     *
     * @param tableName Table Name
     * @param args What To Insert
     */
    public static void insert(String tableName, String[][] args) {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(insertQueryBuilder(tableName, args));

            int i = 0;
            for (String[] field : args) {
                statement.setString(i, field[i]);
                i++;
            }

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete data from database
     *
     * @param tableName Table Name
     * @param args Where Arguments
     */
    public static void delete(String tableName, String[][] args) {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(deleteQueryBuilder(tableName, args));

            int i = 0;
            for (String[] field : args) {
                statement.setString(i, field[i]);
                i++;
            }

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Update data from database
     *
     * @param tableName Table Name
     * @param setArgs Set Arguments
     * @param whereArgs Where Arguments
     */
    public static void update(String tableName, String[][] setArgs, String[][] whereArgs) {
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(updateQueryBuilder(tableName, setArgs, whereArgs));
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Query builder for insert
     *
     * @param tableName Table Name
     * @param args What To Insert
     * @return Insert Query
     */
    private static String insertQueryBuilder(String tableName, String[][] args) {
        int i = 0;
        String query = INSERT_START + tableName + QUERY_OPEN;
        for (String[] field : args) {
            query += field[0];
            if (i != (args.length - 1)) {
                query += QUERY_MID;
            } else {
                query += QUERY_CLOSE;
            }
            i++;
        }
        i = 0;
        query += INSERT_MID + QUERY_OPEN;
        for (String[] field : args) {
            query += "?";
            if (i != (args.length - 1)) {
                query += QUERY_MID;
            } else {
                query += QUERY_CLOSE;
            }
            i++;
        }
        return query;
    }

    /**
     * Query builder for delete
     *
     * @param tableName Table Name
     * @param args Where Arguments
     * @return Delete Query
     */
    private static String deleteQueryBuilder(String tableName, String[][] args) {
        int i = 0;
        String query = DELETE_OPEN + QUERY_FROM + tableName + QUERY_WHERE;
        for (String[] field : args) {
            query += field[0] + " = ?";
            if (i != (args.length - 1)) {
                query += QUERY_AND;
            }
            i++;
        }
        System.out.println(query);
        return query;
    }

    /**
     * Query builder for update
     *
     * @param tableName Table Name
     * @param setArgs Set Arguments
     * @param whereArgs Where Arguments
     * @return Update Query
     */
    private static String updateQueryBuilder(String tableName, String[][] setArgs, String[][] whereArgs) {
        String query = UPDATE + tableName + QUERY_SET;
        int i = 0;
        for (String[] field : setArgs) {
            query += field[0] + " = '" + field[1] + "'";
            if (i != (setArgs.length - 1)) {
                query += QUERY_MID;
            }
            i++;
        }
        i = 0;
        query += QUERY_WHERE;
        for (String[] field : whereArgs) {
            query += field[0] + " = '" + field[1] + "'";
            if (i != (whereArgs.length - 1)) {
                query += QUERY_AND;
            }
            i++;
        }
        System.out.println(query);
        return query;
    }
}
