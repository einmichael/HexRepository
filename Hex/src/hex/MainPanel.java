/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import hex.Map.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author faust
 */
public class MainPanel extends JPanel {
    Point2D viewPort= (Point2D) new Point(0,0);
    public StatusPanel father;
    public JLayeredPane layeredPane;
    public JButton testButton;
    public JPanel testPanel, glassPane, navigationPane, obsPane, mapPane;
    public int mouseX, mouseY;
        
    public MainPanel(StatusPanel statusPanel){
        this.father=statusPanel;
        layeredPane=new JLayeredPane(){
            
        };
        layeredPane.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
               /* System.out.println("mouse"+e.getX());
                mouseX = e.getX();
                mouseY = e.getY();
                father.refreshMouse(mouseX, mouseY, mouseX+1, mouseY+2);*/
            }
        });
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
       this.add(layeredPane);
       layeredPane.setVisible(true);
       testPanel=new JPanel();
       
        layeredPane.add(testPanel,new Integer(800));
              testButton=  new JButton("TestButton1234"){
        };
              
  testPanel.add(testButton);
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                shiftViewPort();
            }
        });
        
        shiftViewPort();
                
    }
    
    public void shiftViewPort(){
        viewPort.setLocation(this.getWidth()/2, this.getHeight()/2);
    }
    
    public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;
                
                //debug
                //map.drawPoints(g);
                Map.map.drawPolygons(g);
                drawViewPort(g2D);
               
            }
    
    public void drawViewPort(Graphics2D g){
        g.setColor(Color.black);
        g.drawString("Viewport: "+viewPort.getX(),0,0);
    }
}
