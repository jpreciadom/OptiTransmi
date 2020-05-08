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
import Information.BasePackage;
import optitransmi_client.Singleton;

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
        start();
    }
    
    /**
     * Consulta si el socket esta conectado con el servidor
     * @return Verdadero si el socket esta conectado y falso en otro caso
     */
    public boolean isConnected(){
        return !socket.isClosed();
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
    public void connect(){
        try {
            socket = new Socket("localhost", port);                             //Se conecta con el servidor
            output = new ObjectOutputStream(socket.getOutputStream());          //Obtiene el stream de salida
            input = new ObjectInputStream(socket.getInputStream());             //Obtiene el stream de entrada
        } catch (IOException ex){
            System.out.println(ex.getMessage());                                //Imprime el mensaje de error
        }
    }
    
    public void disconnect(){
        try{
            this.socket.close();
        } catch(IOException ex) {
            
        }
    }
    
    /**
     * Lee los datos disponible en el buffer. Se encarga de leer los objetos
     * que esten disponibles y los guarda en la cola de objetos leidos.
     * @return falso si ocurre alguna excepcion y verdadero en otro caso
     */
    private boolean read(){
        boolean answer;                                                         //Indica si se pudo enviar o no el mensaje
        try{
            BasePackage readed = (BasePackage)input.readObject();               //Lee el objeto
            Singleton.getSingleton().AddInToReadQueue(readed);                  //Lo agrega a la cola de objetos leidos
            answer = true;                                                      //Marca la respuesta como verdadero
            System.out.println("Objeto recibido");
        } catch(Exception ex){
            disconnect();                                                       //Imprime el mensaje de error
            answer = false;                                                     //Marca la respuesta como false
        }
        return answer;                                                          //Devuelve la respuesta
    }
    
    /**
     * Envía un objeto al servidor. Saca el objeto que esté en la cabeza de la
     * cola de prioridad y lo intenta enviar, si no puede realizar el envío
     * vuelve a poner el objeto en la cola.
     * @return falso si ocurre alguna excepcion y verdadero en otro caso
     */
    private boolean send(){
        boolean answer;                                                         //Indica si se pudo enviar o no el mensaje
        BasePackage toSend = Singleton.getSingleton().ReadInToWriteQueue();     //Saca el objeto a enviar de la cola de envios
        if(toSend != null){
            try{
                output.writeObject(toSend);                                     //Intenta hacer el envío, si puede
                System.out.println("Obeto enviado");
                answer = true;                                                  //Marca como verdadero el estado del envio si se pudo hacer
            } catch(IOException ex){
                disconnect();
                answer = false;                                                 //Marca como false el estado del envio
            }
        } else {
            answer = true;
        }
        
        if(!answer)                                                             //Si no se pudo hacer el envio
            Singleton.getSingleton().AddInToWriteQueue(toSend);                 //Vuelve a poner el objeo a enviar en la cola
        return answer;                                                          //Devuelve la respuesta
    }
    
    /**
     * Sincronización de datos. Se encarga llamar constantemente a la funcion de
     * envío y de lectura de datos
     */
    @Override
    public void run(){
        new Thread(){
            @Override
            public void run(){
                while(isConnected()){
                    boolean readAnswer = read();
                }
            }
        }.start();
        
        while(isConnected()){
            boolean writeAnswer = send();
        }
    }
}
