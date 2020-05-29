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
public class Answer extends BasePackage {
    
    protected boolean answer;
    protected String message;
    
    public Answer(int idRequest, boolean answer){
        super(0, idRequest);
        this.answer = answer;
    }
    
    public Answer(int idRequest, boolean answer, String message){
        super(0, idRequest);
        this.answer = answer;
        this.message = message;
    }
    
    public boolean getAnswer(){
        return answer;
    }
    
    public String getMessage(){
        return message;
    }
}
