/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.dao;

import com.dqt.core.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author tj
 */
public class MessageDAO {

    DBSingletonConnection dbSingletonConnection = DBSingletonConnection.getInstance();
    Connection conn = dbSingletonConnection.getConnection();
    Statement stmt;
    ResultSet rs;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    UserDAO userDao = new UserDAO();
    public int generateId() throws SQLException {
        String query = "select max(id) as maxId from t_messages ";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("maxId") + 1;
        } else {
            return 0;
        }
    }

    public boolean addNewMessage(Message message) throws SQLException {
        String query = "INSERT INTO t_messages(id,sender_id,recipient_id,content) VALUES (" + message.getId() + "," + message.getSender().getId() + "," + message.getRecipient().getId() + ",'" + message.getContent() + "')";

        stmt = conn.createStatement();
        int n = stmt.executeUpdate(query);
        if (n != 0) {
            return true;
        }
        return false;

    }

    public Vector getMessage(int id1, int id2) throws SQLException {
        Vector chat = new Vector();
        String query = "SELECT *FROM t_messages WHERE (sender_id=" + id1 + " and recipient_id =" + id2 + ") or (recipient_id =" + id1 + " and sender_id=" + id2 + ")";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        if (rs.next()) {
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setSender(userDao.getUserById(rs.getInt("sender_id")));
                message.setRecipient(userDao.getUserById(rs.getInt("recipient_id")));
                message.setContent(rs.getString("content"));
                
                chat.add(message);
            }
        }

        return chat;
    }

}
