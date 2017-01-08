/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author faust
 */
public class Obs {
    
    private int x,y;
    private int width, height;
    
    public void render(Graphics g){
       
        g.setColor(Color.pink);
        g.fillRect(x, y, 15, 15);
    }
    
    public void move(String s, Object o){
        switch (s){
           case "up":
               y--;
           break;
           default:
               y++;
        }
        StatusPanel.sp.refresh();
        
    }
    
}
