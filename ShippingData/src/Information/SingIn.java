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
public class SingIn extends BasePackage {
    
    private String mail;
    private String password;

    public SingIn(String mail, String password) {
        super();
        this.priority = 0;
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