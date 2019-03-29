/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.server;

import com.dqt.core.Data;
import com.dqt.core.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author tj
 */
public class Server {

    public static ThreadSocket threadSocket;
    public static ServerSocket server;
    public static Hashtable<String, ThreadSocket> listUser;

    public void begin() {
        try {
            listUser = new Hashtable<String, ThreadSocket>();
            server = new ServerSocket(9000);

            while (true) {

                try {
                    Socket client = server.accept();
                    new ThreadSocket(this, client);
                } catch (Exception e) {
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAllUpdate() {
        Enumeration e = listUser.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            listUser.get(name).sendData(new Data("online", "server", getAllName(), "client"));
        }
    }


    public void sendOne(Data data) {
        Enumeration e = listUser.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            if (data.getRecipient().equals(name)) {
                listUser.get(name).sendData(new Data("send", data.getSender(), data.getContent(), data.getRecipient()));
            }
        }
    }

    public void sendNewChat(Data data, Vector chat) {
        Enumeration e = listUser.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            if (data.getSender().equals(name)) {
                listUser.get(name).sendData(new Data("newchat", "SERVER", chat, data.getRecipient()));
            }
        }
    }

    public void sendLogout(Data data) {
        Enumeration e = listUser.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            if (data.getSender().equals(name)) {
                listUser.get(name).sendData(new Data("logout", "SERVER", null, "CLIENT"));
            }
        }
    }

    public Vector getAllName() {
        Enumeration e = listUser.keys();
        int size = listUser.size();
        Vector allname = new Vector();
        int i = 0;
        while (e.hasMoreElements()) {
            allname.add(e.nextElement().toString());
            i++;
        }
        return allname;
    }

    public boolean check(String nameCheck) {
        Enumeration er = listUser.keys();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
            if (name.equals(nameCheck)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) throws IOException {
        new Server().begin(); 

    }
}
