/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Information;

import java.io.Serializable;

/**
 *
 * @author Juan Diego
 */
public abstract class BasePackage implements Serializable{
    protected int priority;
    
    public BasePackage(){
        super();
    }
    
    public int getPriority(){
        return priority;
    }
    
    public int compare(BasePackage obj){
        return this.priority - obj.priority;
    }
}
