/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demorcm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author michael.faust
 */
public class RCMWindow extends JFrame{
    BufferedImage linesVertimage;
    BufferedImage rcms150;
    public static BufferedImage w35AB;
    BufferedImage w35ABfront;
    
    JPanel myPanel =  new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(w35AB,10,0,150,120,null);
            g.drawImage(linesVertimage, 50, 0, null);
            g.drawImage(linesVertimage, 50, 72, null);
            //g.drawImage(rcms150, 10, 20,300,100, null);
             g.drawImage(w35ABfront,10,0,150,120,null);
            
        }
    };
    
    public RCMWindow(String s){
        super(s);
        linesVertimage = loadImage("/img/L123N-vert-29-72.png");
        rcms150= loadImage("/img/RCMS150.png");
        w35AB = loadImage("/img/W35AB.png");
        w35ABfront = loadImage("/img/W35AB-front.png");
         
        this.setLocation(250,250);
        myPanel.setPreferredSize(new Dimension(500,500));
        this.add(myPanel);
        this.pack();
        this.setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    private BufferedImage loadImage(String s){
        BufferedImage img;
        try{
            img = ImageIO.read(getClass().getResource(s));
        }catch (Exception e){
            System.out.println("Fehler bei  "+s +"! "+e);
            img=null;
        }
        return img;
    }
}
