/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class SingIn extends BasePackage {
    
    private final String mail;
    private final String password;

    public SingIn(int idRequest, String mail, String password) {
        super(1, idRequest);
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
