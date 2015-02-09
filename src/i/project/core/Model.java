/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i.project.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Model> get() {
        List<Model> models = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet result = null;
        ResultSetMetaData meta = null;
        String columnName[];
        int count = 0;
        try {
            conn = IProject.getConn();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            meta = result.getMetaData();
            count = meta.getColumnCount();
            columnName = new String[count];

            for (int i = 1; i <= count; i++) {
                columnName[i - 1] = meta.getColumnLabel(i);
            }

            while (result.next()) {
                Model model = new Model();
                model.TABLE_NAME = TABLE_NAME;
                model.PRIMARY_KEY = PRIMARY_KEY;

                for (String column : columnName) {
                    model.set(column, result.getString(column));
                }
                models.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IProject.close(conn, statement, result);
        }
        return models;
    }

    public List<Model> get_where(String param, String arg) {
        List<Model> models = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet result = null;
        ResultSetMetaData meta = null;
        String columnName[];
        int count = 0;
        try {
            conn = IProject.getConn();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + param
                    + " " + '=' + " " + '"' + arg + '"');
            meta = result.getMetaData();
            count = meta.getColumnCount();
            columnName = new String[count];

            for (int i = 1; i <= count; i++) {
                columnName[i - 1] = meta.getColumnLabel(i);
            }

            while (result.next()) {
                Model model = new Model();
                model.TABLE_NAME = TABLE_NAME;
                model.PRIMARY_KEY = PRIMARY_KEY;

                for (String column : columnName) {
                    model.set(column, result.getString(column));
                }
                models.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IProject.close(conn, statement, result);
        }
        return models;
    }

    /**
     * Insert
     */
    public void insert() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = IProject.getConn();
            statement = conn.prepareStatement(insertQuery());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Utils.alertBox("Error while inserting data to database", "Insert error");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IProject.close(conn, statement);
        }
    }

    /**
     * Update
     */
    public void update() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = IProject.getConn();
            statement = conn.prepareStatement(updateQuery());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Utils.alertBox("Error while update data to database", "Update error");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IProject.close(conn, statement);
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
        } finally {
            IProject.close(conn, statement);
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
