/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i.project.core;

import i.project.config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auliayf
 */
public class IProject {

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD);
        } catch (Exception e) {
            Utils.alertBox("Can't connect to database", "Database connection error");
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(IProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(Connection conn, Statement statement) {
        try {
            conn.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(Connection conn, Statement statement, ResultSet result) {
        try {
            conn.close();
            statement.close();
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(IProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(Connection conn, PreparedStatement statement) {
        try {
            conn.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void close(Connection conn, PreparedStatement statement, ResultSet result) {
        try {
            conn.close();
            statement.close();
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(IProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
