package optitransmi_client;

import Base.BasePackage;
import Connection.ConnectionModel;
import DataStructures.*;
import GUI.MenuInicioController;
import Information.*;
import Login.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;
import Request.*;

public class Model extends Thread {
    //Connection with the GUI
    private final MenuInicioController controler;
    
    //Socket objects
    private int currentIdRequest;
    private final ConnectionModel client;
    private final ReentrantLock mutexToWrite;
    private final PriorityQueue<BasePackage> toWrite;
    private final ReentrantLock mutexToRead;
    private final PriorityQueue<BasePackage> toRead;

    //Model objects
    private boolean RunningThread;
    private boolean Logged;
    private boolean Admin;
    
    //Request control
    private SynchronizedHashMap<Integer, BasePackage> request;

    public Model(MenuInicioController controler){
        this.setPriority(MAX_PRIORITY);
        
        //GUI
        this.controler = controler;
        
        //Socket objects
        currentIdRequest = 0;
        client = new ConnectionModel(7777);
        mutexToWrite = new ReentrantLock();
        toWrite = new PriorityQueue<>();
        mutexToRead = new ReentrantLock();
        toRead = new PriorityQueue<>();
        
        //Model objets
        RunningThread = true;
        Logged = false;
        Admin = false;
        
        //Request control
        request = new SynchronizedHashMap<>();
    }

