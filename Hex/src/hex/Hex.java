/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 *
 * @author michael.faust
 */
public class Hex extends JFrame {

    public static Hex hex;
    public JPanel panel;
    
    public Hex(String s){
        super(s);
        panel=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.setColor(Color.red);
                super.paintComponent(g);
                g.drawRect(100,100,200,400);
            }
        };
        
        this.add(panel);
        panel.setVisible(true);
        this.repaint();
    }
    /*    *
     * @param args the command line arguments
    *
     */
    public static void main(String[] args) {
        hex =  new Hex("Sim");
        hex.setLayout(null);
        hex.setLocation(250,250);
        hex.setPreferredSize(new Dimension(500,500));
        hex.pack();
        hex.setVisible(true);
        
        hex.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
 }
