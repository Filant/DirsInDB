/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.antonfeel.testquest;


import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anton
 */
public class ConnectorDB {
    private String userName = "Anton";
    private String password = " ";
    private String url = "jdbc:derby://localhost:1527/AntonDB";
    private String driver = "org.apache.derby.jdbc.ClientDriver";
    ArrayList<File> filesInDB;
    ArrayList<File> filesOutDB = new ArrayList<>();
    
    public ConnectorDB(ArrayList<File> files){
        this.filesInDB = files;       
    }
    public ConnectorDB(){
           
    }
    
    public void create() {
        try {
            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connect = DriverManager.getConnection(url, userName, password)) {
                System.out.println("Connected to the db");
                Statement statement = connect.createStatement();
                DatabaseMetaData metaData = connect.getMetaData();
                ResultSet checkNewTable = metaData.getTables(null, "ANTON", "FILES", null);
                if(!checkNewTable.next()){
                    statement.executeUpdate("CREATE TABLE FILES ("
                            + "ID INTEGER not null primary key GENERATED ALWAYS AS IDENTITY"
                            + "(START WITH 1, INCREMENT BY 1),"
                            + "NAME varchar(60),"
                            + "PATH varchar(60),"
                            + "SIZE INTEGER)");
                }else{
                    System.out.println("This table is already created");
                }
                connect.close();
                System.out.println("Disconnected from the db");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert() {
        try {
            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connect = DriverManager.getConnection(url, userName, password)) {
                System.out.println("Connected to the db, start operarion Insert");
                Statement statement = connect.createStatement();
                for(int i = 0; i < filesInDB.size(); i++){
                    String name = filesInDB.get(i).getName();
                    String path = filesInDB.get(i).getParent();
                    int size = (int) filesInDB.get(i).length();
                    int newRow = statement.executeUpdate("INSERT INTO Anton.Files(NAME, PATH, SIZE) "
                            + "VALUES('"+ name+"','"+path+"',"+size+")");
                    if(newRow != 0){
                        System.out.println("New row aded");
                    }                   
                }
                connect.close();
                System.out.println("Disconnected from the db");
            }
            
           
        } catch (SQLException ex) {
            Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void select() {
        try {
            try {               
                try {
                    Class.forName(driver).newInstance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }               
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connect = DriverManager.getConnection(url, userName, password)) {
                System.out.println("Connected to the db, start operation Select");
                Statement statement = connect.createStatement();
                ResultSet resset = statement.executeQuery("SELECT * FROM Anton.Files");
                while(resset.next()){
                    System.out.print(resset.getInt("ID"));
                    System.out.print((" | "));
                    System.out.print(resset.getString("NAME"));
                    System.out.print((" | "));
                    System.out.print(resset.getString("PATH"));
                    System.out.print((" | "));
                    System.out.println(resset.getInt("SIZE"));
                }
            connect.close(); 
            System.out.println("Disconnected from the db, operation Select finished");
            }                                      
        } catch (SQLException ex) {
            Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<File> selectToTable(){                   
            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connect = DriverManager.getConnection(url, userName, password)) {
                System.out.println("Connected to the db");
                Statement statement = connect.createStatement();
                ResultSet resset = statement.executeQuery("SELECT * FROM Anton.Files");
                while(resset.next()){
                    filesOutDB.add(new File(resset.getString("path"), resset.getString("name")));
                    System.out.println("Aded new file in table");
                }
                connect.close();
                System.out.println("Disconnected from the db");               
            } catch (SQLException ex) {
            Logger.getLogger(ConnectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return filesOutDB;           
    }
}
