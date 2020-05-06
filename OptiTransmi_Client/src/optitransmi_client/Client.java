package optitransmi_client;

import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 * @author Heidy Johana Alayon
 */

public class Client {
    
    static Socket socket;
    
    static {
        try {
            socket = new Socket("localhost", 7777);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Prueba de conexion");
        System.out.println(socket.isConnected());
    }
}
