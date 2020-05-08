package optitransmi_server;


/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 * @author Heidy Johana Alayon
 */

public class Server {

    public static void main(String[] args) {
        System.out.println("Prueba de conexion");
        Singleton s = Singleton.getSingleton();
        System.out.println("El servidor esta escuchando solicitudes");
    }
}
