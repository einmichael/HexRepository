/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Dimension;
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
    public Hex(String s){
        super(s);
        this.add(new JPanel(){
            
        });
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
