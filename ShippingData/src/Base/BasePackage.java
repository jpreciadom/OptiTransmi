/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

import java.io.Serializable;

/**
 *
 * @author Juan Diego
 */
public abstract class BasePackage implements Serializable, Comparable<BasePackage> {
    
    protected final int priority;
    protected int idRequest;
    
    public BasePackage(int priority, int idRequest){
        super();
        this.priority = priority;
        this.idRequest = idRequest;
    }
    
    public int getPriority(){
        return priority;
    }
    
    public int getIdRequest(){
        return idRequest;
    }

    @Override
    public int compareTo(BasePackage o) {
        return this.priority - o.priority;
    }
}
