/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.antonfeel.testquest;

/**
 *
 * @author Anton
 */
import javax.swing.*;
import java.awt.*;

public class CustomListRenderer extends DefaultListCellRenderer {
    
    public CustomListRenderer() {}
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        System.out.println("!!!"); //todo: del
        
        if (value instanceof CustomListElement) {
            CustomListElement listElement = (CustomListElement) value;
            String message = listElement.getTextMessage();
            ImageIcon icon = listElement.getIcon();
            JLabel label = (JLabel) super.getListCellRendererComponent(list, message, index, isSelected, hasFocus);
            label.setIcon(icon);
            return label;
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
    }
}

