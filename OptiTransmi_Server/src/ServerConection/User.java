/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConection;

import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase alamacena la información del usuario conectado. Almacenando la
 * información de su socket y su nombre de usuario con el cuál está almacenado
 * en la tabla hash
 * 
 * @author Juan Diego
 */
public class User {
    private final Socket clientSocket;
    private String userName;
    
    public User(Socket client, String userName){
        this.clientSocket = client;
        this.userName = userName;
    }
    
    public Socket getUserSocket(){
        return clientSocket;
    }

    public String getUserName() {
        return userName;
    }
}
