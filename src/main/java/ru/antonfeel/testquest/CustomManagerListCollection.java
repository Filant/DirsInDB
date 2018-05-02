/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.antonfeel.testquest;

import java.util.ArrayList;
import java.util.List;

public class CustomManagerListCollection {
    
    private List<CustomListElement> collection;
    private int numMessages;
    
    CustomManagerListCollection () {
        collection= new ArrayList<CustomListElement>();
        numMessages= 0;
    }
    public void addMessage (CustomListElement mess) {
        //System.out.println("addMessage"); //todo: del
        collection.add(mess);
        this.numMessages = collection.size();
    }
    public ArrayList<CustomListElement> getCollection() {
        return (ArrayList<CustomListElement>) collection;
    }
    public void setCollection(ArrayList<CustomListElement> col) {
        this.collection= col;
    }
    public int getNumMessages() {
        return numMessages;
    }
}