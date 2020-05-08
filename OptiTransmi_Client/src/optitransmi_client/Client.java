package optitransmi_client;

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
        System.out.println("Prueba realizada");
    }
}
