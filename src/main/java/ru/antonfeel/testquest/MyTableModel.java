/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.antonfeel.testquest;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Anton
 */
public class MyTableModel extends AbstractTableModel{
    private ArrayList<File> files;
    
    public MyTableModel(ArrayList<File> files) {
        this.files = files;
    }
        
    @Override
    public int getRowCount() {       
        return files.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rowIndex;
            case 1:
                return files.get(rowIndex).getName();
            case 2:
                return files.get(rowIndex).getParent();
            case 3:                
                return files.get(rowIndex).length();   
            default:
                return "";
        }
    }
    
    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case 0:
                result = "Номер";
                break;
            case 1:
                result = "Имя";
                break;
            case 2:
                result = "Расположение";
                break;
            case 3:
                result = "Размер";
        }
        return result;
    }
    
}
