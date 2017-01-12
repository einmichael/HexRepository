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

    AffineTransform at = new AffineTransform();
    AffineTransform atTrans = new AffineTransform();
    AffineTransform atScale = new AffineTransform();
    AffineTransform old;
    public Point2D src;
    public Point2D dst;

    int camX = 100, camY = 100, camW = 160, camH = 160;
    int vpX = camX + camW / 2, vpY = camY + camH / 2;

    int mouseX, mouseY;

    public int scale = 10;
    public int scaleMax = 20, scaleMin = 5, scaleInc = 1;
    public int xOffset, yOffset;

    public class MyPanel extends JPanel {

        Graphics2D g2D;

        public MyPanel() {
            this.setPreferredSize(new Dimension(500, 500));
            at = new AffineTransform();
            JButton button1 = new JButton("Translate +");
            this.add(button1);
            button1.setFocusable(false);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    atTrans.concatenate(AffineTransform.getTranslateInstance(5, 5));
                    refresh();
                    repaint();
                }
            ;
            });
            JButton button2 = new JButton("Translate -");
            button2.setFocusable(false);
            this.add(button2);
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    atTrans.concatenate(AffineTransform.getTranslateInstance(-5, -5));
                    refresh();
                    repaint();
                }
            ;
            });
                        
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    refresh();
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

            g2D.transform(at);

            //draw on tranlated g
            draw(g2D);
            g2D.setTransform(old);

            //draw on normal g
            //draw CameraRect
            g.setColor(Color.black);
            g.drawRect(camX, camY, camW, camH);
            g.setColor(Color.magenta);
            g.drawRect(vpX - 1, vpY - 1, 2, 2);
            drawCoords(g);

        }

        public void draw(Graphics2D g2D) {
            g2D.setColor(Color.red);
            g2D.fillRect(0, 0, 100, 100);
        }

        public void drawCoords(Graphics g) {
            g.setColor(Color.black);
            g.drawString(("zu Map: " + (mouseX - camX) + "/" + (mouseY - camY)), mouseX + 20, mouseY + 10);
            g.drawString(("zu rot: " + (int) dst.getX() + "/" + (int) dst.getY()), mouseX + 20, mouseY + 20);

        }
    }

    
    
    public void refresh() {
        //Translate fix, scale angehängt!
        at=(AffineTransform) atTrans.clone();
        at.concatenate(atScale);
        //Translate wird gescalet! Beim Scalen verschiebt sich offset
       // at=(AffineTransform) atScale.clone();
       // at.concatenate(atTrans);


        System.out.println("Trans: " + atTrans);
        System.out.println("Scale: " + atScale);
        System.out.println("at: " + at);

        src.setLocation((double) mouseX, (double) mouseY);
        try {
            at.inverseTransform(src, dst);

        } catch (Exception e) {

        }

    }

    public ATTestMap(String s) {
        super(s);

        src = (Point2D) new Point(0, 0);
        dst = (Point2D) new Point(0, 0);

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
                if (e.getWheelRotation() > 0) {
                    if (scale >= scaleMin + scaleInc) {
                        scale -= scaleInc;
                    }
                } else if (scale <= scaleMax - scaleInc) {
                    scale += scaleInc;
                }
                System.out.println("Scale: " + scale);
                atScale.setToScale((double) scale / 10, (double) scale / 10);
                refresh();
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Verkackte Taste!!!!!!!!!!!!!!!!!!!!!");
                int x=0, y=0;
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
                atTrans.concatenate(AffineTransform.getTranslateInstance(x,y));
                refresh();
                repaint();

            }
        });

    }

}
