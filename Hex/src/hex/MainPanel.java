/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import hex.Map.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author faust
 */
public class MainPanel extends JPanel {
    StatusPanel father;
    public int mouseX, mouseY;
        
    public MainPanel(StatusPanel statusPanel){
        this.father=statusPanel;
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                
                mouseX = e.getX();
                mouseY = e.getY();
                father.refreshMouse(mouseX, mouseY, mouseX+1, mouseY+2);
                if (Map.map != null) {
                    Map.map.updateMouse(mouseX, mouseY);
                }
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                if (Map.map != null) {
                    Map.map.updateMouse(mouseX, mouseY);
                }
                repaint();
            }
        });
        add(new JButton("TestButton1234"){
        });
                
    }
    
    public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //debug
                //map.drawPoints(g);
                Map.map.drawPolygons(g);
            }
}
