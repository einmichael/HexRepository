/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex.Map;

import Cont.Neutral;
import Cont.Pl;
import Cont.PlFactory;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author michael.faust
 */
public class Tile extends MapElement {
    
    //public static int number=new Integer(0);
    public Polygon polygon;
    public Color color = Color.blue;
    public Pl controller;
    public int bats= 0;
    
    private Tile 
            neighborN, neighborNE, neighborSE, 
            neighborS, neighborSW, neighborNW;
    private int x, y;
    
    public Tile (int x, int y){
        super();
        this.x=x;
        this.y=y;
        
    }
    
    /**
     * legt alle Nachbarn an
     */
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
    
    private Tile checkNeighbor(int x, int y){
        return Map.map.getTile(x,y);
    }
    
    public void makePolygon(){
        this.polygon=Map.map.makePolygon(this);
    }
    
    public void colorNeighbors(){
        if(neighborN!=null)neighborN.color=Color.orange;
        if(neighborS!=null)neighborS.color=Color.green;
        if(neighborSE!=null)neighborSE.color=Color.green;
        if(neighborNE!=null)neighborNE.color=Color.green;
        if(neighborSW!=null)neighborSW.color=Color.green;
        if(neighborNW!=null)neighborNW.color=Color.green;
        
    }
    
    @Override
    public void drawElement(Graphics g){
        g.setColor(color);
        g.fillPolygon(polygon);
        Graphics2D g2d = (Graphics2D) g;
         g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.CAP_BUTT));
        g2d.setColor(Color.gray);
        g2d.drawPolygon(polygon);
        
        if(PlFactory.INIT){
            g.setColor(color.black);
            g.drawString(Integer.toString(bats), polygon.xpoints[0]+20, polygon.ypoints[0]+20);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
