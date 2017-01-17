/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 *
 * @author faust
 */
public class StatusPanel extends JPanel {

    public int mouseX, mouseY;
    public static StatusPanel sp;
    public static final int xGap = 5, yGap = 5;
    public JLabel topl, topr;
    public JPanel top;
    public MainPanel middle;

    public static void refresh() {
        sp.repaint();
    }

    public StatusPanel() {
        sp = this;
        this.setLayout(new BorderLayout(xGap, yGap));
        top = new JPanel();
        topl = new JLabel("TOP Left", JLabel.CENTER);
        topl.setForeground(Color.red);
        topl.setFont(new Font("Serif", Font.BOLD, 36));
        topr = new JLabel("TOP Right", JLabel.CENTER);
        topr.setForeground(Color.blue);
        topr.setFont(new Font("Serif", Font.BOLD, 36));

        top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
        top.add(Box.createHorizontalGlue());
        top.setBorder(BorderFactory.createEtchedBorder(new Color(127, 127, 155), Color.black));
        top.add(topl);
        top.add(Box.createRigidArea(new Dimension(10, 0)));
        top.add(topr);
        top.add(Box.createHorizontalGlue());
        this.add(top, BorderLayout.PAGE_START);

        middle = new MainPanel(this);
        this.add(middle, BorderLayout.CENTER);
        middle.setMinimumSize(new Dimension(100, 100));

    }

    public void refreshMouse(int x, int y, int xM, int yM) {
        topl.setText("" + x + "/" + y);
        topr.setText("" + xM + "/" + yM);
        repaint();
    }

}
