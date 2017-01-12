/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demorcm;

import java.awt.Graphics;

/**
 *
 * @author michael.faust
 */
public interface myClickEventListener {
    public void gotClicked(myClickEvent e);
    public void gotUnClicked(myClickEvent e);
    public void draw(Graphics g);
}
