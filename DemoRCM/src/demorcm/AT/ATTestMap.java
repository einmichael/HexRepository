/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demorcm.AT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author michael.faust
 */
public class ATTestMap extends JFrame {

    public Point2D src;
    public Point2D dst;

    public AffineTransform old;

    int mouseX, mouseY;

    public int scale = 10;
    public int scaleMax = 20, scaleMin = 5, scaleInc = 1, scaleDelta = 0;
    public int xOffset, yOffset;

    public class myAT {

        public AffineTransform at;
        public AffineTransform atTrans;
        public AffineTransform atScale;

        Point2D mouseOld, mouseCurrent = (Point2D) new Point(0, 0);
        public int scale;
        public int tX, tY;
        Point2D destination;
        Point2D source;
        public myAT() {
            System.out.println("Konstruktor " + this.getClass());
            destination = (Point2D) new Point(0, 0);
            source = (Point2D) new Point(0, 0);
            scale = 10;
            tX = 0;
            tY = 0;
            atTrans = new AffineTransform();
            at = new AffineTransform();
            atScale = AffineTransform.getScaleInstance(1, 1);
            refresh();
        }
        public void showInfo() {
            System.out.println(" " + this);
            System.out.println("scale: " + scale + ", tX: " + tX + ", tY: " + tY);
            System.out.println("atTrans: " + atTrans);
            System.out.println("atScale: " + atScale);
            System.out.println("at: " + at);
        }
        public void refresh() {
            //at = (AffineTransform) atTrans.clone();
            //at.concatenate(atScale);
            at = (AffineTransform) atScale.clone();
            at.concatenate(atTrans);
            
            showInfo();
            
        }
        public void addTrans(int x, int y) {
            tX += x;
            tY += y;
            atTrans.concatenate(AffineTransform.getTranslateInstance(x, y));
            refresh();
        }
        public void changeScale(int scale) {
            this.scale = scale;
            atScale.setToScale((double) scale / 10, (double) scale / 10);
            refresh();
        }
        public Point2D trans(int x, int y) {
            source.setLocation(x, y);
            at.transform(source, destination);
            return destination;
        }
        public Point2D invTrans(int x, int y) {
            source.setLocation(x, y);
            try {
                at.inverseTransform(source, destination);
            } catch (Exception e) {
            }
            return destination;
        }
        public AffineTransform getAT() {
            return at;
        }
    }
    public myAT mapAT;
    public myAT camAT;
    Graphics2D g2D;
    public class MyPanel extends JPanel {
        public MyPanel() {
            System.out.println("Konstruktor " + this.getClass());
            this.setPreferredSize(new Dimension(500, 500));
            JButton button1 = new JButton("Camera 1");
            this.add(button1);
            button1.setFocusable(false);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    camAT.atTrans = AffineTransform.getTranslateInstance(10, 10);
                    camAT.refresh();
                    repaint();
                }
            ;
            });
            JButton button2 = new JButton("Camera 2");
            button2.setFocusable(false);
            this.add(button2);
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    camAT.atTrans = AffineTransform.getTranslateInstance(100, 100);
                    camAT.refresh();
                    repaint();
                }
            ;
            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }
            });
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2D = (Graphics2D) g;
            old = g2D.getTransform();
            //draw on normal g
            //transform
            g2D.transform(mapAT.at);
            //draw on Map-tranlated g
            draw(g2D);
            //Camera-related
            g2D.setTransform(camAT.at);
            drawCam(g2D);
            //old
            g2D.setTransform(old);
            //draw on normal g
            drawCoords(g);
        }
        public void draw(Graphics2D g2D) {
            g2D.setColor(Color.red);
            g2D.fillRect(0, 0, 100, 100);
            //g2D.drawImage(RCMWindow.w35AB, 0,0,400,400,null);
        }
        public void drawCam(Graphics2D g2D) {
            Point2D tmp1, tmp2;
            AffineTransform t;
            g2D.setColor(Color.black);
            g2D.drawRect(0, 0, 100, 100);
            g2D.setColor(Color.magenta);
            g2D.drawRect(49, 49, 2, 2);
            //Cam-Viewport nach fenster-koordinaten holen
            tmp1 = camAT.trans(50, 50);
            //fenster in map-Koordinaten
            tmp2 = mapAT.invTrans((int) tmp1.getX(), (int) tmp1.getY());
            g2D.drawString(("mapX/Y: " + tmp2.getX() + "/" + tmp2.getY()), 55, 55);
        }
        public void drawCoords(Graphics g) {
            g.setColor(Color.black);
            Point2D tmp = (Point2D) new Point(0, 0);
            tmp = mapAT.invTrans(mouseX, mouseY);
            g.drawString(("in Frame: " + mouseX + "/" + mouseY), mouseX + 20, mouseY + 10);
            g.drawString(("zu Map  : " + tmp.getX() + "/" + tmp.getY()), mouseX + 20, mouseY + 25);
            tmp = camAT.invTrans(mouseX, mouseY);
            g.drawString(("zu Cam  : " + tmp.getX() + "/" + tmp.getY()), mouseX + 20, mouseY + 40);
        }
    }
    public ATTestMap(String s) {
        super(s);
        System.out.println("Konstruktor " + this.getClass());
        mapAT = new myAT();
        camAT = new myAT();
        mapAT.changeScale(7);
        System.out.println("" + mapAT.getAT());
        System.out.println("jo");
        this.setVisible(true);
        this.setBounds(200, 200, 400, 400);
        this.add(new MyPanel());
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();
                Point2D tmp1, tmp2=(Point2D)new Point(0,0), tmp3=(Point2D) new Point(0,0);
                //cam in fenster
                tmp1 = camAT.trans(50, 50);
                //fenster- in map-Koordinaten
                tmp2 = (Point2D) mapAT.invTrans((int) tmp1.getX(), (int) tmp1.getY()).clone();
                
                scale = mapAT.scale;
                if (e.getWheelRotation() > 0) {
                    if (scale >= scaleMin + scaleInc) {
                        scaleDelta = scale - scaleInc;
                    }
                } else if (scale <= scaleMax - scaleInc) {
                    scaleDelta = scale + scaleInc;
                }
                System.out.println("Scale: " + scale);
                mapAT.changeScale(scaleDelta);
                
                
                //cam in fenster
                tmp1 = camAT.trans(50, 50);
                //fenster- in map-Koordinaten
                tmp3 = mapAT.invTrans((int) tmp1.getX(), (int) tmp1.getY());
                
                System.out.println("vorher "+tmp2);
                System.out.println("nachher "+tmp3);
                
                mapAT.addTrans((int) (tmp3.getX()-tmp2.getX()),(int) (tmp3.getY()-tmp2.getY()));
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Taste gedrückt!");
                int x = 0, y = 0;
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    x = 5;
                    y = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    x = -5;
                    y = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    x = 0;
                    y = 5;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    x = 0;
                    y = -5;
                }
                mapAT.addTrans(x, y);
                repaint();
            }
        });
    }
}
