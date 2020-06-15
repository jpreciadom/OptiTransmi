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
import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */

public class ConnectionModel {
    
    private boolean connected;
    private final int port;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    public ConnectionModel(int port){
        this.port = port;
    };
    
    /**
     * Consulta si el socket esta conectado con el servidor
     * @return Verdadero si el socket esta conectado y falso en otro caso
     */
    public boolean isConnected(){
        return connected;
    }
    
    /**
     * Obtiene el puerto con el que se esta haciendo la conexion al servidor.
     * @return el numero de puerto con el que se esta estableciendo la conexion
     */
    public int getPort(){
        return port;
    }
    
    /**
     * Conecta el cliente con el servidor. Se encarga de hacer la conexión entre
     * el cliente y el servidor con el puerto establecido ajustando los Streams
     * de entrada y salida de datos
     */
    public synchronized void connect(){
        if(isConnected()){
            disconnect();
        }
        try {
            socket = new Socket("25.18.189.67", port);                          //Se conecta con el servidor
            output = new ObjectOutputStream(socket.getOutputStream());          //Obtiene el stream de salida
            input = new ObjectInputStream(socket.getInputStream());             //Obtiene el stream de entrada
            connected = true;
        } catch (IOException ex){
            System.out.println(ex.getMessage());                                //Imprime el mensaje de error
        }
    }
    
    public synchronized void disconnect(){
        try{
            this.socket.close();
            this.connected = false;
        } catch(IOException ex) {
            
        }
    }
    
    /**
     * Lee los datos disponible en el buffer. Se encarga de leer los objetos
     * que esten disponibles y los guarda en la cola de objetos leidos.
     * @return falso si ocurre alguna excepcion y verdadero en otro caso
     */
    public synchronized BasePackage read(){
        BasePackage readed = null;                                              //Indica si se pudo enviar o no el mensaje
        try{
            if(input.available() > 0){
                byte []enBuf = new byte[4];
                input.read(enBuf);
                readed = (BasePackage)input.readObject();                       //Lee el objeto
            }
        } catch(Exception ex){

        }
        return readed;                                                          //Devuelve la respuesta
    }
    
    /**
     * Envía un objeto al servidor. Saca el objeto que esté en la cabeza de la
     * cola de prioridad y lo intenta enviar, si no puede realizar el envío
     * vuelve a poner el objeto en la cola.
     * @return falso si ocurre alguna excepcion y verdadero en otro caso
     */
    public synchronized boolean send(BasePackage toSend){
        boolean answer;                                                         //Indica si se pudo enviar o no el mensaje
        if(toSend != null){
            byte []enBuf = new byte[4];
            try{
                output.write(enBuf);
                output.writeObject(toSend);                                     //Intenta hacer el envío, si puede
                answer = true;                                                  //Marca como verdadero el estado del envio si se pudo hacer
            } catch(IOException ex){
                disconnect();
                answer = false;                                                 //Marca como false el estado del envio
            }
        } else {
            answer = true;
        }
        return answer;                                                          //Devuelve la respuesta
    }
}
