/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserDataConfig;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class ChangePassword extends BasePackage {
    private final String currentPassword;
    private final String newPassword;
    
    public ChangePassword(int idRequest, String cp, String np){
        super(3, idRequest);
        this.currentPassword = cp;
        this.newPassword = np;
    }
    
    public String getCurrentPassword(){
        return currentPassword;
    }
    
    public String getNewPassword(){
        return newPassword;
    }
}
