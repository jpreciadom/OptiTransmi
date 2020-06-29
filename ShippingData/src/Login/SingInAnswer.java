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

    public SingInAnswer(int idRequest, boolean answer, String message, int userType) {
        super(idRequest, answer, message);
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }
}
