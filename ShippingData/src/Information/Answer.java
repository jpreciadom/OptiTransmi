/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Information;

/**
 *
 * @author Juan Diego
 */
public class Answer extends BasePackage {
    
    protected boolean answer;
    protected String message;
    
    public Answer(boolean answer){
        super();
        this.priority = 0;
        this.answer = answer;
    }
    
    public Answer(boolean answer, String message){
        super();
        this.priority = 0;
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
