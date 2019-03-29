/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tj
 */
public class DBSingletonConnection {

    private static DBSingletonConnection instance = null;

    private Connection connection = null;

    /**
     *
     */
    private DBSingletonConnection() {

        super();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CHAT", "root", "123456");
        } catch (SQLException ex) {
            Logger.getLogger(DBSingletonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the instance
     */
    public static synchronized DBSingletonConnection getInstance() {

        try {
            if (instance == null || instance.getConnection().isClosed()) {

                instance = new DBSingletonConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instance;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

}
