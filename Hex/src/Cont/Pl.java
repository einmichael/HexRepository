/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cont;

import Entities.Bat;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author faust
 */
public class Pl {
    
    
    static{
        //wird einmal (statisch) zuerst ausgeführt
    }
    
    {
        //wird bei jedem Konstruktor ausgeführt
    }
    
    
    protected Pl(){
        System.out.println("Konstruktor " + this);
    }
    
    
    private Color color;
    private int terretories;
    private int areas;
    private int keeps;
    public  ArrayList<Bat> bat = new ArrayList<Bat>();
    
    
    
    
}