    @Override
    //Check the connection, send messages and computate answers
    public void run() {
        boolean wasConnected = true;
        while(RunningThread){
            boolean isConnected = client.isConnected();
            if(wasConnected && !isConnected){
                System.out.println("Usted esta actualmente desconectado");
                blockControls();
            } else if(!wasConnected && isConnected) {
                System.out.println("Conectado al servidor");
                enableControls();
            }
            wasConnected = isConnected;
            
            try {
                sleep(50);
            } catch(InterruptedException ie){
                System.out.println(ie.getMessage());
            }

            if(!isConnected){
                client.connect();
            } else if(Logged){
                DataControl();
                
                BasePackage readed = ReadFromToReadQueue();
                
                if(readed != null){
                    if(readed instanceof StationListAnswer){
                        StationListAnswer sla = (StationListAnswer)readed;
                        BasePackage request = this.request.get(readed.getIdRequest());
                        if(request != null){
                            if(controler.buscarEstacionWindow.isVisible() && sla.getName() != null){
                                String toAppend = 
                                        "Nombre: " + sla.getName() + "\n" +
                                        "Direccion: " + sla.getDirection()+ "\n" +
                                        "Vagones: " + sla.getWagons() + "\n\n";
                               controler.ResultadosBuscarEstacion.appendText(toAppend);
                            } else {
                                RequestFulfilled(readed);
                            }
                        }
                    }else if(readed instanceof RutaListAnswer){
                        RutaListAnswer rla = (RutaListAnswer)readed;
                        BasePackage request = this.request.get(readed.getIdRequest());
                        if(request != null){
                            if(controler.buscarRutaWindow.isVisible()){
                                controler.CrearListaRutas(rla.getValores());
                            } else {
                                RequestFulfilled(readed);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void blockControls(){
        if(controler.inicioWindow.isVisible()){
            controler.inicioSesion.setDisable(true);
            controler.registro.setDisable(true);
        }
    }
    
    private void enableControls(){
        if(controler.inicioWindow.isVisible()){
            controler.inicioSesion.setDisable(false);
            controler.registro.setDisable(false);
        }
    }
    
    private void DataControl(){
        BasePackage bp = ReadInToWriteQueue();
        if(!client.send(bp)){
            AddInToWriteQueue(bp);
        }
        AddInToReadQueue(client.read());
    }
    
    public boolean isLogged(){
        return Logged;
    }
    
    public void setLogged(boolean Logged){
        this.Logged = Logged;
    }

    public void setAdmin(boolean Admin){
        this.Admin = Admin;
    }
    
    public void EndModel() {
        RunningThread = false;
    }
    
    //SOCKET OPERATIONS - START
    
    public ConnectionModel getClient(){
        return client;
    }
    
    public int getCurrentIdRequest(){
        int reg = currentIdRequest;
        currentIdRequest++;
        return reg;
    }
    
    public boolean AddInToWriteQueue(BasePackage toAdd){
        boolean added = false;
        if(toAdd != null){
            try{
                mutexToWrite.lock();
                added = toWrite.add(toAdd);
            } finally {
                mutexToWrite.unlock();
            }
        }
        return added;
    }
    
    public BasePackage ReadInToWriteQueue(){
        BasePackage toReturn = null;
        try {
            mutexToWrite.lock();
            toReturn = toWrite.poll();
        } finally {
            mutexToWrite.unlock();
        }
        return toReturn;
    }
    
    public boolean AddInToReadQueue(BasePackage toAdd){
        boolean added = false;
        if(toAdd != null) {
            try{
                mutexToRead.lock();
                added = toRead.add(toAdd);
            } finally {
                mutexToRead.unlock();
            }
        }
        return added;
    }
    
    public BasePackage ReadFromToReadQueue(){
        BasePackage toReturn = null;
        try {
            mutexToRead.lock();
            toReturn = toRead.poll();
        } finally {
            mutexToRead.unlock();
        }
        return toReturn;
    }
    
    //SOCKET OPERATIONS - END
    
    //MODEL LOGIN OPERATIONS - START
    
    public void DeleteUserFile(){
        try {
            File f = new File("Archivos/Usuarios.opti");
            f.delete();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public String readSaveUser(){
        String readed = new String();
        try {
            File f = new File("Archivos/Usuarios.opti");
            if(f.exists() && f.length()>1){
                BufferedReader br = new BufferedReader(new java.io.FileReader(f));
                String r;
                do{
                    r = br.readLine();
                    if(r != null){
                        readed += decode(r) + " ";
                    }
                } while(r != null);
                readed = readed.trim();
            } else {
                readed = null;
            }
        } catch(IOException ex){
            readed = null;
        }
        return readed;
    }

    public SingInAnswer singIn(String mail, String password, boolean rememberUser) {
        SingInAnswer a;
        int id = getCurrentIdRequest();
        client.send(new SingIn(id, mail, password));
        a = (SingInAnswer) ReadWithTime(5000);
        if(rememberUser){
            try{
                File f = new File("Archivos/Usuarios.opti");
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f);
                
                pw.println(code(mail));
                pw.println(code(password));
                pw.flush();
                pw.close();
            } catch(IOException ex){
                //Error guardando en el fichero
            }         
        }
        return a;
    }

    public Answer singUp(String mail, String password, String name, boolean RememberUser){
        Answer a = null;
        int id = getCurrentIdRequest();
        client.send(new SingUp(id, mail, password, name, 1));
        a = (Answer) ReadWithTime(5000);
        return a;
    }

    private BasePackage ReadWithTime(int timeMS){
        BasePackage readed = null;
        Thread t = new Thread(){
            @Override
            public void run(){
                BasePackage bp = null;
                while(bp == null){
                    bp = client.read();
                }
                AddInToReadQueue(bp);
            }
        };
        t.setPriority(MAX_PRIORITY);
        t.start();

        long TiempoInicio, TiempoFinal;
        TiempoInicio = TiempoFinal = System.currentTimeMillis();
        while(readed == null && (TiempoFinal - TiempoInicio) < timeMS){
            readed = ReadFromToReadQueue();
            TiempoFinal = System.currentTimeMillis();
        }
        t = null;

        return readed;
    }
    
    private String code(String s){
        String newS = new String();
        for(int i = 0; i < s.length(); i++){
            int c = (s.charAt(i) + 'a')%255;
            newS += (char)c;
        }
        return newS;
    }
    
    private String decode(String s){
        String newS = new String();
        for(int i = 0; i < s.length(); i++){
            int c =  (255 + s.charAt(i) - 'a')%255;
            newS += (char)c;
        }
        return newS;
    }
    
    //MODEL LOGIN OPERATIONS - END
    
    //REQUEST CONTROL - START
    
    public SynchronizedHashMap getRequest(){
        return request;
    }
    
    public void createRequest(BasePackage bp){
        request.put(bp.getIdRequest(), bp);
        AddInToWriteQueue(bp);
    }
    
    public void RequestFulfilled(BasePackage bp){
        request.remove(bp.getIdRequest());
    }
    
    //REQUEST CONTROL - END
}
