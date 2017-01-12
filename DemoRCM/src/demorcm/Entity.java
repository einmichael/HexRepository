/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demorcm;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author michael.faust
 */
public class Entity implements myClickEventListener {
    
    public BufferedImage backImage;
    public BufferedImage midImage;
    public BufferedImage frontImage;
    
    public Rectangle mySize;
    public boolean isColored= false;
   
    
    @Override
    public void gotClicked(myClickEvent e){
        if(isColored)isColored=false;
        else isColored=true;
    }
    @Override
    public void gotUnClicked(myClickEvent e){
        
    }
    
    public void draw(Graphics g){
        
    }
    
}
