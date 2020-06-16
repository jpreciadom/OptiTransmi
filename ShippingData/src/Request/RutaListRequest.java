package Request;

import Base.BasePackage;

public class RutaListRequest extends BasePackage{

    private final String subName;

    public RutaListRequest(int idRequest, String subName){
        super(2, idRequest);
        this.subName = subName;
    }

    public String getSubName(){
        return subName;
    }
}
