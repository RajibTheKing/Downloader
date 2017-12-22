/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;

import static downloader.Downloader.menubar;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.*;

/**
 *
 * @author rajib
 */
public class DownloadStatus extends javax.swing.JFrame {

    /**
     * Creates new form DownloadStatus
     */
    DefaultTableModel dm;
    DownloadThread dt;
    JMenuBar menubar;
    public DownloadStatus() {
        initComponents();
        setLocationRelativeTo(null);
        initializeTable();
        readHistory();
        initializeMenuItem();
        this.dt = new DownloadThread(this);
        
        //AddNewRow("www.google.com", "Processing");
        //AddNewRow("www.FaceBook.com", "Processing");
        
        

    }
    
    private void initializeTable()
    {
        dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"URL", "Status", "Datetime"};
        dm.setColumnIdentifiers(header);
        downloadTable.setModel(dm);
        
        Rectangle r = this.getBounds();
        int h = r.height;
        int w = r.width;
        System.out.println("");
        downloadTable.getColumnModel().getColumn(0).setPreferredWidth(70 * w / 100);
        downloadTable.getColumnModel().getColumn(1).setPreferredWidth(10 * w / 100);
        downloadTable.getColumnModel().getColumn(2).setPreferredWidth(20 * w / 100);
        
        
    }
    
    private void readHistory() 
    {
        BufferedReader br = null;
        String everything = "";
        try {
            br = new BufferedReader(new FileReader("history.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            br.close();
            System.out.println("TheKing--> readHistory: " + everything);
            
                    
            JSONObject obj = new JSONObject(everything);

            //List<String> list = new ArrayList<String>();
            
            JSONArray array = obj.getJSONArray("entry");
            for(int i = 0 ; i < array.length() ; i++)
            {
                //list.add(array.getJSONObject(i).getString("interestKey"));
                String fileurl = array.getJSONObject(i).getString("URL");
                String status = array.getJSONObject(i).getString("Status");
                String dateTime = array.getJSONObject(i).getString("Datetime");
                Downloader.hashMap.put(fileurl, new Boolean(true));
                AddRow(fileurl, status, dateTime);
            }


            

            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
    
    private void SelectFileFromDesktop()
    {
        JFileChooser jfc = new JFileChooser("./");

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
                
            try {
                Scanner scan = new Scanner(selectedFile);
                while(scan.hasNext())
                {
                    String nextUrlString = scan.nextLine();
                    System.out.println(nextUrlString);
                    Downloader.downloadQueue.AddNewLinkToQueue(nextUrlString);
                    
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DownloadStatus.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }
    
    private void AskUserFordata() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.requestFocus();
        textArea.requestFocusInWindow();
        scrollPane.setPreferredSize(new Dimension(800, 600));
        JOptionPane.showMessageDialog(this, scrollPane,"Paste Info", JOptionPane.PLAIN_MESSAGE);
        String info = textArea.getText();
        if(info.length() == 0)
        {
            JOptionPane.showMessageDialog(this, "User input is not found");
        }
        else
        {
            String linkList[] = info.split("\n");
            for(String value: linkList)
            {
                System.out.println("TheKing--> value = "+ value);
                Downloader.downloadQueue.AddNewLinkToQueue(value);
            }   
               
        }
    }

    private void initializeMenuItem() 
    {
        menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        
        JMenuItem fileListOfLink = new JMenuItem("Add list of Links From a file ");
        fileListOfLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Inside New ");
                SelectFileFromDesktop();
            }
        });
        
        JMenuItem userInputListOfLink = new JMenuItem("Add list from User Input");
        userInputListOfLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Inside Add list from User Input");
                AskUserFordata();
            }
        });
        
        
        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        file.add(fileListOfLink);
        file.add(userInputListOfLink);
        file.add(fileExit);
        
        JMenu tools = new JMenu("Tools");
        JMenuItem toolsOption = new JMenuItem("Current Status");
        tools.add(toolsOption);
        toolsOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rajib Chandra Das");
            }
        });
        JMenu help = new JMenu("Help");

        JMenuItem helpAbout = new JMenuItem("About");

        helpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "TheKing-Downloader\nCreated By: \nRajib Chandra Das\nShahjalal University of Science & Technology\nSoftware Version: 1.0", "About", 1);
            }
        });
        help.add(helpAbout);
        menubar.add(file);
        menubar.add(tools);
        menubar.add(help);
        
        setJMenuBar(menubar);

    }
    
    
    public void AddRow(String fileUrl, String status, String dateTime)
    {
        Vector<Object> data = new Vector<Object>();
        data.add(fileUrl);
        data.add(status);
        data.add(dateTime);
        
        dm.addRow(data);
    }
    
    public void updateRow(int row, int percentage)
    {
       dm.setValueAt(percentage+"%", row, 1);
       progressBar.setValue(percentage);
       downloadTable.scrollRectToVisible(downloadTable.getCellRect(downloadTable.getRowCount()-1, 0, true));
       
    }
     
    void updateRow(int row, String str) 
    {
        dm.setValueAt(str, row, 1);
    }
    
    public void AddNewRowAndStartDownload(String fileUrl, String status)
    {
        Vector<Object> data = new Vector<Object>();
        data.add(fileUrl);
        data.add(status);
        Date date = new Date();
        data.add(date.toString());
        
        dm.addRow(data);
        
        this.dt = new DownloadThread( this);
        this.dt.startDownload(dm.getRowCount() - 1, fileUrl);
    }
    
    
    public boolean getDownloadSlotAvailable()
    {
        return this.dt.getDownloadSlotAvailable();
    }
    
    public void updateHistoryFile() {

        JSONObject jsonData = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        int nRow = this.dm.getRowCount(), nCol = this.dm.getColumnCount();
        try {
            //Object[][] tableData = new Object[nRow][nCol];
            for (int i = 0; i < nRow; i++) {
                //for (int j = 0 ; j < nCol ; j++)
                //tableData[i][j] = dtm.getValueAt(i,j);
                JSONObject detailJson = new JSONObject();
                detailJson.put("URL", this.dm.getValueAt(i, 0).toString());
                detailJson.put("Status", this.dm.getValueAt(i, 1).toString());
                detailJson.put("Datetime", this.dm.getValueAt(i, 2).toString());
                jsonArray.put(detailJson);
            }
            jsonData.put("entry", jsonArray);
            System.out.println("TheKing--> jsonData: " + jsonData.toString());
            
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("history.json"), "utf-8"));
            
            writer.write(jsonData.toString());
            
            writer.close();
            

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        downloadTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton_ClearLog = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        downloadTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "URL", "Status", "Datetime"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(downloadTable);

        jLabel1.setText("TheKing-Downloader");

        jButton_ClearLog.setText("Clear Log");
        jButton_ClearLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ClearLogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(419, 419, 419)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(444, 444, 444)
                                .addComponent(jButton_ClearLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22)))
                        .addGap(422, 422, 422)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton_ClearLog)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ClearLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ClearLogActionPerformed
        // TODO add your handling code here:
        if (this.dm.getRowCount() > 0) 
        {
            for (int i = this.dm.getRowCount() - 1; i > -1; i--) 
            {
                this.dm.removeRow(i);
            }
        }
    }//GEN-LAST:event_jButton_ClearLogActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable downloadTable;
    private javax.swing.JButton jButton_ClearLog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables


}
