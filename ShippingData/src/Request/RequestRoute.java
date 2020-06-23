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
public class RequestRoute extends BasePackage {
    private final String route;
    private final String station;

    public RequestRoute(String route, String station, int idRequest) {
        super(7, idRequest);
        this.route = route;
        this.station = station;
    }

    public String getRoute() {
        return route;
    }

    public String getStation() {
        return station;
    }
}
