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
public abstract class Ent {
    
    private Tile tile;
    public Ent(Tile tile){
        this.tile=tile;
    }
    
    
}
