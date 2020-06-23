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
public class UpdateNews extends News {
    private final int idToUpdate;

    public UpdateNews(int idToUpdate, String title, String content, int idRequest) {
        super(title, content, idRequest);
        this.idToUpdate = idToUpdate;
    }

    public int getIdToUpdate() {
        return idToUpdate;
    }
}
