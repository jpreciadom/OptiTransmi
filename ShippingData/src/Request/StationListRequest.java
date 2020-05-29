/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Request;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class StationListRequest extends BasePackage {
    
    private final String subName;
    
    public StationListRequest(int idRequest, String subName){
        super(2, idRequest);
        this.subName = subName;
    }
    
    public String getSubName(){
        return subName;
    }
}
