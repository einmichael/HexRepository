/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author faust
 */

//newcomment

public class Map {

    public static Map map;

    public ArrayList tiles = new ArrayList<Tile>();
    public ArrayList nodes = new ArrayList<Node>();
    public ArrayList edges = new ArrayList<Edge>();

    private int[] xPoints;
    private int[] yPoints;

    private int mouseX, mouseY, x, y, scale = new Integer(0);
    private int scale3SQRT = new Integer(0);
    private int scaleHalf = new Integer(0);

    public Map(int x, int y, int scale) {
        map = this;
        //System.out.println("Konstruktor");
        this.x = new Integer(x);
        this.y = new Integer(y);
        this.scale = scale;
        makePoints(scale);
        makeTiles();
        makePolygonsForTiles();
        hookUpTiles();
    }

    public void updateMouse(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        Tile t;
        Iterator<Tile> tileIteratorClear = tiles.iterator();
        while (tileIteratorClear.hasNext()) {
            tileIteratorClear.next().color = Color.blue;
        }
        Iterator<Tile> tileIterator = tiles.iterator();
        while (tileIterator.hasNext()) {
            t = tileIterator.next();
            if (t.polygon.contains(mouseX, mouseY)) {
                t.color = Color.RED;
                t.colorNeighbors();
                break;
            } else {
                t.color = Color.blue;
            }

        }
    }

    public void refresh(int scale) {
        makePoints(scale);
        makePolygonsForTiles();
    }

    private void hookUpTiles() {
        Tile t;
        Iterator<Tile> tileIterator = tiles.iterator();
        while (tileIterator.hasNext()) {
            t = tileIterator.next();
            t.hookUp();
        }
    }

    private void makePolygonsForTiles() {
        Iterator<Tile> tileIterator = tiles.iterator();
        while (tileIterator.hasNext()) {
            tileIterator.next().makePolygon();
        }
    }

    private void makeTiles() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tiles.add(new Tile(i, j));
            }
        }

    }

    private void makePoints(int scale) {
        this.scale = scale;
        this.scale3SQRT = (int) (Math.sqrt(3) * ((double) scale / 2));
        this.scaleHalf = scale / 2;
        //x
        xPoints = new int[3 * x + 2];
        yPoints = new int[2 * y + 2];
        for (int i = 0; i < (3 * x + 2); i++) {
            if (i == 0) {
                xPoints[i] = new Integer(0);
            } else //gerade
            {
                xPoints[i] = new Integer(xPoints[i - 1] + this.scaleHalf);
            }
        }
        //y
        for (int i = 0; i < (2 * y + 2); i++) {
            if (i == 0) {
                yPoints[i] = new Integer(0);
            } else {
                yPoints[i] = new Integer(yPoints[i - 1] + scale3SQRT);
            }

        }
    }

    public Polygon makePolygon(Tile tile) {
        return makePolygon(tile.getX(), tile.getY());
    }

    public Polygon makePolygon(int x, int y) {
        Polygon p = new Polygon();
        if (x % 2 == 0) {
            p.addPoint(xPoints[3 * x + 1], yPoints[2 * y]);
            p.addPoint(xPoints[3 * x + 3], yPoints[2 * y]);
            p.addPoint(xPoints[3 * x + 4], yPoints[2 * y + 1]);
            p.addPoint(xPoints[3 * x + 3], yPoints[2 * y + 2]);
            p.addPoint(xPoints[3 * x + 1], yPoints[2 * y + 2]);
            p.addPoint(xPoints[3 * x], yPoints[2 * y + 1]);
        } else {
            p.addPoint(xPoints[3 * x + 1], yPoints[2 * y + 1]);
            p.addPoint(xPoints[3 * x + 3], yPoints[2 * y + 1]);
            p.addPoint(xPoints[3 * x + 4], yPoints[2 * y + 2]);
            p.addPoint(xPoints[3 * x + 3], yPoints[2 * y + 3]);
            p.addPoint(xPoints[3 * x + 1], yPoints[2 * y + 3]);
            p.addPoint(xPoints[3 * x], yPoints[2 * y + 2]);
        }
        return p;
    }

    public void drawPoints(Graphics g) {
        g.setColor(Color.red);
        for (int i = 0; i < (3 * x + 2); i++) {
            for (int j = 0; j < (2 * y + 2); j++) {
                g.drawRect(xPoints[i], yPoints[j], 1, 1);
            }
        }

    }

    public void drawPolygons(Graphics g) {
        Iterator<Tile> tileIterator = tiles.iterator();
        while (tileIterator.hasNext()) {
            tileIterator.next().drawElement(g);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile(int x, int y) {
        Tile t;
        if (x >= 0 && x <= this.x && y >= 0 && y <= this.y) {
            Iterator<Tile> tileIterator = tiles.iterator();
            while (tileIterator.hasNext()) {
                t = tileIterator.next();
                if (t.getX() == x && t.getY() == y) {
                    return t;
                }
            }
            return null;
        } else {
            return null;
        }
    }

}
