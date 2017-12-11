/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author rajib
 */
public class Downloader {

    /**
     * @param args the command line arguments
     */
    public static JFrame frame;
    
    public static void main(String[] args) {
        frame = new DownloadStatus();
        frame.setVisible(true);
        //downloadStatusFrame.setVisible(true);
        // TODO code application logic here
        // Create a new trust manager that trust all certificates
        
        //String urlString = "https://www.planwallpaper.com/static/images/6768666-1080p-wallpapers.jpg";
        //String urlString = "https://streams.my-free-mp3.net/puWBZB:EHx1rB";
        String urlString = "http://download.music.com.bd/Music/C/Cactus/Cactus%20-%20Nil%20Nirjone%20(music.com.bd).mp3";

        URL url;
        try {
            url = new URL(urlString);
            System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
            System.out.println(FilenameUtils.getExtension(url.getPath())); // -> xml
            System.out.println(FilenameUtils.getName(url.getPath())); // -> file.xml

            //saveFileFromUrlWithCommonsIO(urlString, url.getPath());
            //downloadFile(urlString, FilenameUtils.getBaseName(url.getPath()), FilenameUtils.getName(url.getPath()));
            TheKing_DownloadFileFromURL(urlString, "./downloaded/"+FilenameUtils.getName(url.getPath()).replaceAll("[%&$:*#@!~/]", ""));
            

        } catch (Exception ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Do you want to download an image?
    // But are u denied access?
    // well here is the solution.
    public static void TheKing_DownloadFileFromURL(String search, String path) throws IOException {

        // This will get input data from the server
        InputStream inputStream = null;

        // This will read the data from the server;
        OutputStream outputStream = null;

        try {
            // This will open a socket from client to server
            URL url = new URL(search);

            // This user agent is for if the server wants real humans to visit
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

            // This socket type will allow to set user_agent
            URLConnection con = url.openConnection();

            // Setting the user agent
            con.setRequestProperty("User-Agent", USER_AGENT);
            
            //Getting content Length
            int contentLength = con.getContentLength();
            System.out.println("File contentLength = " + contentLength + " bytes");


            // Requesting input data from server
            inputStream = con.getInputStream();

            // Open local file writer
            outputStream = new FileOutputStream(path);

            // Limiting byte written to file per loop
            byte[] buffer = new byte[2048];

            // Increments file size
            int length;
            int downloaded = 0; 
            
            // Looping until server finishes
            while ((length = inputStream.read(buffer)) != -1) 
            {
                // Writing data
                outputStream.write(buffer, 0, length);
                downloaded+=length;
                //System.out.println("Downlad Status: " + (downloaded * 100) / (contentLength * 1.0) + "%");
                
                        
            }
        } catch (Exception ex) {
            //Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        // closing used resources
        // The computer will not be able to use the image
        // This is a must
        outputStream.close();
        inputStream.close();
    }

}
