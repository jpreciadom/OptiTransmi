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
import Information.*;
import java.sql.ResultSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import optitransmi_server.Singleton;

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
    private final ReentrantLock mutexToWrite;
    private final PriorityQueue<BasePackage> toWrite;
    private final ReentrantLock mutexToRead;
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
        
        mutexToWrite = new ReentrantLock();
        toWrite = new PriorityQueue<>();
        mutexToRead = new ReentrantLock();
        toRead = new PriorityQueue<>();
        
        if(sinc == null)
            sinc = new Semaphore(4);
        
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
    
    public boolean AddInToWriteQueue(BasePackage toAdd){
        boolean added = false;
        try{
            mutexToWrite.lock();
            added = toWrite.add(toAdd);
        } finally {
            mutexToWrite.unlock();
        }
        return added;
    }
    
    public BasePackage ReadInToWriteQueue(){
        BasePackage toReturn = null;
        try {
            mutexToWrite.lock();
            toReturn = toWrite.poll();
        } finally {
            mutexToWrite.unlock();
        }
        return toReturn;
    }
    
    public boolean AddInToReadQueue(BasePackage toAdd){
        boolean added = false;
        try{
            mutexToRead.lock();
            added = toRead.add(toAdd);
        } finally {
            mutexToRead.unlock();
        }
        return added;
    }
    
    public BasePackage ReadFromToReadQueue(){
        BasePackage toReturn = null;
        try {
            mutexToRead.lock();
            toReturn = toRead.poll();
        } finally {
            mutexToRead.unlock();
        }
        return toReturn;
    }
    
    private void disconnect(){
        Singleton singleton = Singleton.getSingleton();
        singleton.getActiveUsers().remove(this.userName);
        try {
            this.clientSocket.close();
        } catch (IOException ex) {

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
            AddInToReadQueue(readed);                                           //Lo agrega a la cola de objetos leidos
            answer = true;                                                      //Marca la respuesta como verdadero
        } catch(Exception ex){
            disconnect();                                                       //Desconecta el socket
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
                disconnect();                                                       //Imprime el mensaje del error que ocurrió y
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
        new Thread(){
            @Override
            public void run(){
                TryToRead();
            }
        }.start();
        while(isConnected()){
            
            try {
                sinc.acquire();
            } catch (InterruptedException ex) {
                continue;
            }
            
            boolean sendAnswer = send();
            
            BasePackage readedObject = ReadFromToReadQueue();
            
            if(readedObject == null){
                sinc.release();
                continue;
            } 
            
            Singleton singleton = Singleton.getSingleton();
            
            if(readedObject instanceof SingIn) {
                SingIn singIn = (SingIn)readedObject;
                
                if(Singleton.getSingleton().getActiveUsers().exist(singIn.getMail())){
                    AddInToWriteQueue(new Answer(false, "Ya tiene una sesión activa"));
                } else {
                    String query = "select ValidateLogin('"
                            + singIn.getMail() + "', '"
                            + singIn.getPassword() + "')";
                    ResultSet result = singleton.getConexion().executeQuery(query);

                    try {
                        boolean userExist = false;
                        if(result.next()){
                            userExist = result.getInt(1) == 1;
                        }
                        Answer answer;
                        if(userExist){
                            answer = new Answer(true, "Ingreso exitoso");
                        } else {
                            answer = new Answer(false, "Datos de inicio de sesión no válidos");
                        }
                        AddInToWriteQueue(answer);
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
            } else if (readedObject instanceof SingUp) {
                SingUp singUp = (SingUp)readedObject;
                
                boolean UserAlreadyExist = false;
                String SQL = "SELECT COUNT(*) FROM USUARIO WHERE correo = '"
                        + singUp.getMail() + "'";
                
                ResultSet result = singleton.getConexion().executeQuery(SQL);
                try {
                    if(result.next()){
                        UserAlreadyExist = result.getInt(1) == 1;
                    }
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
                if(!UserAlreadyExist){
                    SQL = "INSERT INTO USUARIO VALUES('"
                            + singUp.getMail() + "', '"
                            + singUp.getPassword() + "', '"
                            + singUp.getName() + "', "
                            + singUp.getUserType() + ")";
                    singleton.getConexion().executeSQL(SQL);
                    AddInToWriteQueue(new Answer(true));
                } else {
                    AddInToWriteQueue(new Answer(false, "El correo ya esta en uso"));
                }
            } else {
                System.out.println(readedObject.getPriority());
            }
            
            sinc.release();
        }
    }
    
    private void TryToRead(){
        while(isConnected()){
            boolean readAnswer = read();
        }
    }
}
