/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConection;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.PriorityQueue;
import Information.BasePackage;

/**
 * Esta clase alamacena la información del usuario conectado. Almacenando la
 * información de su socket y su nombre de usuario con el cuál está almacenado
 * en la tabla hash
 * 
 * @author Juan Diego
 */
public class User {
    private String userName;
    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final PriorityQueue<BasePackage> toWrite;
    private final PriorityQueue<BasePackage> toRead;
    
    public User(Socket client, String userName){
        this.clientSocket = client;
        this.userName = userName;
        try{
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        toWrite = new PriorityQueue<>();
        toRead = new PriorityQueue<>();
    }
    
    public Socket getUserSocket(){
        return clientSocket;
    }

    public String getUserName() {
        return userName;
    }
}
