/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.antonfeel.testquest;

import javax.swing.*;
public class CustomListElement {
    private String textMessage;
    private ImageIcon icon;
    private String iconMessageType;
    
    public CustomListElement (String message, String type) {
        setTextMessage(message);
        setIconMessageType(type);
        setTextIcon(type);
    }
    
    public CustomListElement(String str, ImageIcon folder) {
        setTextMessage(str);
        setIcon(folder);
        setIconMessageType(folder.toString());
    }
    @Override
    public String toString() {
        return textMessage;
    }
    
    public String toStringWithIconMessage() {
        return "[" + iconMessageType + "] " + textMessage;
    }
    public String getTextMessage() {
        return textMessage;
    }
    public void setTextMessage(String reportMessage) {
        this.textMessage = reportMessage;
    }
    public ImageIcon getIcon() {
        return icon;
    }
    public void setTextIcon(String messType) {
        this.icon = new ImageIcon(messType);
    }
    public void setIcon(ImageIcon reportIcon) {
        this.icon = reportIcon;
    }
    public String getMessageType() {
        return iconMessageType;
    }
    public void setIconMessageType(String messageType) {
        this.iconMessageType = messageType;
    }
}