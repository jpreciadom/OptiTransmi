package optitransmi_client;

import Base.BasePackage;
import Information.Answer;
import Login.SingIn;
import Login.SingUp;

public class LoginModel extends Thread {
    protected Singleton singleton;

    protected boolean RunningThread;

    public LoginModel(){
        singleton = Singleton.getSingleton();
        RunningThread = true;
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

    public Answer singIn(String mail, String password, boolean rememberUser) {
        Answer a = null;
        int id = singleton.getCurrentIdRequest();
        singleton.getClient().send(new SingIn(id, mail, password));
        a = (Answer) ReadWithTime(5000);
        if(rememberUser){
            //Guardar en el fichero
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
        if(t.isAlive()){
            t.destroy();
        }

        return readed;
    }
}
