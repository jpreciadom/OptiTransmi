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
public class RouteListAnswer extends BasePackage {
    private final String ruta;
    private final String dia;
    private final String inicio;
    private final String fin;
    
    public RouteListAnswer(int idRequest, String ruta, String dia, String inicio, String fin){
        super(1, idRequest);
        this.ruta = ruta;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getRuta() {
        return ruta;
    }

    public String getDia() {
        return dia;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }
}
