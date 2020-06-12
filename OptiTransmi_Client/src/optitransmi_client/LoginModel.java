package optitransmi_client;

import Base.BasePackage;
import Information.Answer;
import Login.SingIn;
import Login.SingUp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginModel extends Thread {
    protected Singleton singleton;

    protected boolean RunningThread;

    public LoginModel(){
        singleton = Singleton.getSingleton();
        RunningThread = true;
        singleton.getClient().connect();
    }

    @Override
    //Check the connection
    public void run() {
        boolean wasConnected = true;
        while(RunningThread){
            boolean isConnected = singleton.getClient().isConnected();
            if(wasConnected && !isConnected){
                //Agregar un mensaje diciendo que no está conectado
            } else if(!wasConnected && isConnected) {
                //quitar el mensaje de desconnecion
            }

            if(!isConnected){
                singleton.getClient().connect();
            }
            wasConnected = isConnected;
        }
    }

    public void EndModel() {
        RunningThread = false;
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

    public Answer singIn(String mail, String password, boolean rememberUser) {
        Answer a = null;
        int id = singleton.getCurrentIdRequest();
        singleton.getClient().send(new SingIn(id, mail, password));
        a = (Answer) ReadWithTime(5000);
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
        int id = singleton.getCurrentIdRequest();
        singleton.getClient().send(new SingUp(id, mail, password, name, 1));
        a = (Answer) ReadWithTime(5000);
        return a;
    }

    private BasePackage ReadWithTime(int timeMS){
        BasePackage readed = null;
        Thread t = new Thread(){
            @Override
            public void run(){
                singleton.AddInToReadQueue(singleton.getClient().read());
            }
        };
        t.start();

        long TiempoInicio, TiempoFinal;
        TiempoInicio = TiempoFinal = System.currentTimeMillis();
        while(readed == null && (TiempoFinal - TiempoInicio) < timeMS){
            readed = singleton.ReadFromToReadQueue();
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
}
