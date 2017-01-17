/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import Cont.PlFactory;
import hex.Map.Map;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import myEvents.ScrollBroadcaster;
import myEvents.ScrollEvent;
import myEvents.ScrollListener;

/**
 *
 * @author faust
 */
public class HexWindow extends JFrame implements Runnable, ScrollListener {

    public final static int TPS = 25;

    public final int BREIT = 500;
    public final int HOCH = 500;

    public StatusPanel statusPanel;

    public static int a = new Integer(50);


    private static Map map;

    public int mouseX, mouseY;

    public static KeyManager keyManager;

    private void getInput(){
        int input = new Integer(0);
        boolean valid = false;
        String name;
        while (!valid) {
            name = JOptionPane.showInputDialog(this, "Size? (1..50)");
            try {
                input = Integer.parseUnsignedInt(name);
                if (input < 50 && input > 0) {
                    valid = true;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Not Valid!");
            }
        }
        this.input=input;
    }
    int input = new Integer(2);
    public HexWindow(String s) {

        super(s);
        keyManager = new KeyManager();
        addKeyListener(keyManager);
        
        getInput();
        /*test        
        SwingUtilities.invokeLater(new Runnable (){
          public void run(){
              getInput();
          }  
        });*/
        
        map = new Map(input, input, a);
        ScrollBroadcaster.getInstance().addScrollListener(this);
        
        setLocation(250, 250);
        setMinimumSize(new Dimension(400, 400));
        setVisible(true);
        Insets insets = getInsets();
        setPreferredSize(new Dimension(
                BREIT + insets.left + insets.right,
                HOCH + insets.top + insets.bottom));

        statusPanel = new StatusPanel();
        add(statusPanel);
        pack();

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                ScrollBroadcaster.getInstance().fireScrollEvent(new ScrollEvent(this, e.getWheelRotation()));
                e.consume();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        PlFactory.getInstance().make();
    }
    
    @Override
        public void scrollUpdate(ScrollEvent e){
            //notneeded
    };
        
    //physics, inputs, etc.

    public void tick() {
       /*
        
        if (keyManager.down) {
            Map.obs.move("down", this);
        }
        if (keyManager.up) {
            Map.obs.move("up", this);
        }*/
    }

    long timecheck, delta;
    int TPScounter;
    
    ActionListener timerEnd = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            TPScounter++;
            delta = System.nanoTime() - timecheck;

            if (delta > 1000000000l) {
                System.out.println("Running with TPS (set to " + TPS + ") : " + TPScounter);
                timecheck = System.nanoTime();
                TPScounter = 0;
            }
            tick();
        }
    };

    Timer timer = new Timer(1000 / TPS, timerEnd);
    @Override
    public void run() {
        timer.start();
        //repaint();
    }
}
