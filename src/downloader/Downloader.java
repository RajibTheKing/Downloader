/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    public static void main(String[] args) 
    {
        downLoadStatusFrame = new DownloadStatus();
        downLoadStatusFrame.setVisible(true);

        //String urlString = "https://www.planwallpaper.com/static/images/6768666-1080p-wallpapers.jpg";
        //String urlString = "https://streams.my-free-mp3.net/puWBZB:EHx1rB";
        String urlString = "http://download.music.com.bd/Music/C/Cactus/Cactus%20-%20Nil%20Nirjone%20(music.com.bd).mp3";

        downloadQueue = new DownloadQueue();
        
    }
    
    
    

    

}
