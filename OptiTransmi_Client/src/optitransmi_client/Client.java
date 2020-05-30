package optitransmi_client;

import Login.*;
import Information.*;
import Request.*;
import UserDataConfig.ChangePassword;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Juan Diego Preciado
 * @author Juan Pablo Carmona
 * @autor Juan Camilo Acosta
 */

public class Client {
    
    static Singleton singleton;
    static File file;
    static Scanner lector;
    static FileWriter fw;
    static BufferedWriter bw;
    static RandomAccessFile raf;
    static String correoGuardado;
    static String contrasennaGuardado;
    
    
    static {
        file=new File("Archivos/Usuarios.opti");
        try {
            raf= new RandomAccessFile(file,"rw");
            correoGuardado=raf.readLine();
            contrasennaGuardado=raf.readLine();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        lector = new Scanner(System.in);
        try {
            fw= new FileWriter("Archivos/Historial.opti",true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        bw= new BufferedWriter(fw);
        
        
    }
        
    
    
    public static void terminarPrueba(){
        System.out.println("Finalizando prueba...");
        singleton.getClient().disconnect();
    }
    
    public static void userSingIn(){
        int optionS;
        do {
            optionS = 0;
            
            System.out.println("Opciones:");
            System.out.println("1. Buscar estaciones.");
            System.out.println("2. Modificar datos de usuario.");
            System.out.println("0. Terminar prueba");
            System.out.println("");
            
            try {
                optionS = lector.nextInt();
            } catch(InputMismatchException ex){
                System.out.println("La opcion ingresada no es valida");
                continue;
            }
            
            switch(optionS){
                case 0:
                    terminarPrueba();
                    break;
                case 1:
                    BuscarEstaciones();
                    break;
                case 2:
                    modifyUserData();
                    break;
                 /*
                case 3:
                    BuscarEstaciones();
                    break;
                */
                default:
                    System.out.println("La opcion ingresada no es valida");
            }
            
        } while(optionS != 0);
    }
    
    public static void modifyUserData(){
        int optionM;
            System.out.println("Modificacion de datos de usuario");
            
            try{
                
                optionM = 0;
            
            System.out.println("Opciones:");
            System.out.println("1. Modificar nombre");
            System.out.println("2. Modificar contraseña");
            System.out.println("");
            
                try {
                    optionM = lector.nextInt();
                } catch(InputMismatchException ex){
                    System.out.println("La opcion ingresada no es valida");

                }

                switch(optionM){
                    case 1:
                        BuscarEstaciones();
                        break;
                    case 2:
                        changePassword();
                        break;
                     /*
                    case 3:
                        BuscarEstaciones();
                        break;
                    */
                    default:
                        System.out.println("La opcion ingresada no es valida");
                }
                
                
            }catch (InputMismatchException ex){
                System.out.println("Tipo de dato no valido, regresando al menú");
            }
    }
    
    public static void changePassword(){
         String contrasenna, newContrasenna;
         System.out.println("Modificar contraseña");
         try{
            System.out.print("ingrese su contraseña actual: ");
            contrasenna = lector.next();
            System.out.print("Ingrese su nueva contraseña: ");
            newContrasenna = lector.next();
            int id = singleton.getCurrentIdRequest();
            singleton.AddInToWriteQueue(new ChangePassword(id, contrasenna, newContrasenna));
            singleton.getClient().send();
            singleton.getClient().read();
            System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
        } catch(InputMismatchException ex){
            System.out.println("Tipo de dato no valido, regresando al menú");
        }
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
    
    public static void singIn() throws IOException{
        String correo, contrasenna;
        boolean infoExist=false;
        System.out.println("Inicio de sesion");
        
        try{
            if(correoGuardado!=null){
                infoExist= true;
                System.out.println(correoGuardado);
                System.out.println(contrasennaGuardado);
                int id = singleton.getCurrentIdRequest();
                singleton.AddInToWriteQueue(new SingIn(id, correoGuardado, contrasennaGuardado));
                singleton.getClient().send();
                singleton.getClient().read();
                //System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
            }else{
                System.out.print("Correo: ");
                correo = lector.next();
                System.out.print("Contraseña: ");
                contrasenna = lector.next();
                int id = singleton.getCurrentIdRequest();
                singleton.AddInToWriteQueue(new SingIn(id, correo, contrasenna));
                singleton.getClient().send();
                singleton.getClient().read();
                System.out.println("Desea guardar el usuario en el dispositivo: (S/cualquier simbolo)");
                String confirmacion=lector.next();
                if(confirmacion.equals("S")|| confirmacion.equals("s")){
                    if(infoExist){
                        System.out.println("Ya existe un usuario registrado en este dispositivo, desea guardar uno nuevo (S/cualquier simbolo)");
                        String guardar=lector.next();
                        if(guardar.equals("S")||guardar.equals("s")){
                            raf.seek(0);
                            raf.writeChars(correo+"\n");
                            raf.writeChars(contrasenna+"\n");
                        }
                    }else{
                        raf.seek(0);
                        raf.writeChars(correo+"\n");
                        raf.writeChars(contrasenna+"\n");
                    }
                    System.out.println(((Answer)(singleton.ReadFromToReadQueue())).getMessage());
                }
            }
            
        } catch(InputMismatchException ex){
            System.out.println("Tipo de dato no valido, regresando al menú");
        }
        raf.close();
    }
    
    public static void BuscarEstaciones(){
        System.out.println("Ingrese el nombre de la estacion");
        String subname = lector.next();
        int id = singleton.getCurrentIdRequest();
        
        singleton.AddInToWriteQueue(new StationListRequest(id, subname));
        singleton.getClient().send();
        
        boolean finish = false;
        while(!finish){
            singleton.getClient().read();
            StationListAnswer sla = (StationListAnswer)singleton.ReadFromToReadQueue();
            if(sla.getName() == null){
                finish = true;
            } else {
                System.out.println("Nombre: " + sla.getName());
                System.out.println("Direccion: " + sla.getDirection());
                System.out.println("Vagones " + sla.getWagons());
                System.out.println("-----------------------------------------");
            }
        }
    }
    
    public static void registrarAccion(int opcion) throws IOException{     //opciones: 1. Registro, 2. Inicio de sesion,3. Busqueda estaciones
        Date fecha= new Date();
        switch(opcion){
            case 1:
                bw.write("Registro a Optitransmi");
                bw.newLine();
                bw.write("fecha: "+ fecha.toString()+"\n");
                break;
            case 2:
                bw.write("Inicio de sesion a Optitransmi");
                bw.newLine();
                bw.write("fecha: "+ fecha.toString()+"\n");
                break;
            case 3:
                bw.write("Busqueda de estaciones");
                bw.newLine();
                bw.write("fecha: "+ fecha.toString()+"\n");
                break;
            default:
                System.out.println("Caso invalido, registro a historial fallido");
        }
        
    }
    
    
    
    
    public static void main(String[] args) throws IOException {
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
            //System.out.println("3. Buscar estaciones.");
            System.out.println("4. Mostrar tarifas.");
            System.out.println("0. Terminar prueba.");
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
                    registrarAccion(2);
                    break;
                case 2:
                    singUp();
                    registrarAccion(1);
                    break;
                 /*
                case 3:
                    BuscarEstaciones();
                    registrarAccion(3);
                    break;
                */
                case 4:
                    System.out.println("Tarifa troncal: "+singleton.getRates()[0]);
                    System.out.println("Tarifa zonal: "+singleton.getRates()[1]);
                    break;
                default:
                    System.out.println("La opcion ingresada no es valida");
            }
            
        } while(option != 0 && option != 1);
        
        if(option == 1 ){
            userSingIn();
        }
        
        bw.close();
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        singleton.getClient().disconnect();
            System.out.println("Prueba realizada");
    }
}
