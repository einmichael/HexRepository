/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import Cont.PlFactory;
import hex.Map.Map;
import hex.Map.Tile;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 *
 * @author faust
 */
public class HexWindow extends JFrame {

    public final int BREIT = 500;
    public final int HOCH = 500;
    public JLayeredPane layeredPane;
    public StatusPanel statusPanel;
    public static int a = new Integer(50);
    private static Map map;

    public int mouseX, mouseY;
    
    public static MouseManager mouseManager;

    
    public HexWindow(String s) {
        
        super(s);
        mouseManager=new MouseManager();
        int input = new Integer(0);
        boolean valid = false;
        String name;
        while (!valid) {
            name = JOptionPane.showInputDialog(this, "Size? (1..50)");
            try {
                input = Integer.parseUnsignedInt(name);
                if(input<50&&input>0) valid=true;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Not Valid!");
            }
        }
        map = new Map(input, input, a);
        
        
        setLocation(250, 250);
        setVisible(true);
        Insets insets = getInsets();
        setPreferredSize(new Dimension(
                BREIT + insets.left + insets.right,
                HOCH + insets.top + insets.bottom));

        
      
        
        statusPanel=new StatusPanel();
        add(statusPanel);
        
        pack();
        setMinimumSize(new Dimension(400,400));
        

        addMouseWheelListener(mouseManager);
        
        /*
        addMouseListener(new MouseAdapter(){
           @Override
           public void mousePressed(MouseEvent e){
               
               System.out.println("Klick");
               PlFactory.INIT=true;
               repaint();
               
           }
        });
*/
        addMouseListener(mouseManager);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        PlFactory.getInstance().make();

    }

}
