/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cont;

import hex.Map.Map;

/**
 *
 * @author faust
 */
public class PlFactory {
    
    public static boolean INIT=false;
    
    private Pl1 pl1;
    private Pl2 pl2;
    private Neutral neutral;
    
    public static PlFactory factory =  new PlFactory();
    
    private PlFactory(){
        
    }
    public static PlFactory getInstance(){
        return factory;
    }
    
    public void make(){
        
        int xp1, yp1, xp2, yp2;
        xp1= (int) (Math.random()*(Map.map.getX()-2)) +1;
        yp1= (int) (Math.random()*(Map.map.getY()-2)) +1;
        
        pl1 = new Pl1(xp1,yp1);
        pl2 = new Pl2();
    }
    
    
}
