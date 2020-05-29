/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerConection;

import Base.BasePackage;
import Information.Answer;
import Login.SingIn;
import Login.SingUp;
import Request.StateListRequest;
import Request.StationListAnswer;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import optitransmi_server.Singleton;

/**
 *
 * @author Juan Diego
 */
public class LoggedUser extends UnLoggedUser {
    
    public LoggedUser(Socket client, String userName) {
        super(client, userName);
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
            int idRequest = readedObject.getIdRequest();
            
            if(readedObject instanceof StateListRequest){
                StateListRequest slr = (StateListRequest)readedObject;
                
                String query = "SELECT NOMBRE_ESTACION, DIRECCION, VAGONES " +
                               "FROM estacion " +
                               "WHERE NOMBRE_ESTACION LIKE '%" + slr.getSubName() + "%'";
                
                ResultSet result = singleton.getConexion().executeQuery(query);
                
                try{
                    while(result.next()){
                        String name = result.getString(1);
                        String direction = result.getString(2);
                        int wagons = result.getInt(4);
                        AddInToWriteQueue(new StationListAnswer(idRequest, name, direction, wagons));
                    }
                } catch(SQLException ex){
                    
                } finally {
                    AddInToWriteQueue(new StationListAnswer(idRequest, null, null, -1));
                }
            }
            
            sinc.release();
        }
    }
}
