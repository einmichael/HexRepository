/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Graphics;
import myEvents.ScrollBroadcaster;
import myEvents.ScrollEvent;
import myEvents.ScrollListener;

/**
 * scrolling, movement, holds ats, offers trans and invtrans for ats
 *
 * @author faust
 */
public class Obs implements ScrollListener {

    private int x, y;
   
    @Override
    public void scrollUpdate(ScrollEvent e) {
        System.out.println("Update empfangen!!!! " + e.getA() + " " + this);
    }

    public Obs() {
        ScrollBroadcaster.getInstance().addScrollListener(this);
    }

    //debug
    public void render(Graphics g) {
        
        g.setColor(Color.pink);
        g.fillRect(x, y, 15, 15);
    }

    public void move(String s, Object o) {
        switch (s) {
            case "up":
                y--;
                break;
            case "down":
                y++;
                break;
            default:

        }
        StatusPanel.sp.refresh();

    }

}
