
package ru.antonfeel.testquest;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Anton
 */
public class UIClass extends JFrame{
    
    private JButton chooseButton = new JButton("Выбрать файлы");
    private JButton addButton = new JButton("Добавить в таблицу");
    private JButton deleteButton = new JButton("Удалить файл");
    private JButton backButton = new JButton("Назад");
    private JButton outload = new JButton("Выгрузить таблицу в БД");
    private JButton download = new JButton("Загрузить таблицу из БД");
    private JButton cleanTable = new JButton("Очистить таблицу");
    private JTable table = new JTable();
    private JPanel mainPanel = new JPanel();
    private JPanel rootDialogPanel = new JPanel();
    private ArrayList<File> files = new ArrayList<>();
    private JList filesList = new JList();
    private JList selectedFilesList = new JList();
    private JScrollPane forSelectedFilesList = new JScrollPane(selectedFilesList);
    private ImageIcon folderIcon = new ImageIcon("src/folder.png");
    private ImageIcon fileIcon = new ImageIcon("src/file.png");
    private JComboBox sortBox = new JComboBox();
    private JScrollPane filesScroll = new JScrollPane(filesList);
      
    public UIClass(){
        super("Table of files");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(1000, 700));
        getContentPane().add(mainPanel);
        
        MyTableModel mtModel = new MyTableModel(files);
        table.setModel(mtModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        RowSorter<TableModel> sorter = new TableRowSorter<>(mtModel);
        table.setRowSorter(sorter);
        //Добавление слушателя на кнопкку chooseButton, который формирует окно для просмотра файлов
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JDialog chooseFilesDialog = new JDialog(UIClass.this, "Выбор файлов", true);
                chooseFilesDialog.add(rootDialogPanel);
                File discs[] = File.listRoots();
                //В этом листе хранится полный путь к октрытому каталогу
                final ArrayList <String> cashPathFiles = new ArrayList <String>();               
                filesList.setCellRenderer(new CustomListRenderer());
                filesScroll.setPreferredSize(new Dimension(400, 500));                
                filesList.setListData(discs);
                filesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                //Добавление слушателя на открытие папок
                filesList.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount() == 2){
                            String selectedPath = filesList.getSelectedValue().toString();                           
                            String currentPath = !cashPathFiles.isEmpty() ? toAbsolutelPath(cashPathFiles) : "";
                            //Определяется открытие папки или диска
                            File file = currentPath.length()!= 0 ? new File(currentPath, selectedPath)
                                    : new File(selectedPath); 

                            if(file.isDirectory()){
                                String[] rootStr = file.list();                                                            
                                pathChange(rootStr, file.getPath());
                                if(cashPathFiles.size() <= 1){
                                    cashPathFiles.add(selectedPath);
                                }else{
                                    cashPathFiles.add("\\" + selectedPath);
                                } 
                            }                              
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                //Возврат назад по файловой системе
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(cashPathFiles.size() > 1){
                            cashPathFiles.remove(cashPathFiles.size() - 1);
                            String backPath = toAbsolutelPath(cashPathFiles);
                            String[] rootStr = new File(backPath).list();
                            pathChange(rootStr, backPath);                                                            
                        }else{
                            
                           filesList.setListData(discs);
                           cashPathFiles.removeAll(cashPathFiles);
                        }
                    }
                });
                //Добавление в таблицу выбранного файла
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {                       
                        ListModel model = filesList.getModel();
                        int [] selectedIndices = filesList.getSelectedIndices();                       
                        String parentPath = toAbsolutelPath(cashPathFiles);                       
                        for(int i : selectedIndices){
                            String selectedFileName = model.getElementAt(i).toString();
                            files.add(new File(parentPath, selectedFileName));
                        }   
                        mtModel.fireTableDataChanged();
                    }
                });
                                            
                rootDialogPanel.setLayout(new GridBagLayout());
                rootDialogPanel.add(backButton, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(10, 10, 6, 10), 0, 0));
                rootDialogPanel.add(filesScroll, new GridBagConstraints(0, 1, 2, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(6, 6, 6, 6), 0, 0));
                rootDialogPanel.add(addButton, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(10, 10, 6, 10), 0, 0));               
                           
                chooseFilesDialog.pack();
                chooseFilesDialog.setLocationRelativeTo(null);
                chooseFilesDialog.setVisible(true);
            }
        });
        
        deleteButton.addActionListener((ActionEvent e) -> {
            if(table.getSelectedRow() != -1){
                int selctedRow = table.convertRowIndexToModel(table.getSelectedRow());
                files.remove(selctedRow);
                mtModel.fireTableDataChanged();                       
            }
        });               
        
        outload.addActionListener((ActionEvent e) -> {
            ConnectorDB conn = new ConnectorDB(files);
            conn.create();
            conn.insert();
            conn.select();
        });
        
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectorDB conn = new ConnectorDB();
                files.removeAll(files);
                files.addAll(conn.selectToTable());
                mtModel.fireTableDataChanged();
            }
        });
        
        cleanTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                files.removeAll(files);
                mtModel.fireTableDataChanged();
            }
        });
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(scrollPane, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0));
        mainPanel.add(chooseButton, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                        GridBagConstraints.EAST, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0));
        mainPanel.add(deleteButton, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0));
        mainPanel.add(download, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.EAST, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0)); 
        mainPanel.add(outload, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                        GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0));
        mainPanel.add(cleanTable, new GridBagConstraints(0, 3, 2, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(1, 1, 6, 1), 0, 0));  
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);      
    }
    //Метод соединяющий куски пути из листа в строку       
    public String toAbsolutelPath (List<String> file){
        String listPart = "";
        for(String str : file)
            listPart = listPart + str;            
        return listPart;       
    }
    //Метод отрисовывает элементы открытой папки
    public void pathChange(String[] catalogUnit, String path){
        CustomListModel model = new CustomListModel();
        for (String str : catalogUnit) {
            File checkForHidden = new File (path, str);
            if(!checkForHidden.isHidden()){
                if(checkForHidden.isDirectory()){
                    model.addElement(new CustomListElement(str, folderIcon));
                }else{
                    model.addElement(new CustomListElement(str, fileIcon));
                }
            }               
        }
        filesList.setModel(model);
    }
}
