/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author michael.faust
 */
public class Hex extends JFrame {

    public static Hex hex;
    public static JPanel panel;
    
    public Hex(String s){
        super(s);
    }
    /*    *
     * @param args the command line arguments
    *
     */
    public static void main(String[] args) {
        hex =  new Hex("Sim");
        hex.setLocation(250,250);
        hex.setVisible(true);
        Insets insets = hex.getInsets();
        hex.setPreferredSize(new Dimension(
                500 + insets.left + insets.right,
                500 + insets.top + insets.bottom));
                
        panel=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.red);
                g.fillRect(100,100,200,400);
                                
            }
        };
        
        hex.add(panel);
        hex.pack();
               
        hex.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
 }
