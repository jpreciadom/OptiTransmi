package Request;

import Base.BasePackage;

public class RutaListRequest extends BasePackage{

    private final String subName;
    private final String day;

    public RutaListRequest(int idRequest, String subName, String day){
        super(2, idRequest);
        this.subName = subName;
        this.day= day;
    }

    public String getSubName(){
        return subName;
    }

    public String getDay() {
        return day;
    }
}
