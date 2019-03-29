/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.server;

import com.dqt.core.Data;
import com.dqt.core.Message;
import com.dqt.core.User;
import com.dqt.dao.MessageDAO;
import com.dqt.dao.UserDAO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tj
 */
public class ThreadSocket implements Runnable {

    public ServerSocket server;
    public Socket client;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public Server sv;
    private Thread thread;
    private boolean run;
    private String username;
    private String password;

    UserDAO userDao = new UserDAO();
    MessageDAO messageDAO = new MessageDAO();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ThreadSocket(Server sv, Socket socket) {
        this.sv = sv;
        this.client = socket;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            this.start();
        } catch (Exception e) {

        }

    }
    //Tao Luong ket noi cho client

    public void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            Data data = getData();
            Execute(data);
        }
    }

    public void Execute(Data data) {
        if (data.getType().equals("login")) {
            Vector check = new Vector();
            username = data.getContent().get(0).toString();
            password = data.getContent().get(1).toString();
            if (!sv.check(username)) {
                check.add("inuse");
                sendData(new Data("login", "Server", check, "Client"));
            } else if (!checkLogin(username, password)) {
                check.add("wrong");
                sendData(new Data("login", "Server", check, "Client"));
            } else {
                UserDAO userDao = new UserDAO();
                try {
                    User user = userDao.checkUsername(username);
                } catch (SQLException ex) {
                    Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                check.add("true");
                sendData(new Data("login", "Server", check, "Client"));
                sv.listUser.put(username, this);
                sv.sendAllUpdate();
            }

        }

        if (data.getType().equals("register")) {
            try {
                Vector check = new Vector();
                String username = data.getContent().get(0).toString();
                User user = userDao.checkUsername(username);

                if (user != null) {
                    check.add("already");
                    sendData(new Data("register", "Server", check, "Client"));
                } else {
                    user = new User(userDao.generateId(), data.getContent().get(0).toString(), data.getContent().get(1).toString());
                    userDao.addNewUser(user);
                    check.add("success");
                    sendData(new Data("register", "Server", check, "client"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (data.getType().equals("newchat")) {
            try {
                User sender = userDao.checkUsername(data.getSender());
                User recipient = userDao.checkUsername(data.getRecipient());
                Vector chat = messageDAO.getMessage(sender.getId(), recipient.getId());
                sv.sendNewChat(data, chat);
            } catch (SQLException ex) {
                Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (data.getType().equals("send")) {
            try {
                User sender = userDao.checkUsername(data.getSender());
                User recipient = userDao.checkUsername(data.getRecipient());

                Message message = new Message();
                message.setId(messageDAO.generateId());
                message.setContent(data.getContent().get(0).toString());
                message.setSender(sender);
                message.setRecipient(recipient);                
                messageDAO.addNewMessage(message);
                sv.sendOne(data);
            } catch (SQLException ex) {
                Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (data.getType().equals("logout")) {
            try {
                sv.sendLogout(data);
                sv.listUser.remove(username);
                sv.sendAllUpdate();
                run = false;
                in.close();
                out.close();
                client.close();
            } catch (Exception e) {
            }
        }

    }


    public synchronized void sendData(Data data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Data getData() {
        Data data = null;
        try {
            data = (Data) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public boolean checkLogin(String username, String password) {
        User user = null;
        try {
            user = userDao.checkLogin(username, password);
        } catch (SQLException ex) {
            Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user != null) {
            return true;
        }
        return false;
    }

}
