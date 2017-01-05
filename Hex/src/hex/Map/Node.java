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
public class Node extends MapElement {
    
    private Node neighborN, neighborNE, neighborSE, neighborS, neighborSW, neighborNW;
    private int x = new Integer(0), y = new Integer (0);
    
    public Node (int x, int y){
        super();
        this.x=x;
        this.y=y;
        
    }
    
    @Override
    public void drawElement(Graphics g){
        g.setColor(Color.yellow);
        
    }
}
