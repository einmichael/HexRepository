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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
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

    public JLayeredPane layeredPane;
    public StatusPanel statusPanel;

    public static int a = new Integer(50);
    public static final int aMin= new Integer(20);
    public static final int aInc= new Integer(5);
    public static final int aMax= new Integer(80);

    private static Map map;

    public int mouseX, mouseY;

    public static KeyManager keyManager;

    public HexWindow(String s) {

        super(s);
        keyManager = new KeyManager();
        addKeyListener(keyManager);
        
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
                if (e.getWheelRotation() > 0) {
                    if(a>=aMin+aInc)
                    a -= aInc;
                } else {
                    if(a<=aMax-aInc)
                    a += aInc;
                }
                //System.out.println("mouseWheelMoved" + e);
                ScrollBroadcaster.getInstance().fire(new ScrollEvent(this, a));
                e.getComponent().repaint();
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
        if (keyManager.down) {
            Map.obs.move("down", this);
        }
        if (keyManager.up) {
            Map.obs.move("up", this);
        }
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
