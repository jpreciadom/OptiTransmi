/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Information.Answer;

/**
 *
 * @author Juan Diego
 */
public class SingInAnswer extends Answer {
    private int userType;
    private String userName;

    public SingInAnswer(int idRequest, boolean answer, String userName, int userType) {
        super(idRequest, answer);
        this.userType = userType;
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }
}
