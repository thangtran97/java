/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.dao;

import com.dqt.core.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @user tj
 */
public class UserDAO {

    DBSingletonConnection dbSingletonConnection = DBSingletonConnection.getInstance();
    Connection conn = dbSingletonConnection.getConnection();
    Statement stmt;
    ResultSet rs;

    public int generateId() throws SQLException {
        String query = "select max(id) as maxId from t_users ";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("maxId") + 1;
        } else {
            return 0;
        }
    }

    public User getUserById(int id) throws SQLException {
        String query = "select * from t_users where id=" + id + "";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        } else {
            return null;
        }

    }

    public User checkLogin(String name, String pass) throws SQLException {
        String query = "SELECT *FROM t_users where username ='" + name + "' and password = '" + pass + "'";
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } else {
            return null;
        }
    }

    public User checkUsername(String name) throws SQLException {
        String query = "select * from t_users where username='" + name + "'";
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } else {
            return null;
        }
    }

    public boolean addNewUser(User user) throws SQLException {
        String query = "insert into t_users(id,username,password) values (" + user.getId() + ",'" + user.getUsername() + "','" + user.getPassword() + "')";
        stmt = conn.createStatement();
        int n = stmt.executeUpdate(query);
        // return (n!=0);
        if (n != 0) {
            return true;
        }
        return false;
    }

}
