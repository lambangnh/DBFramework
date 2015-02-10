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

    public static final String[] TAG = {" = " + '"', String.valueOf('"')};

    public static final String[] LIKE_BOTH = {" LIKE '%", "%'"};
    public static final String[] LIKE_BEFORE = {" LIKE '%", "'"};
    public static final String[] LIKE_AFTER = {" LIKE '", "%'"};

    public static final String JOIN_EMPTY = " ";
    public static final String JOIN_RIGHT = " RIGHT ";
    public static final String JOIN_LEFT = " LEFT ";
    
    public static final String OR = " OR ";
    public static final String COMMA = ", ";
    public static final String AND = " AND ";

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
            String query = "SELECT * FROM " + TABLE_NAME;
            if (arg.length() > 0) {
                query += " WHERE " + param + " " + '=' + " " + '"' + arg + '"';
            }
            result = statement.executeQuery(query);
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

    public List<Model> like(String[] param) {
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
            result = statement.executeQuery(likeQuery(param));
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
    
    public List<Model> join(Model bModel, String param) {
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
            result = statement.executeQuery(
                    "SELECT * FROM " + TABLE_NAME + param + "JOIN " 
                            + bModel.TABLE_NAME + " ON " + bModel.TABLE_NAME + "." + bModel.PRIMARY_KEY 
                            + " = " + TABLE_NAME + "." + bModel.PRIMARY_KEY);
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
        String query = "INSERT INTO " + TABLE_NAME
                + " (" + Utils.implode_k(mMap, COMMA)
                + ") VALUES (" + Utils.implode_v(mMap, COMMA, '"') + ")";
        return query;
    }

    /**
     * Query builder for update
     *
     * @return Update Query
     */
    private String updateQuery() {
        String query = "UPDATE " + TABLE_NAME + " SET "
                + Utils.implode_kv(mMap, COMMA, TAG)
                + " WHERE " + PRIMARY_KEY + " = " + '"' + mMap.get(PRIMARY_KEY) + '"';
        ;

        return query;
    }
    
    /**
     * Query builder for like
     * @param param Tag
     * @return      Like Query
     */
    private String likeQuery(String[] param) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + Utils.implode_kv(mMap, OR, param);
        return query;
    }
}
