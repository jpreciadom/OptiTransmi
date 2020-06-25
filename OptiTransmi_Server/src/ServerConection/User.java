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
import java.util.ArrayList;
import java.util.PriorityQueue;
import Base.BasePackage;
import Information.*;
import Login.*;
import Request.*;
import UserDataConfig.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import optitransmi_server.Singleton;

enum UserType {
    unlogged, logged, administrator
}

/**
 * Esta clase almacena la información del usuario conectado. Almacenando la
 * información de su socket y su nombre de usuario con el cuál está almacenado
 * en la tabla hash
 * 
 * @author Juan Diego
 */
public class User extends Thread {
    protected String userName;
    public UserType userType;
    protected long lastRequest;
    protected final Socket clientSocket;
    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected final ReentrantLock mutexToWrite;
    protected final PriorityQueue<BasePackage> toWrite;
    protected final ReentrantLock mutexToRead;
    protected final PriorityQueue<BasePackage> toRead;
    
    protected static Semaphore sinc;
    
    public User(Socket client, String userName){
        this.clientSocket = client;
        this.userName = userName;
        userType = UserType.unlogged;
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
    
    protected void disconnect(){
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
    protected boolean read(){
        boolean answer;                                                         //Indica si se pudo enviar o no el mensaje
        try{
            if(input.available() > 0){
                byte []enBuf = new byte[4];
                input.read(enBuf);
                BasePackage readed = (BasePackage)input.readObject();           //Lee el objeto
                AddInToReadQueue(readed);                                       //Lo agrega a la cola de objetos leidos
            }
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
    protected boolean send(){
        boolean answer;                                                         //Indica si se pudo enviar o no el mensaje
        BasePackage toSend = ReadInToWriteQueue();                              //Saca el objeto a enviar de la cola de envios
        if(toSend != null){
            byte []enBuf = new byte[4];
            try{
                output.write(enBuf);
                output.writeObject(toSend);                                     //Intenta hacer el envío, si puede
                answer = true;                                                  //Marca como verdadero el estado del envio si se pudo hacer
            } catch(IOException ex){
                disconnect();                                                   //Imprime el mensaje del error que ocurrió y
                answer = false;                                                 //Marca como false el estado del envio
            }
        } else {
            answer = true;
        }
        
        if(!answer)                                                             //Si no se pudo hacer el envio
            AddInToWriteQueue(toSend);                                          //Vuelve a poner el objeto a enviar en la cola
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

                lastRequest = System.currentTimeMillis();
                //if(userType == UserType.unlogged){
                    UnLoggedUser(readedObject);
                //}else if(userType == UserType.logged){
                    LoggedUser(readedObject);
                //}

                //if(userType == UserType.administrator){
                    Admin(readedObject);
                //}

            sinc.release();
        }
        Singleton.getSingleton().getActiveUsers().remove(this.userName);
    }
    
    protected void TryToRead(){
        while(isConnected()){
            boolean readAnswer = read();
        }
    }
    
    private void UnLoggedUser(BasePackage readedObject){
        Singleton singleton = Singleton.getSingleton();
        int idRequest = readedObject.getIdRequest();
        if(readedObject instanceof SingIn) {
            SingIn singIn = (SingIn)readedObject;

            if(Singleton.getSingleton().getActiveUsers().containsKey(singIn.getMail())){
                AddInToWriteQueue(new Answer(idRequest, false, "Ya tiene una sesión activa"));
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
                        query =  "SELECT NOMBRE_USUARIO FROM USUARIO WHERE CORREO = '" + singIn.getMail() + "'";
                        result = singleton.getConexion().executeQuery(query);
                        String name = null;
                        try {
                            name = result.getString(1);
                        } catch (Exception se){ }
                        answer = new Answer(idRequest, true, name);
                        Singleton.getSingleton().getActiveUsers().updateKey(this.userName, singIn.getMail());
                        this.userName = singIn.getMail();
                        this.userType = UserType.logged;
                    } else {
                        answer = new Answer(idRequest, false, "Datos de inicio de sesión no válidos");
                    }
                    AddInToWriteQueue(answer);
                } catch(Exception e){
                    System.out.println(e.getMessage());
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
                AddInToWriteQueue(new Answer(idRequest, true));
                Singleton.getSingleton().getActiveUsers().updateKey(this.userName, singUp.getMail());
                this.userType = UserType.logged;
            } else {
                AddInToWriteQueue(new Answer(idRequest, false, "El correo ya esta en uso"));
            }
        }else {
            System.out.println(readedObject.getPriority());
        }
    }
    
    private void LoggedUser(BasePackage readedObject){
        Singleton singleton = Singleton.getSingleton();
        int idRequest = readedObject.getIdRequest();
        if(readedObject instanceof StationListRequest){
            StationListRequest slr = (StationListRequest)readedObject;

            String query = "SELECT NOMBRE_ESTACION, DIRECCION, VAGONES " +
                           "FROM estacion " +
                           "WHERE LOWER(NOMBRE_ESTACION) LIKE '%" + slr.getSubName().toLowerCase() + "%'";

            ResultSet result = singleton.getConexion().executeQuery(query);

            try{
                AddInToWriteQueue(new StationListAnswer(idRequest, null, null, -1));
                while(result.next()){
                    String name = result.getString(1);
                    String direction = result.getString(2);
                    int wagons = result.getInt(3);
                    AddInToWriteQueue(new StationListAnswer(idRequest, name, direction, wagons));
                }
            } catch(SQLException ex){ }
        }else if(readedObject instanceof RutaListRequest){
           RutaListRequest rlr = (RutaListRequest)readedObject;

           String query = "SELECT CODIGO_RUTA, DIA, INICIO, FIN " +
                   "FROM ruta " +
                   "WHERE LOWER(CODIGO_RUTA) LIKE '%" + rlr.getSubName().toLowerCase() + "%'";

            ResultSet result = singleton.getConexion().executeQuery(query);

            try{
                //AddInToWriteQueue(new RutaListAnswer(idRequest, null));
                ArrayList<String> valores = new ArrayList<String>();
                while(result.next()){
                    String codigo = result.getString(1);
                    String dia = result.getString(2);
                    String inicio = result.getString(3);
                    String fin = result.getString(4);
                    valores.add(codigo);
                    valores.add(dia);
                    valores.add(inicio);
                    valores.add(fin);
                    //System.out.println(codigo);
                    //System.out.println("aqui"+valores.get(0));
                }
                AddInToWriteQueue(new RutaListAnswer(idRequest, valores));
            } catch(SQLException ex){ }
        }else if(readedObject instanceof ChangePassword){
            ChangePassword cp = (ChangePassword)readedObject;
            
            String query1 = "select ValidateLogin('"
                        + this.userName + "', '"
                        + cp.getCurrentPassword() + "')";
                ResultSet result = singleton.getConexion().executeQuery(query1);
                
                try {
                    boolean contrasennaCorrecta = false;
                    if(result.next()){
                        contrasennaCorrecta = result.getInt(1) == 1;
                    }
                    Answer answer;
                    if(contrasennaCorrecta == true){
                        String query = "UPDATE usuario " +
                           "SET CONTRASENNA = '" + cp.getNewPassword() +"' "+
                           "WHERE CORREO= '" + this.userName + "'";
                        
                            singleton.getConexion().executeSQL(query);
                            
                            answer = new Answer(idRequest, true, "Cambio de contraseña exitoso");
                    } else {
                        answer = new Answer(idRequest, false, "Contraseña incorrecta");
                    }
                    AddInToWriteQueue(answer);
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
         
        }else if(readedObject instanceof ChangeUserName){
            ChangeUserName cus = (ChangeUserName)readedObject;
            
            String query1 = "select ValidateLogin('"
                        + this.userName + "', '"
                        + cus.getCurrentPassword() + "')";
                ResultSet result = singleton.getConexion().executeQuery(query1);
                
                try {
                    boolean contrasennaCorrecta = false;
                    if(result.next()){
                        contrasennaCorrecta = result.getInt(1) == 1;
                    }
                    Answer answer;
                    if(contrasennaCorrecta == true){
                        String query = "UPDATE usuario " +
                           "SET NOMBRE_USUARIO = '" + cus.getNewUserName() +"' "+
                           "WHERE CORREO= '" + this.userName + "'";
                        
                            singleton.getConexion().executeSQL(query);
                            
                            answer = new Answer(idRequest, true, "Cambio de nombre de usuario exitoso");
                            AddInToWriteQueue(answer);
                    } else {
                        answer = new Answer(idRequest, false, "Contraseña incorrecta");
                    }
                    AddInToWriteQueue(answer);
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
        }
    }
    
    private void Admin(BasePackage readedObject){
        Singleton singleton = Singleton.getSingleton();
        if(readedObject instanceof UpdateNews){
            String query = "UPDATE FROM NOTICIA VALUES(" + ((UpdateNews) readedObject).getIdToUpdate() +
                    ", '" + this.userName + "', '" + ((News) readedObject).getContent() + 
                    "', " + ((News) readedObject).getTitle() + "')";
            singleton.getConexion().executeSQL(query);
        } else if(readedObject instanceof News){
            String query = "INSERT INTO NOTICIA VALUES(" + readedObject.getIdRequest() +
                    ", '" + this.userName + "', '" + ((News) readedObject).getContent() + 
                    "', " + ((News) readedObject).getTitle() + "')";
            singleton.getConexion().executeSQL(query);
        }
    }
}
