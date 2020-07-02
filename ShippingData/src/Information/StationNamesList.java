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
public class StationNamesList extends BasePackage {
    protected String nameStation;

    public StationNamesList(String nameStation, int idRequest) {
        super(nameStation == null ? 5 : 4, idRequest);
        this.nameStation = nameStation;
    }

    public String getNameStation() {
        return nameStation;
    }
}
