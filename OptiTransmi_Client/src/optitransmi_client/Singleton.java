/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optitransmi_client;

/**
 *
 * @author Juan Diego
 */
public class Singleton {
    
    
    public static Singleton singleton;
    
    public Singleton(){
        
    }
    
    public Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        
        return singleton;
    }
}
