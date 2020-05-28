/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Information;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class Test extends BasePackage{
    
    private String message;
    
    public Test(int p, String message){
        super(p, -1);
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}
