/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i.project.core;

import i.project.config.Config;
import java.sql.Connection;
import java.sql.DriverManager;

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
}
