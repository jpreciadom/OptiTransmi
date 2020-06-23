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
public class News extends BasePackage {
    private final String title;
    private final String content;

    public News(String title, String content, int idRequest) {
        super(10, idRequest);
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
