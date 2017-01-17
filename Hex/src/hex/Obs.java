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
import javax.swing.JButton;
import javax.swing.SwingUtilities;
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

    //map insgesamt mapAT
    public AffineTransform mapAT;
    // mapATTrans nur Translation
    public AffineTransform mapATTrans;
    //scale ist frisch in scale, AT wird aus Scale gebaut
    public int scale = 10;
    public int scaleMax = 20, scaleMin = 5, scaleInc = 1, scaleDelta = 0;
    
    //ViewPort AT
    public AffineTransform VPAT;
    //tmp AT
    private AffineTransform tmpAT;

    public Point2D oldViewPort;
    public Point2D currentViewPort;
    public Point2D tmpPoint;

    public Point2D src;
    public Point2D dst;

    
   
    
    private int mouseX, mouseY;

    private int x, y;

    public MainPanel mainPanel;
    
    //Konstruktor
    public Obs(MainPanel main) {
        this.mainPanel = main;
        ScrollBroadcaster.getInstance().addScrollListener(this);

        
        currentViewPort = (Point2D) new Point(0, 0);
        oldViewPort = (Point2D) new Point(0, 0);

        VPAT = new AffineTransform();
        mapAT = new AffineTransform();
        mapATTrans = new AffineTransform();
    }
    
    @Override
    public void scrollUpdate(ScrollEvent e) {
        System.out.println("Update empfangen!!!! " + e.getA() + " " + this);
        //InvokeLater
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                adjustScale(e.getA());
                debugShowATs();
            }
        });
    }

    public void debugShowATs() {
        System.out.println("Map: " + mapAT);
        System.out.println("VP : " + VPAT);
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
    
   

    public void refreshViewPort() {

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

    private void l(String s){
        System.out.println(""+s);
    }
    
    private Point2D pointOnMap;
    private Point2D point;
    
    /**
     * takes p and transforms to Map-x/y
     * into pointOnMap
     * @param p 
     */
    private void shiftPointToMap(Point2D p){
        pointOnMap = (Point2D) new Point(0,0);
        point = (Point2D) p.clone();
        try{
            mapAT.inverseTransform(p, pointOnMap);
        }catch(Exception e){
            
        }
    }
    /**
     * takes p and transforms to Map-x/y
     * into pointOnMap
     * @param p 
     */
   /* 
    //funktioniert!
    private void shiftPointToMap(Point2D p, Point2D into){
        Point2D tmp = (Point2D) new Point(0,0);
        
        try{
            mapAT.inverseTransform(p, tmp);
        }catch(Exception e){
            
        }
        into.setLocation(tmp);
    }*/
    
    //funktioniert!
    private void shiftPointToMap(Point2D p, Point2D into){
               
        try{
            mapAT.inverseTransform(p, into);
        }catch(Exception e){
            
        }
        
    }

    public int xToMap(int x) {
        // hin return (int) (mapAT.getScaleX()*((double) x)+mapAT.getTranslateX());
        //zurück
        return (int) ((((double) x) - mapAT.getTranslateX())
                / mapAT.getScaleX());
    }

    public int yToMap(int y) {
        // hin return (int) (mapAT.getScaleY() * ((double) y) + mapAT.getTranslateY());
        //zurück
        return (int) ((((double) y) - mapAT.getTranslateY())
                / mapAT.getScaleY());
    }
    //debug
    public void pointTest(){
        int x= (int) currentViewPort.getX();
        int y= (int) currentViewPort.getY();
        l(" ");
        
        l("x:" + x);
        l("y:" + y);
        l("x in Map man. : " + xToMap(x));
        l("y in Map man. : " + yToMap(y));
        point = (Point2D) currentViewPort.clone();
        shiftPointToMap(point);
        l("Point (origin)" + point);
        l("x/y in Map via AT:"+pointOnMap.getX() + "/" + pointOnMap.getY());
        shiftPointToMap(new Point(20,20), pointOnMap);
        l("x/y in Map via AT:"+pointOnMap.getX() + "/" + pointOnMap.getY());
        
        
        
    }
    
    public void render(Graphics g) {
        //ohne verschiebung. aufruf mit transform auf g!
        g.setColor(Color.pink);
        g.fillRect(
                (int) currentViewPort.getX() - 1,
                (int) currentViewPort.getY() - 1,
                2, 2);

        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Viewport xMap/yMap: "
                + xToMap((int) currentViewPort.getX())
                + "/"
                + yToMap((int) currentViewPort.getY()), 15, 65);
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
