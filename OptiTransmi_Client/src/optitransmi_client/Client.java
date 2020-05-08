package optitransmi_client;

import Information.Test;
import java.util.Scanner;

/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 * @author Heidy Johana Alayon
 */

public class Client {
    
    public static void main(String[] args) {
        System.out.println("Prueba de conexion");
        Singleton.getSingleton();
        Scanner lector = new Scanner(System.in);
        System.out.println("Prueba realizada");
        while(true){
            Singleton.getSingleton().AddInToWriteQueue(new Test(0, lector.next()));
        }
    }
}
