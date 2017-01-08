/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JLayeredPane;

/**
 *
 * @author faust
 */
public class StatusPanel extends JPanel {
    
    public int mouseX, mouseY;

    public static final int xGap = 5, yGap = 5;
    public JLabel top;
    public GamePanel middle;
    public JLayeredPane pane;

    public StatusPanel() {
        this.setLayout(new BorderLayout(xGap, yGap));

        top = new JLabel("TOP", JLabel.CENTER);
        top.setForeground(Color.pink);
        top.setFont(new Font("Serif", Font.BOLD, 48));
        

        
        middle = new GamePanel();
        

        this.add(top, BorderLayout.PAGE_START);
        this.add(middle, BorderLayout.CENTER);
        middle.setMinimumSize(new Dimension(100, 100));
        
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouseX = e.getX();
                mouseY = e.getY();
                top.setText("x: "+ mouseX + ", y: "+mouseY);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                
            }

        });
        addMouseListener(HexWindow.mouseManager);

    }

}