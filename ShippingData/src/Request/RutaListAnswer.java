package Request;

import Base.BasePackage;

import java.util.ArrayList;

public class RutaListAnswer extends BasePackage {
    private final ArrayList<String> valores;

    public RutaListAnswer(int idRequest, ArrayList<String> valores){
        super(2, idRequest);
        this.valores = valores;
    }

    public ArrayList<String> getValores(){
        return valores;
    }
}
