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
public class GamePanel extends JPanel {
    public int mouseX, mouseY;
    
    
    public GamePanel(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouseX = e.getX();
                mouseY = e.getY();
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
        add(new JButton("Testa<srgsdfhsdfhsfh"){
            
        });
        
        addMouseListener(HexWindow.mouseManager);
    }
    
    public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //debug
                g.setColor(new Color(215, 215, 225));
                //g.fillRect(0, 0, BREIT, HOCH);
                //map.drawPoints(g);
                Map.map.drawPolygons(g);

                //g.setColor(Color.black);
                //g.drawString("x/y: " + mouseX + "/" + mouseY, BREIT - 100, HOCH - 20);
                
            }
}
