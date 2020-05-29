/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optitransmi_server;

import ServerConection.ServerConection;
import ServerConection.Conexion;
import DataStructures.SynchronizedHashMap;

/**
 *
 * @author Juan Diego
 */
public class Singleton {
    
    private final Conexion conexion;
    private final ServerConection server;                   //Objeto con el socket del servior
    private final SynchronizedHashMap activeUsers;          //Tabla hash para almacenar a los usuarios conectados
    
    private static Singleton singleton;
    
    public Singleton(){
        conexion = new Conexion();                          //Se establece conexion con la base de datos
        server = new ServerConection(7777);                 //Se inicia el socket con el puerto 7777
        activeUsers = new SynchronizedHashMap();            //Se inicializa las tabla hash
        server.start();                                     //Se inicia el hilo
    }
    
    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        
        return singleton;
    }
    
    public synchronized Conexion getConexion(){
        return conexion;
    }
    
    public SynchronizedHashMap getActiveUsers(){
        return activeUsers;
    }
}
