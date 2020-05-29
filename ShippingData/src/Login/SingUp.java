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
public class SingUp extends BasePackage {
    private final String mail;
    private final String password;
    private final String name;
    private final int userType;

    public SingUp(int idRequest, String mail, String password, String name, int userType) {
        super(1, idRequest);
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getUserType() {
        return userType;
    }
}
