package Request;

import Base.BasePackage;

public class StationAnswer extends BasePackage {
    private final String name;


    public StationAnswer(int idRequest, String name){
        super(name == null ? 2 : 1, idRequest);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
