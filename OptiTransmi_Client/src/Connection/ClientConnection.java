/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 *
 * @author Juan Diego
 */
public class ClientConnection extends Thread {
    private final int port;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public ClientConnection(int port){
        this.port = port;
        connect();
    }
    
    public boolean isConnected(){
        return !socket.isClosed();
    }
    
    public int getPort(){
        return port;
    }
    
    public void connect(){
        try {
            socket = new Socket("localhost", port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
}
