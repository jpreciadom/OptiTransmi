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
import Information.Test;
import java.util.concurrent.Semaphore;

/**
 * Esta clase alamacena la información del usuario conectado. Almacenando la
 * información de su socket y su nombre de usuario con el cuál está almacenado
 * en la tabla hash
 * 
 * @author Juan Diego
 */
public class User extends Thread {
    private String userName;
    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final PriorityQueue<BasePackage> toWrite;
    private final PriorityQueue<BasePackage> toRead;
    
    private static Semaphore sinc;
    
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
        
        if(sinc == null)
            sinc = new Semaphore(6);
        
        start();
    }
    
    public Socket getUserSocket(){
        return clientSocket;
    }
    
    public boolean isConnected(){
        return !clientSocket.isClosed();
    }

    public String getUserName() {
        return userName;
    }
    
    public synchronized boolean AddInToWriteQueue(BasePackage toAdd){
        return toWrite.add(toAdd);
    }
    
    public synchronized BasePackage ReadInToWriteQueue(){
        return toWrite.poll();
    }
    
    public synchronized boolean AddInToReadQueue(BasePackage toAdd){
        return toRead.add(toAdd);
    }
    
    public synchronized BasePackage ReadFromToReadQueue(){
        return toRead.poll();
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
            AddInToReadQueue(readed);                                           //Lo agrega a la cola de objetos leidos
            System.out.println(((Test)readed).getMessage());
            answer = true;                                                      //Marca la respuesta como verdadero
        } catch(Exception ex){
            System.out.println(ex.getMessage());                                //Imprime el mensaje de error
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
        BasePackage toSend = ReadInToWriteQueue();                              //Saca el objeto a enviar de la cola de envios
        if(toSend != null){
            try{
                output.writeObject(toSend);                                         //Intenta hacer el envío, si puede
                answer = true;                                                      //Marca como verdadero el estado del envio si se pudo hacer
            } catch(IOException ex){
                System.out.println(ex.getMessage());                                //Imprime el mensaje del error que ocurrió y
                answer = false;                                                     //Marca como false el estado del envio
            }
        } else {
            answer = true;
        }
        
        if(!answer)                                                             //Si no se pudo hacer el envio
            AddInToWriteQueue(toSend);                                          //Vuelve a poner el objeo a enviar en la cola
        return answer;                                                          //Devuelve la respuesta
    }
    
    @Override
    public void run(){
        while(isConnected()){
            try {
                sinc.acquire();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                continue;
            }
            boolean readAnswer = read();
            //boolean writeAnswer = send();
            
            sinc.release();
        }
    }
}
