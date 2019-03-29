/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.client;

import com.dqt.core.Data;
import com.dqt.core.Message;
import com.dqt.dao.MessageDAO;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sun.awt.resources.awt;

/**
 *
 * @author tj
 */
public class Client extends javax.swing.JFrame {

    Socket client;
    String username;
    String userOn;
    static Hashtable<String, JTextArea> list = new Hashtable<>();
    ObjectInputStream in;
    ObjectOutputStream out;
    int numTabs = 0;
    MessageDAO messageDAO = new MessageDAO();

    /**
     * Creates new form Client
     */
    public Client() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    sendData(new Data("logout", username, null, "SERVER"));
                    System.exit(0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        initComponents();

        try {
            client = new Socket("localhost", 9000);
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changePanel(JPanel p1, JPanel p2) {
        p1.removeAll();
        p1.add(p2);
        p1.repaint();
        p1.revalidate();
    }

    public void addNewTab(String nick, Vector chat) {
        int index = numTabs;
        tabbedPane.add(createJPanel(chat), nick, index);
        tabbedPane.setTabComponentAt(index, new TabChat(this));
        tabbedPane.setSelectedIndex(index);
        numTabs++;
    }

    public String accChat() {
        int index = tabbedPane.getSelectedIndex();
        String td = tabbedPane.getTitleAt(index);
        return td;
    }

    public JPanel createJPanel(Vector chat) {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        JTextArea ta = createTextArea(15, 30, chat);
        list.put(this.userOn, ta);
        //Them cac thanh phan vao Panel
        panel.add(new JScrollPane(ta), 0);
        panel.add(createPanelSend(), 1);

        //Xu ly su kien button
        JPanel panel2 = (JPanel) panel.getComponent(1);
        JButton btn = (JButton) panel2.getComponent(1);
        btn.addActionListener(new Send(panel, this));

        return panel;
    }

    public JPanel createPanelSend() {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);
        JTextField text = new JTextField(10);
        JButton btn = new JButton("Send");
        panel.add(text, 0);
        panel.add(btn, 1);

        return panel;
    }

    public JTextArea createTextArea(int row, int col, Vector chat) {
        JTextArea ta = new JTextArea(row, col);
        if (!chat.isEmpty()) {
            for (int i = 0; i < chat.size(); i++) {
                Message message = new Message();
                message = (Message) chat.get(i);
                if (message.getSender().getUsername().equals(this.username)) {
                    ta.append(this.username + ":\t" + message.getContent() + "\n");
                } else {
                    ta.append(this.userOn + ":\t" + message.getContent() + "\n");
                }
            }
        }

        ta.setForeground(Color.BLACK);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        return ta;
    }

    public void removeTab(int index) {
        tabbedPane.remove(index);
        numTabs--;
    }

    public synchronized void sendData(Data data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Data getData() {
        Data data = null;
        try {
            data = (Data) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pMain = new javax.swing.JPanel();
        pLogin = new javax.swing.JPanel();
        lbLogin = new javax.swing.JLabel();
        pInput = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        pHome = new javax.swing.JPanel();
        jScollPanel = new javax.swing.JScrollPane();
        tbUserOn = new javax.swing.JTable();
        pChat = new javax.swing.JPanel();
        pChat2 = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        btnLogout = new javax.swing.JButton();
        lbUser = new javax.swing.JLabel();
        pRegister = new javax.swing.JPanel();
        lbRegister = new javax.swing.JLabel();
        pInput1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRegUser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnRegAcc = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        txtCfPass = new javax.swing.JPasswordField();
        btnRLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pMain.setLayout(new java.awt.CardLayout());

        pLogin.setPreferredSize(new java.awt.Dimension(695, 398));

        lbLogin.setForeground(new java.awt.Color(255, 0, 51));

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pInputLayout = new javax.swing.GroupLayout(pInput);
        pInput.setLayout(pInputLayout);
        pInputLayout.setHorizontalGroup(
            pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInputLayout.createSequentialGroup()
                .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(51, 51, 51)
                .addGroup(pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)))
        );
        pInputLayout.setVerticalGroup(
            pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnRegister)))
        );

        javax.swing.GroupLayout pLoginLayout = new javax.swing.GroupLayout(pLogin);
        pLogin.setLayout(pLoginLayout);
        pLoginLayout.setHorizontalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoginLayout.createSequentialGroup()
                .addGap(0, 208, Short.MAX_VALUE)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(173, 173, 173))
        );
        pLoginLayout.setVerticalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoginLayout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(pInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pMain.add(pLogin, "card2");

        tbUserOn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Online"
            }
        ));
        tbUserOn.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tbUserOnMouseClicked(evt);
            }
        });
        jScollPanel.setViewportView(tbUserOn);

        pChat.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout pChat2Layout = new javax.swing.GroupLayout(pChat2);
        pChat2.setLayout(pChat2Layout);
        pChat2Layout.setHorizontalGroup(
            pChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
            .addGroup(pChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
        );
        pChat2Layout.setVerticalGroup(
            pChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
            .addGroup(pChat2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );

        pChat.add(pChat2, "card3");

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lbUser.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout pHomeLayout = new javax.swing.GroupLayout(pHome);
        pHome.setLayout(pHomeLayout);
        pHomeLayout.setHorizontalGroup(
            pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pHomeLayout.createSequentialGroup()
                        .addGroup(pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(pChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addGroup(pHomeLayout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pHomeLayout.setVerticalGroup(
            pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pHomeLayout.createSequentialGroup()
                        .addComponent(pChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addGroup(pHomeLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jScollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout)))
                .addContainerGap())
        );

        pMain.add(pHome, "card4");

        pRegister.setPreferredSize(new java.awt.Dimension(695, 398));

        lbRegister.setForeground(new java.awt.Color(255, 0, 0));

        jLabel3.setText("Username");

        jLabel4.setText("Password");

        jLabel5.setText("Confirm Password");

        btnRegAcc.setText("Register");
        btnRegAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegAccActionPerformed(evt);
            }
        });

        btnRLogin.setText("Login");
        btnRLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pInput1Layout = new javax.swing.GroupLayout(pInput1);
        pInput1.setLayout(pInput1Layout);
        pInput1Layout.setHorizontalGroup(
            pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInput1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(43, 43, 43)
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRegUser)
                    .addComponent(txtPass)
                    .addComponent(txtCfPass))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInput1Layout.createSequentialGroup()
                .addComponent(btnRLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegAcc, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
        );
        pInput1Layout.setVerticalGroup(
            pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInput1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtRegUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(pInput1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegAcc)
                    .addComponent(btnRLogin)))
        );

        javax.swing.GroupLayout pRegisterLayout = new javax.swing.GroupLayout(pRegister);
        pRegister.setLayout(pRegisterLayout);
        pRegisterLayout.setHorizontalGroup(
            pRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pRegisterLayout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addGroup(pRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pRegisterLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(166, 166, 166))
        );
        pRegisterLayout.setVerticalGroup(
            pRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pRegisterLayout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(pInput1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pMain.add(pRegister, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbUserOnMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tbUserOn.getSelectedRow();
        if (numTabs == 0) {
            changePanel(pChat, pChat2);
        }

        this.userOn = tbUserOn.getValueAt(row, 0).toString().trim();

        if (check(this.userOn)) {
            sendData(new Data("newchat", this.username, null, this.userOn));
        }
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        if (txtUsername.getText().equals("")) {
            lbLogin.setText("Username Null!");
        } else if (String.copyValueOf(txtPassword.getPassword()).equals("")) {
            lbLogin.setText("Password Null!");
        } else {
            Vector login = new Vector();
            login.add(txtUsername.getText().trim());
            login.add(String.copyValueOf(txtPassword.getPassword()).trim());
            sendData(new Data("login", "client", login, "server"));
            Data data = getData();
            if (data.getType().equals("login")) {
                if (data.getContent().get(0).equals("wrong")) {
                    lbLogin.setText("Username/Password is incorect");
                } else if (data.getContent().get(0).equals("inuse")) {
                    lbLogin.setText("Account is alredy in use!");
                } else if (data.getContent().get(0).equals("true")) {

                    username = txtUsername.getText().trim();

                    lbUser.setText(this.username);
                    new SocketClient(this, in);
                    changePanel(pMain, pHome);

                }

            }

        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        changePanel(pMain, pRegister);
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnRegAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegAccActionPerformed
        if (txtRegUser.getText().equals("")) {
            lbRegister.setText("Username Null!");
        } else if (String.copyValueOf(txtPass.getPassword()).equals("")) {
            lbRegister.setText("PassWord Null!");
        } else if (String.copyValueOf(txtCfPass.getPassword()).equals("")) {
            lbRegister.setText("Comfirm PassWord Null!");
        } else if (!String.copyValueOf(txtPass.getPassword()).equals(String.copyValueOf(txtCfPass.getPassword()))) {
            lbRegister.setText("Your passwords were not matching!");
        } else {
            Vector register = new Vector();
            register.add(txtRegUser.getText().trim());
            register.add(String.copyValueOf(txtCfPass.getPassword()).trim());
            sendData(new Data("register", "client", register, "server"));
            Data data = getData();
            if (data.getType().equals("register")) {
                if (data.getContent().get(0).equals("already")) {
                    lbRegister.setText("Username is already taken!");
                } else if (data.getContent().get(0).equals("success")) {
                    lbRegister.setText("Success!");
                }

            }
        }

    }//GEN-LAST:event_btnRegAccActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        sendData(new Data("logout", this.username, null, "SERVER"));
        System.exit(0);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnRLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRLoginActionPerformed
        changePanel(pMain, pLogin);
    }//GEN-LAST:event_btnRLoginActionPerformed

    public boolean check(String nameCheck) {
        Enumeration er = list.keys();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
            if (name.equals(nameCheck)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {

        new Client().setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRLogin;
    private javax.swing.JButton btnRegAcc;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScollPanel;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbRegister;
    private javax.swing.JLabel lbUser;
    public javax.swing.JPanel pChat;
    public javax.swing.JPanel pChat2;
    private javax.swing.JPanel pHome;
    private javax.swing.JPanel pInput;
    private javax.swing.JPanel pInput1;
    private javax.swing.JPanel pLogin;
    private javax.swing.JPanel pMain;
    private javax.swing.JPanel pRegister;
    public javax.swing.JTabbedPane tabbedPane;
    public javax.swing.JTable tbUserOn;
    private javax.swing.JPasswordField txtCfPass;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtRegUser;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
