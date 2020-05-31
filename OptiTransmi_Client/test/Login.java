/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import optitransmi_client.Client;
import optitransmi_client.Singleton;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Juan Diego
 */
public class Login {
    
    public Login() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void Connection(){
        System.out.println("Prueba de conexion");
        Singleton singleton = Singleton.getSingleton();
        singleton.getClient().connect();
        assertEquals(singleton.getClient().isConnected(), true);
    }
    
    @Test
    public void SingInTest(){
        Singleton singleton = Singleton.getSingleton();
        try {
            int r = Client.singIn("jdiegopm12@gmail.com", "alcachofas");
            assertEquals(r, 0);
            r = Client.singIn("jdiegopm12@gmail.com", "Alcachofas");
            assertEquals(r, 1);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
