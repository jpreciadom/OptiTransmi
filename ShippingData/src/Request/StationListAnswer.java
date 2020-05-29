/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Request;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class StationListAnswer extends BasePackage {
    private final String name;
    private final String direction;
    private final int wagons;
    
    public StationListAnswer(int idRequest, String name, String direction, int wagons){
        super(1, idRequest);
        this.name = name;
        this.direction = direction;
        this.wagons = wagons;
    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public int getWagons() {
        return wagons;
    }
}
