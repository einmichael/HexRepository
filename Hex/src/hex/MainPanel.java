/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import hex.Map.Map;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author faust
 */
public class MainPanel extends JPanel {
    Point2D viewPort= (Point2D) new Point(0,0);
    
    private AffineTransform old;
    
    public Obs obs;
    
    public StatusPanel father;
    public JLayeredPane layeredPane;
    public JButton testButton;
    public JPanel testPanel, glassPane, navigationPane, obsPane, mapPane;
    public int mouseX, mouseY;
        
    public MainPanel(StatusPanel statusPanel){
        this.father=statusPanel;
        obs=new Obs(this);
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                
                mouseX = e.getX();
                mouseY = e.getY();
                
                //refresht statusPanel Labels mit Maus auf Panel und Maus auf Map
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        father.refreshMouse(mouseX, mouseY, obs.xToMap(mouseX), obs.yToMap(mouseY));
                    }
                });
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
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                shiftViewPort();
            }
        });
        shiftViewPort();
        
        
    }
    
    
    
    public void shiftViewPort(){
        //später setViewPortLocationWithMapAdjust
        obs.setViewPortLocation(this.getWidth()/2, this.getHeight()/2);
    }
    
    public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;
                old=g2D.getTransform();
                g2D.transform(obs.mapAT);
                //debug
                //map.drawPoints(g);
                Map.map.drawPolygons(g);
                
                g2D.setTransform(old);
                obs.render(g2D);
               
            }
}
