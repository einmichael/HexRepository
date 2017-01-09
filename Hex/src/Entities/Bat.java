/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import hex.Map.Tile;

/**
 *
 * @author faust
 */
public class Bat extends Ent {
    
    public Bat(Tile tile){
        super(tile);
        if(tile!=null)tile.bats=1;
    }
    
    
    
    
}
