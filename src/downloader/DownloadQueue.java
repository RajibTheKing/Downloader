/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author RajibTheKing
 */
public class DownloadQueue extends Thread
{

    Queue<String> queue = new LinkedList<String>();
    
    public DownloadQueue() 
    {
        this.start();
    }
    
    void AddNewLinkToQueue(String urlString)
    {
        System.out.println("TheKing--> Adding new url to Queue: "  + urlString);
        queue.add(urlString);
    }
    
    public void run()
    {
        try{
            while (true)
            {
                if(queue.isEmpty() == true)
                {
                    Thread.sleep(1000);
                }
                else if(Downloader.downLoadStatusFrame.getDownloadSlotAvailable() == false)
                {
                    Thread.sleep(1000);
                }
                else
                {
                    String urlString = this.queue.poll();
                    
                    if( Downloader.hashMap.containsKey(urlString) == false)
                    {
                        Downloader.hashMap.put(urlString, new Boolean(false));
                    }
                    
                    if(Downloader.hashMap.get(urlString).equals(new Boolean(false)))
                    {
                        System.out.println("Adding New Row with Url: " + urlString);
                        Downloader.downLoadStatusFrame.AddNewRowAndStartDownload(urlString, "Queued");
                    }
                    
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            
        }
    }
    
    
}
