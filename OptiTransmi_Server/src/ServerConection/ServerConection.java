/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConection;

import java.net.ServerSocket;
import java.io.IOException;
import optitransmi_server.Singleton;

/**
 * Esta clase alamcena el socket del servidor. Implementa un hilo que se encarga
 * de escuchar y aceptar las solicitudes de conexión entrantes asignandoles un
 * nombre de usuario provicional mientras el usuario conectado se registra o 
 * inicia sesión
 * 
 * @author Juan Diego
 */
public class ServerConection extends Thread {
    private ServerSocket server;
    
    public ServerConection(int port){
        try{
            server = new ServerSocket(port);                                    //Inicia el socket del servidor
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.out.println("Error iniciando el servidor");
            System.exit(-1);
        }
    }
    
    /**
     * Hilo encargado de escuchar las solicitudes entrantes, se va a ejecutar
     * mientras el socket del servidor esté abierto
     */
    @Override
    public void run(){
        while(!server.isClosed()){
            String nextUserName = "Usuario";
            boolean availableName = false;
            
            //Se busca un nombre disponible en la hash para el proximo usuario a conectar
            while(!availableName){
                int userNum = (int)(Math.random() * Integer.MAX_VALUE);
                if(!Singleton.getSingleton().getActiveUsers().exist(nextUserName + userNum)){
                    nextUserName += userNum;
                    availableName = true;
                }
            }
            
            //Intenta aceptar la solicitud de conexión entrante
            try{
                UnLoggedUser clientAccepted = new UnLoggedUser(server.accept(), nextUserName);
                Singleton.getSingleton().getActiveUsers().put(nextUserName, clientAccepted);                                //Añade el socketl del cliente aceptado a la list
                System.out.println("Cliente aceptado " + nextUserName);
            } catch(IOException ioe){
                System.out.println("Se ha rechazado una solicitud");
            }
        }
    }
}
