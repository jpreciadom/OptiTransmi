/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrator;

import Base.BasePackage;

/**
 *
 * @author Juan Diego
 */
public class UpdateRates extends BasePackage {
    
    private final double newPrice;
    
    public UpdateRates(int idRequest, double newPrice){
        super(10, idRequest);
        this.newPrice = newPrice;
    }
    
    public double getNewPrice(){
        return newPrice;
    }
}
