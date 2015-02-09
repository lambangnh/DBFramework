/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i.project.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auliayf
 */
public class Model {

    private HashMap<String, String> mMap = new HashMap<String, String>();
    public String TABLE_NAME = "";
    public String PRIMARY_KEY = "";

    /**
     * Setter
     *
     * @param key Key for map
     * @param val Value for map
     */
    public void set(String key, String val) {
        this.mMap.put(key, val);
    }

    /**
     * Getter
     *
     * @param key Key for map
     * @return Value from map
     */
    public String get(String key) {
        return this.mMap.get(key);
    }

    /**
     * Insert
     */
    public void insert() {
        Connection conn = null;
        PreparedStatement statement;
        try {
            conn = IProject.getConn();
            statement = conn.prepareStatement(insertQuery());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Utils.alertBox("Error while inserting data to database", "Insert error");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Update
     */
    public void update() {
        Connection conn = null;
        PreparedStatement statement;
        try {
            conn = IProject.getConn();
            statement = conn.prepareStatement(updateQuery());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Utils.alertBox("Error while update data to database", "Update error");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete
     */
    public void delete() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = IProject.getConn();
            statement = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY + " = ?");

            statement.setString(1, mMap.get(PRIMARY_KEY));

            statement.executeUpdate();
        } catch (SQLException ex) {
            Utils.alertBox("Error while deleting data from database", "Delete error");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Query builder for insert
     *
     * @return Insert Query
     */
    private String insertQuery() {
        String query = "INSERT INTO " + TABLE_NAME + " (";

        int i = 0;
        for (Map.Entry<String, String> entry : mMap.entrySet()) {
            query += entry.getKey();
            if (i != (mMap.size() - 1)) {
                query += ", ";
            } else {
                query += ") VALUES(";
            }
            i++;
        }
        i = 0;
        for (Map.Entry<String, String> entry : mMap.entrySet()) {
            query += '"' + entry.getValue() + '"';
            if (i != (mMap.size() - 1)) {
                query += ", ";
            } else {
                query += ")";
            }
            i++;
        }
        return query;
    }

    /**
     * Query builder for update
     *
     * @return Update Query
     */
    private String updateQuery() {
        String query = "UPDATE " + TABLE_NAME + " SET ";

        int i = 0;
        for (Map.Entry<String, String> entry : mMap.entrySet()) {
            if (!entry.getKey().equals(PRIMARY_KEY)) {
                query += entry.getKey() + " " + '=' + ' ' + '"' + entry.getValue() + '"';
                if (i != (mMap.size() - 2)) {
                    query += ", ";
                }
                i++;
            }
        }
        query += " WHERE " + PRIMARY_KEY + " " + '=' + " " + '"' + mMap.get(PRIMARY_KEY) + '"';
        return query;
    }
}
