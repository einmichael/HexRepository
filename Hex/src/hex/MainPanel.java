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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    Point2D viewPort = (Point2D) new Point(0, 0);

    Point2D currentMouse = (Point2D) new Point(0, 0);
    Point2D currentMouseOnMap = (Point2D) new Point(0, 0);

    private AffineTransform old;

    public Obs obs;

    public StatusPanel father;
    public JLayeredPane layeredPane;
    public JButton testButton;
    public JPanel testPanel, glassPane, navigationPane, obsPane, mapPane;
    public int mouseX, mouseY;

    public JButton jButton;

    public MouseMotionListener myMainPanelMouseMotionListener;

    public MainPanel(StatusPanel statusPanel) {
        this.father = statusPanel;
        obs = new Obs(this);
        jButton = new JButton("T") {

        };
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        obs.pointTest();
                    }

                });
            }

        });
        jButton.setBounds(100, 400, 200, 200);
        this.add(jButton);

        myMainPanelMouseMotionListener
                = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouseX = e.getX();
                mouseY = e.getY();
                currentMouse.setLocation(e.getX(), e.getY());
                refreshMouse();

                //refresht statusPanel Labels mit Maus auf Panel und Maus auf Map
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                currentMouse.setLocation(e.getX(), e.getY());
                refreshMouse();

                repaint();
            }
        };

        addMouseMotionListener(myMainPanelMouseMotionListener);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                shiftViewPort();
            }
        });
        shiftViewPort();

    }

    public void shiftViewPort() {
        //später setViewPortLocationWithMapAdjust
        obs.setViewPortLocation(this.getWidth() / 2, this.getHeight() / 2);
    }

    public void refreshMouse() {
        try {
            obs.shiftPointToMap(currentMouse, currentMouseOnMap);
        } catch (Exception e) {

        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                father.refreshMouse(
                        currentMouse,
                        currentMouseOnMap);

            }
        });
        if (Map.map != null) {
            Map.map.updateMouse(currentMouseOnMap);
        }
        //System.out.println("mouse one Map" + currentMouseOnMap);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        old = g2D.getTransform();
        g2D.transform(obs.mapAT);
        //debug
        //map.drawPoints(g);
        Map.map.drawPolygons(g);

        g2D.setTransform(old);
        obs.render(g2D);

    }

    //Getter Setter
    public Obs getObs() {
        return obs;
    }
}
