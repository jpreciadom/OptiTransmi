/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserDataConfig;

import Base.BasePackage;
/**
 *
 * @author Juan Pablo
 */
public class ChangeUserName extends BasePackage {
    private final String currentPassword;
    private final String newUserName;
    
    public ChangeUserName(int idRequest, String cp, String nus){
        super(3, idRequest);
        this.currentPassword = cp;
        this.newUserName = nus;
    }
    
    public String getCurrentPassword(){
        return currentPassword;
    }
    
    public String getNewUserName(){
        return newUserName;
    }
}
