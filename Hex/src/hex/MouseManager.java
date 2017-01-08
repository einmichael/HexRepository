/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import hex.Map.Map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author faust
 */
public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener{
    
    public int mouseX, mouseY;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked called by " + e.getComponent() + " at X/Y: " + e.getX() + "/"+ e.getY());
    
    }
    
    @Override
    public void mouseExited(MouseEvent e){
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("mousePressed called by " + e.getComponent() + " at X/Y: " + e.getX() + "/"+ e.getY());
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
    
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        mouseX=e.getX();
        mouseY=e.getY();
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        if (e.getWheelRotation() > 0) {
                    HexWindow.a -= 3;
                } else {
                    HexWindow.a += 3;
                }
                Map.map.refresh(HexWindow.a);
                e.getComponent().repaint();
        
    }
}
