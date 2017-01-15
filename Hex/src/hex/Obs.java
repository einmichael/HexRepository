/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import myEvents.ScrollBroadcaster;
import myEvents.ScrollEvent;
import myEvents.ScrollListener;

/**
 * scrolling, movement, holds ats, offers trans and invtrans for ats
 *
 *
 * @author faust
 */
public class Obs implements ScrollListener {

    public AffineTransform mapAT;
    public AffineTransform mapATTrans;

    public MainPanel mainPanel;

    public AffineTransform VPAT;
    private AffineTransform tmpAT;

    public int scale = 10;
    public int scaleMax = 20, scaleMin = 5, scaleInc = 1, scaleDelta = 0;

    public Point2D oldViewPort;
    public Point2D currentViewPort;
    public Point2D tmpPoint;

    public Point2D src;
    public Point2D dst;

    private int mouseX, mouseY;

    private int x, y;

    @Override
    public void scrollUpdate(ScrollEvent e) {
        System.out.println("Update empfangen!!!! " + e.getA() + " " + this);
        //InvokeLater
        adjustScale(e.getA());
        debugShowATs();

    }

    public void debugShowATs() {
        System.out.println("Map: " + mapAT);
        System.out.println("VP : " + VPAT);
    }

    public Obs(MainPanel main) {
        this.mainPanel = main;
        ScrollBroadcaster.getInstance().addScrollListener(this);
        //resizeListener von Frame nicht benötigt, eigene Methode

        currentViewPort = (Point2D) new Point(0, 0);
        oldViewPort = (Point2D) new Point(0, 0);
        VPAT = new AffineTransform();

        mapAT = new AffineTransform();
        mapATTrans = new AffineTransform();

    }

    public void camToMap(Point2D cam, Point2D map) {

        //camtransform (VP)
        //danach invTransform von Map
        tmpAT = (AffineTransform) VPAT.clone();
        try {
            tmpAT.concatenate(mapAT.createInverse());
        } catch (Exception e) {
            System.out.println("" + e);
        }
        tmpAT.transform(cam, map);

    }

    public int xToMap(int x) {

        // hin return (int) (mapAT.getScaleX()*((double) x)+mapAT.getTranslateX());
        //zurück
        return (int) 
                ((((double) x) - mapAT.getTranslateX())
                / mapAT.getScaleX());
    }

    public int yToMap(int y) {
        // hin return (int) (mapAT.getScaleY() * ((double) y) + mapAT.getTranslateY());
        //zurück
        return (int) 
                ((((double) y) - mapAT.getTranslateY())
                / mapAT.getScaleY());
    }

    public void refreshViewPort() {
        Point2D tmp;

    }

    public void setOldViewPort() {
        tmpPoint = (Point2D) new Point(0, 0);
        camToMap(currentViewPort, tmpPoint);
        oldViewPort.setLocation(tmpPoint);
        System.out.println("OldViewPort set to: " + oldViewPort);
    }

    public void setViewPortLocation(int x, int y) {
        System.out.println("SetVPLoc" + x);
        currentViewPort.setLocation((double) x, (double) y);
        VPAT = AffineTransform.getTranslateInstance(
                currentViewPort.getX(),
                currentViewPort.getY());
        mainPanel.repaint();
    }

    public void adjustToNewViewPort() {
        mapATTrans.concatenate(AffineTransform.getTranslateInstance(
                (int) (currentViewPort.getX() - oldViewPort.getX()),
                (int) (currentViewPort.getY() - oldViewPort.getY())
        ));
        refreshMapAT();

    }

    public void refreshMapAT() {
        mapAT = AffineTransform.getScaleInstance((double) scale / 10, (double) scale / 10);
        mapAT.concatenate(mapATTrans);
    }

    public void adjustScale(int e) {
        //alten Viewport berechnen
        setOldViewPort();

        if (e < 0) {
            if (scale + scaleInc <= scaleMax) {
                scale += scaleInc;
            }
        } else if (scale - scaleInc >= scaleMin) {
            scale += -scaleInc;
        }

        refreshMapAT();
        // adjustToNewViewPort();

        mainPanel.repaint();
    }

    //debug
    public void render(Graphics g) {
        //System.out.println("render");
        
        //ohne verschiebung. aufruf mit transform auf g!
        g.setColor(Color.pink);
        g.fillRect(
                (int) currentViewPort.getX() - 1,
                (int) currentViewPort.getY() - 1,
                2, 2);

        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
        g.drawString("Viewport xMap/yMap: " 
                + xToMap((int) currentViewPort.getX())
                + "/" 
                + yToMap((int) currentViewPort.getY())
                , 15,35);
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
