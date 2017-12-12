/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author rajib
 */
public class Downloader {

    /**
     * @param args the command line arguments
     */
    public static DownloadStatus downLoadStatusFrame;
    public static DownloadQueue downloadQueue;
    public static JMenuBar menubar;
    public static HashMap hashMap = new HashMap();
    
    public static void main(String[] args) 
    {
        downloadQueue = new DownloadQueue();
        
        downLoadStatusFrame = new DownloadStatus();
        downLoadStatusFrame.setVisible(true);
        

        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            public void run(){
                // what you want to do
                System.out.println("ShutdownHooked ");    
                downLoadStatusFrame.updateHistoryFile();
            }
        }
        ));
        
    }
    
    
    
    
    

    

}
