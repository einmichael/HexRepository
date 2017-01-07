/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cont;

import Entities.Bat;
import hex.Map.Map;

/**
 *
 * @author faust
 */
public class Pl1 extends Pl {
    
    public Pl1 (int x, int y){
        bat.add(new Bat(Map.map.getTile(x, y)));
    }
    
    private void setStart(){
        Map.map.getX();
        
    }
}
