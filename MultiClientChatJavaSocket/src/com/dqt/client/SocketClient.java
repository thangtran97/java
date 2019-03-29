/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.client;

import com.dqt.core.Data;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tj
 */
public class SocketClient implements Runnable {

    private Socket socket;
    public ObjectInputStream in;
    public Client client;
    public boolean run;
    public Thread thread;

    public SocketClient(Client client, ObjectInputStream in) {
        //run=true;
        this.client = client;
        this.in = in;
        this.start();
    }

    public void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            Data data = client.getData();
            execute(data);
        }
    }

    public void execute(Data data) {
        if (data.getType().equals("online")) {
            client.tbUserOn.removeAll();
            Vector cols = new Vector();
            cols.add("Online");

            DefaultTableModel model = new DefaultTableModel(cols, 0);
            client.tbUserOn.setModel(model);

            for (int i = 0; i < data.getContent().size(); i++) {
                if (!client.username.equals(data.getContent().get(i))) {
                    String[] row = {data.getContent().get(i).toString()};
                    model.addRow(row);
                }
            }
        }

        if (data.getType().equals("newchat")) {
            client.userOn = data.getRecipient();
            client.addNewTab(data.getRecipient(), data.getContent());
            client.changePanel(client.pChat, client.pChat2);
        }

        if (data.getType().equals("send")) {
            if (!client.check(data.getSender())) {
                client.list.get(data.getSender()).append(data.getSender() + ":\t" + data.getContent().get(0).toString() + "\n");
            } else {
                client.sendData(new Data("newchat", data.getRecipient(), null, data.getSender()));
            }
        }

        if (data.getType().equals("logout")) {
            run = false;
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
