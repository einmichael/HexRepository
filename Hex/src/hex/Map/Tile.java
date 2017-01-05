/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author michael.faust
 */
public class Tile extends MapElement {
    
    public static int number=new Integer(0);
    public Polygon polygon;
    
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
            neighborNE=checkNeighbor(this.x+1, this.y-1);
            neighborSE=checkNeighbor(this.x+1, this.y);
            neighborSW=checkNeighbor(this.x-1, this.y);
            neighborNW=checkNeighbor(this.x-1, this.y-1);
        //ungerade Spalte "tief"
        }else{
            neighborNE=checkNeighbor(this.x+1, this.y);
            neighborSE=checkNeighbor(this.x+1, this.y+1);
            neighborSW=checkNeighbor(this.x-1, this.y+1);
            neighborNW=checkNeighbor(this.x-1, this.y);
        }
    }
    
    /**
     * Returns the tile of the Map on (x,y), if x and y are in bounds.
     * Else null.
     * @param x
     * @param y
     * @return 
     */
    private Tile checkNeighbor(int x, int y){
        if (x<0) return null;
        if (y<0) return null;
        if (x>=Map.map.getX()) return null;
        if (y>=Map.map.getY()) return null;
        return Map.map.getTile(x,y);
    }
    
    public void makePolygon(){
        this.polygon=Map.map.makePolygon(this);
    }
    
    @Override
    public void drawElement(Graphics g){
        g.setColor(Color.blue);
        g.fillPolygon(polygon);
        g.setColor(Color.pink);
        g.drawPolygon(polygon);
        /*
        //debug
        g.setColor(Color.blue);
        g.fillRect(50*x, 50*y, 40, 40);
        
        g.setColor(Color.MAGENTA);
        if(neighborN!=null) g.fillRect(50*x+15, 50*y, 10, 5);
        if(neighborS!=null) g.fillRect(50*x+15, 50*y+35, 10, 5);
        if(neighborNE!=null) g.fillRect(50*x+30, 50*y, 10, 5);
        if(neighborNW!=null) g.fillRect(50*x, 50*y, 10, 5);
        if(neighborSE!=null) g.fillRect(50*x+30, 50*y+35, 10, 5);
        if(neighborSW!=null) g.fillRect(50*x, 50*y+35, 10, 5);*/
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
