/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dqt.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author tj
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private String sender;
    private Vector content;
    private String recipient;

    public Data(String type, String sender, Vector content, String recipient) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Vector getContent() {
        return content;
    }

    public void setContent(Vector content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{type='" + type + "', sender='" + sender + "', content='" + content + "', recipient='" + recipient + "'}";
    }
}
