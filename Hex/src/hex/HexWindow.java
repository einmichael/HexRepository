/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;


import hex.Map.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author faust
 */
public class HexWindow extends JFrame{
    public final int BREIT = 500;
    public final int HOCH = 500;
    public static JPanel panel;
    public static int a = new Integer(50);
    private static Map map;
    
        
    public HexWindow(String s){
        super(s);
           
        map = new Map (5,5,a);
       

        setLocation(250, 250);
        setVisible(true);
        Insets insets = getInsets();
        setPreferredSize(new Dimension(
                BREIT + insets.left + insets.right,
                HOCH + insets.top + insets.bottom));

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //debug
                g.setColor(new Color(215,215,225));
                g.fillRect(0,0,BREIT, HOCH);
                map.drawPoints(g);
            }
        };

        add(panel);
        pack();
        

        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation() > 0){
                    a-=3;
                    map.makePoints(a);
                }
                else {
                    a+=3;
                    map.makePoints(a);
                
                }
                repaint();

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        
        
        
    }
    
}
