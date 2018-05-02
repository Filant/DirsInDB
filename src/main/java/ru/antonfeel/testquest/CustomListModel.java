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
import javax.swing.event.ListDataListener;
import java.util.Vector;

public class CustomListModel extends DefaultListModel {
    
    private CustomManagerListCollection collection;
    
    public CustomListModel () {
        this.collection= new CustomManagerListCollection();
    }
    public CustomListModel (CustomManagerListCollection collection) {
        this.collection= collection;
    }
    @Override
    public Object getElementAt (int index) {
        return this.collection.getCollection().get(index);
    }
    @Override
    public void insertElementAt (Object elem, int index) {
        this.collection.addMessage((CustomListElement)elem);
    }
    @Override
    public void addElement (Object elem) {
        this.collection.addMessage((CustomListElement)elem);
    }
    @Override
    public void add (int index, Object elem) {        
        this.collection.addMessage((CustomListElement)elem);
    }
    @Override
    public int getSize() {
        return collection.getNumMessages();
    }
    @Override
    public void addListDataListener(ListDataListener l) {}
    @Override
    public void removeListDataListener(ListDataListener l) {}
    
    
}