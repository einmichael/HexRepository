/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex.Map;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author michael.faust
 */
public class Tile extends MapElement {
    
    public static int number=new Integer(0);
    
    private Tile neighborN, neighborNE, neighborSE, neighborS, neighborSW, neighborNW;
    private int x = new Integer(0), y = new Integer (0);
    
    public Tile (int x, int y){
        super();
        System.out.println(" " +number++);
        this.x=x;
        this.y=y;
        
    }
    
    public void hookUp(){
        //N und S
        neighborN=checkNeighbor(this.x, this.y-1);
        neighborS=checkNeighbor(this.x, this.y+1);
        //gerade Spalten "hoch"
        if(this.x%2==0){
            
        }
    }
    
    private Tile checkNeighbor(int x, int y){
        if (x<0) return null;
        if (y<0) return null;
        if (x>=Map.map.getX()) return null;
        if (y>=Map.map.getY()) return null;
        return Map.map.getTile(x,y);
    }
    
    @Override
    public void drawElement(Graphics g){
        if(neighborN!=null) g.setColor(Color.green);
        else g.setColor(Color.MAGENTA);
        g.fillRect(15*x, 15*y, 10, 10);
        
    }
}
