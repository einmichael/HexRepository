/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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

    public final static int CAMERA_SPEED = 3;
    public int cameraShiftX = new Integer(0);
    public int cameraShiftY = new Integer(0);

    //ViewPort AT
    public AffineTransform VPAT;
    //tmp AT
    private AffineTransform tmpAT;

    public Point2D oldViewPort;
    public Point2D currentViewPort;
    public Point2D currentViewPortOnMap;
    public Point2D tmpPoint;

    public Point2D src;
    public Point2D dst;

    public MainPanel mainPanel;

    //Konstruktor
    public Obs(MainPanel main) {
        this.mainPanel = main;
        ScrollBroadcaster.getInstance().addScrollListener(this);

        currentViewPort = (Point2D) new Point(0, 0);
        currentViewPortOnMap = (Point2D) new Point(0, 0);
        oldViewPort = (Point2D) new Point(0, 0);

        VPAT = new AffineTransform();
        mapAT = new AffineTransform();
        mapATTrans = new AffineTransform();
       
    }

    @Override
    public void scrollUpdate(ScrollEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                adjustScale(e.getA());
                debugShowATs();
            }
        });
    }

    //Refresher
    /**
     * baut MapATs nach Änderung von scale oder translates
     */
    public void refreshMapAT() {
        mapAT = AffineTransform.getScaleInstance((double) scale / 10, (double) scale / 10);
        mapAT.concatenate(mapATTrans);
        mainPanel.repaint();
    }

    //funktionierende Transformer
    /**
     * from x/y to Map pointToBeChanged must reference an Point Object.
     *
     * @param pointOriginOnFrame
     * @param pointToBeChangedOnMap
     */
    public void shiftPointToMap(Point2D pointOriginOnFrame, Point2D pointToBeChangedOnMap) throws Exception {
        Exception myException = new Exception("Exception Point2D not valid Obs.shiftPointToMap");

        if (pointOriginOnFrame == null) {
            throw myException;
        }
        if (pointToBeChangedOnMap == null) {
            throw myException;
        }

        mapAT.inverseTransform(pointOriginOnFrame, pointToBeChangedOnMap);

        //gleichbedeutend mit oben, wegen "throws...
        /*
        try {
            mapAT.inverseTransform(pointOriginOnFrame, pointToBeChangedOnMap);
        } catch (Exception e) {
            throw e;
        }*/
    }

    /**
     * From Map to x/y on Frame/Panel...
     *
     * @param pointOriginOnMap
     * @param pointToBeChangedOnFrame
     * @throws Exception
     */
    public void shiftPointFromMap(Point2D pointOriginOnMap, Point2D pointToBeChangedOnFrame) throws Exception {
        Exception myException = new Exception("Exception Point2D not valid Obs.shiftPointFromMap");

        if (pointOriginOnMap == null) {
            throw myException;
        }
        if (pointToBeChangedOnFrame == null) {
            throw myException;
        }

        mapAT.transform(pointOriginOnMap, pointToBeChangedOnFrame);
    }

    //ViewPort
    public void refreshViewPortOnMap() {
         try {
            shiftPointToMap(currentViewPort, currentViewPortOnMap);
        } catch (Exception e) {
            l("" + e);
        }
        mainPanel.refreshMouse();
 
    }

    public void setOldViewPort() {
        tmpPoint = (Point2D) new Point(0, 0);
        //camToMap(currentViewPort, tmpPoint);
        oldViewPort.setLocation(tmpPoint);
        System.out.println("OldViewPort set to: " + oldViewPort);
    }

    /**
     * wird nur von resize Listener aufgerufen
     *
     * @param x
     * @param y
     */
    public void setViewPortLocation(int x, int y) {
        System.out.println("SetVPLocation with x/y: " + x + "/" + y);

        currentViewPort.setLocation((double) x, (double) y);
        VPAT = AffineTransform.getTranslateInstance(
                currentViewPort.getX(),
                currentViewPort.getY());

        try {
            shiftPointToMap(currentViewPort, currentViewPortOnMap);
        } catch (Exception e) {
            l("" + e);
        }

        mainPanel.repaint();
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
        refreshViewPortOnMap();
        mainPanel.repaint();
    }

    /**
     * takes p and transforms to Map-x/y into pointOnMap
     *
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
    
    
    //MapScrolling
    
    
    public void scrollMap(int ShiftX, int ShiftY) {
        //darf noch gescrollt werden?
        
        //scroll
        mapATTrans.concatenate(AffineTransform.getTranslateInstance(ShiftX, ShiftY));
        refreshMapAT();
        //setViewPort
        
        //refresh
        refreshViewPortOnMap();
        
        //reset cameraxSHift
        cameraShiftX=0;
        cameraShiftY=0;

    }
    //debug
    private Point2D pointOnMap;
    private Point2D point;

    /**
     * takes p and transforms to Map-x/y into pointOnMap
     *
     * @param p
     */
    /*public void shiftPointToMap(Point2D p) {
        pointOnMap = (Point2D) new Point(0, 0);
        point = (Point2D) p.clone();
        try {
            mapAT.inverseTransform(p, pointOnMap);
        } catch (Exception e) {

        }
    }*/

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

    private void l(String s) {
        System.out.println("" + s);
    }

    public void debugShowATs() {
        System.out.println("Map: " + mapAT);
        System.out.println("VP : " + VPAT);
    }

    public void pointTest() {
        int x = (int) currentViewPort.getX();
        int y = (int) currentViewPort.getY();
        l(" ");

        l("x:" + x);
        l("y:" + y);
        l("x in Map man. : " + xToMap(x));
        l("y in Map man. : " + yToMap(y));
        point = (Point2D) currentViewPort.clone();
        l("Point (origin)" + point);
        l("x/y in Map via AT:" + pointOnMap.getX() + "/" + pointOnMap.getY());

        l("testing");
//debug throwable
        Point2D pTest = (Point2D) new Point(1000, 1000);
        l("vorher: " + pTest);
        try {
            shiftPointToMap((Point2D) pTest.clone(), pTest);
            l("x/y in Map via AT:" + pTest.getX() + "/" + pTest.getY());
        } catch (Exception e) {
            l("" + e);

        }
        l("dann: " + pTest);
        try {
            shiftPointFromMap((Point2D) pTest.clone(), pTest);
            l("x/y in Map via AT:" + pTest.getX() + "/" + pTest.getY());
        } catch (Exception e) {
            l("" + e);

        }
        l("zurück: " + pTest);

    }

    public void render(Graphics g) {
        //ohne verschiebung. aufruf mit transform auf g! funktioniert!
        Graphics2D g2D;
        g2D = (Graphics2D) g;
        tmpAT = g2D.getTransform();
        g2D.transform(VPAT);
        g2D.setColor(Color.pink);
        g2D.fillRect(
                - 1,
                - 1,
                3, 3);
        g2D.setTransform(tmpAT);

        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Viewport xMap/yMap: "
                + currentViewPortOnMap.getX()
                + "/"
                + currentViewPortOnMap.getY(), 15, 65);

        /*
        //funtioniert
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
         */
    }

    public void move(String s, Object o) {
        switch (s) {
            case "up":
                cameraShiftY = -CAMERA_SPEED;
                break;
            case "down":
                cameraShiftY = CAMERA_SPEED;
                break;
            case "left":
                cameraShiftX = -CAMERA_SPEED;
                break;
            case "right":
                cameraShiftX = +CAMERA_SPEED;
            default:

        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollMap(cameraShiftX, cameraShiftY);
                StatusPanel.sp.refresh();
            }
        });

    }
}
