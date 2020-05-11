package optitransmi_client;

import Information.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 * @author Heidy Johana Alayon
 */

public class Client {
    
    static Singleton singleton;
    static Scanner lector;
    
    static {
        lector = new Scanner(System.in);
    }
    
    public static void terminarPrueba(){
        System.out.println("Finalizando prueba...");
        singleton.getClient().disconnect();
    }
    
    public static void singUp(){
        String correo, contrasenna, nombre;
        System.out.println("Registro...");
        
        try{
            System.out.print("Correo: ");
            correo = lector.next();
            System.out.print("Contraseña: ");
            contrasenna = lector.next();
            System.out.print("Nombre: ");
            nombre = lector.next();
            singleton.AddInToWriteQueue(new SingUp(correo, contrasenna, nombre, 1));
            singleton.getClient().send();
            singleton.getClient().read();
            System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
        } catch(InputMismatchException ex){
            System.out.println("Tipo de dato no valido, regresando al menú");
        }
    }
    
    public static void singIn(){
        String correo, contrasenna;
        System.out.println("Inicio de sesion");
        
        try{
            System.out.print("Correo: ");
            correo = lector.next();
            System.out.print("Contraseña: ");
            contrasenna = lector.next();
            singleton.AddInToWriteQueue(new SingIn(correo, contrasenna));
            singleton.getClient().send();
            singleton.getClient().read();
            System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
        } catch(InputMismatchException ex){
            System.out.println("Tipo de dato no valido, regresando al menú");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Prueba de conexion");
        singleton = Singleton.getSingleton();
        int option;
        do {
            option = 0;
            
            System.out.println("Opciones:");
            System.out.println("1. Iniciar sesion.");
            System.out.println("2. Registrarse.");
            System.out.println("0. Terminar prueba");
            System.out.println("");
            
            try {
                option = lector.nextInt();
            } catch(InputMismatchException ex){
                System.out.println("La opcion ingresada no es valida");
                continue;
            }
            
            switch(option){
                case 0:
                    terminarPrueba();
                    break;
                case 1:
                    singIn();
                    break;
                case 2:
                    singUp();
                    break;
                default:
                    System.out.println("La opcion ingresada no es valida");
            }
            
        } while(option != 0);
        System.out.println("Prueba realizada");
    }
}
