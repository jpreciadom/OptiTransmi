package optitransmi_client;

import Login.*;
import Information.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 * @author Heidy Johana Alayon
 */

public class Client {
    
    static Singleton singleton;
    static Scanner lector;
    static FileWriter fw;
    static PrintWriter pw;
    
    static {
        lector = new Scanner(System.in);
        try {
            fw= new FileWriter("Archivos/Usuarios.opti",true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        pw= new PrintWriter(fw);
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
            int id = singleton.getCurrentIdRequest();
            singleton.AddInToWriteQueue(new SingUp(id, correo, contrasenna, nombre, 1));
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
            int id = singleton.getCurrentIdRequest();
            singleton.AddInToWriteQueue(new SingIn(id, correo, contrasenna));
            singleton.getClient().send();
            singleton.getClient().read();
            System.out.println("Desea guardar el usuario en el dispositivo: (S/N)");
            if(lector.next().equals("S")){
                pw.println(correo + ";" + contrasenna);
            }
            System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
        } catch(InputMismatchException ex){
            System.out.println("Tipo de dato no valido, regresando al menú");
        }
    }
    
    public static void BuscarRutas(){
    }
    
    public static void main(String[] args) {
        System.out.println("Prueba de conexion");
        singleton = Singleton.getSingleton();
        singleton.getClient().connect();
        if (!singleton.getClient().isConnected()){
            System.out.println("No se pudo conectar, prueba fallida");
            System.exit(-1);
        }
        int option;
        do {
            option = 0;
            
            System.out.println("Opciones:");
            System.out.println("1. Iniciar sesion.");
            System.out.println("2. Registrarse.");
            System.out.println("3. Buscar rutas.");
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
                case 3:
                    BuscarRutas();
                    break;
                default:
                    System.out.println("La opcion ingresada no es valida");
            }
            
        } while(option != 0);
         pw.close();
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        singleton.getClient().disconnect();
            System.out.println("Prueba realizada");
    }
}
