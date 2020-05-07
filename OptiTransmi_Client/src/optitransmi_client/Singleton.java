/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optitransmi_client;

import Connection.ClientConnection;
import java.util.PriorityQueue;
import Information.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class Singleton {
    
    private final ClientConnection client;
    private final PriorityQueue<BasePackage> toWrite;
    private final PriorityQueue<BasePackage> toRead;
    
    private static Singleton singleton;
    
    public Singleton(){
        client = new ClientConnection(7777);
        toWrite = new PriorityQueue<>();
        toRead = new PriorityQueue<>();
    }
    
    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        
        return singleton;
    }
    
    public ClientConnection getClient(){
        return client;
    }
}
