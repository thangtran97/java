/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.client;

import com.dqt.core.Data;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author tj
 */
public class Send extends AbstractAction {

    private JPanel panel;
    private int tab;
    private Client client;
    private String userOn;

    public Send(JPanel panel, Client client) {
        this.panel = panel;
        this.tab = tab;
        this.client = client;
        this.userOn = client.userOn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JPanel panel2 = (JPanel) panel.getComponent(1);
        JTextField chat = (JTextField) panel2.getComponent(0);
        String message = chat.getText();
        Vector text = new Vector();
        text.add(message);
        client.sendData(new Data("send", client.username, text, client.accChat()));
        //Dua len tab
        Enumeration e = client.list.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            if (name.equals(userOn)) {
                client.list.get(name).append(client.username+":\t" + message + "\n");
            }
        }
        chat.setText(null);

    }
}
