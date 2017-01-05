/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author faust
 */
public class Map {
    
    public static Map map;

    public ArrayList tiles = new ArrayList<Tile>();
    public ArrayList nodes = new ArrayList<Node>();
    public ArrayList edges = new ArrayList<Edge>();

    private int[] xPoints;
    private int[] yPoints;

    private int x, y, scale = new Integer(0);
    private int scale3SQRT = new Integer(0);

    public Map(int x, int y, int scale) {
        map=this;
        //System.out.println("Konstruktor");
        this.x = new Integer(x);
        this.y = new Integer(y);
        this.scale = scale;
        makePoints(scale);
        makeTiles();
        hookUpTiles();

    }
    
    private void hookUpTiles(){
        Iterator<Tile> tileIterator = tiles.iterator();
       while (tileIterator.hasNext()) {
            tileIterator.next().hookUp();
        }
    }

    private void makeTiles() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tiles.add(new Tile(i,j));
            }
        }

    }

    public void makePoints(int scale) {
        this.scale = scale;
        this.scale3SQRT = (int) (Math.sqrt(3) * ((double) scale / 2));
        //x
        xPoints = new int[2 * x + 2];
        yPoints = new int[2 * y + 2];
        for (int i = 0; i < (2 * x + 2); i++) {
            if (i == 0) {
                xPoints[i] = new Integer(0);
            } else //gerade
             if (i % 2 == 0) {
                    xPoints[i] = new Integer(xPoints[i - 1] + this.scale);
                } else //ungerade
                {
                    xPoints[i] = new Integer(xPoints[i - 1] + this.scale / 2);
                }
        }

        //xy
        for (int i = 0; i < (2 * x + 2); i++) {
            if (i == 0) {
                yPoints[i] = new Integer(0);
            } else {
                yPoints[i] = new Integer(yPoints[i - 1] + scale3SQRT);
            }

        }
    }

    public void drawPoints(Graphics g) {
        g.setColor(Color.red);
        for (int i = 0; i < (2 * x + 2); i++) {
            for (int j = 0; j < (2 * y + 2); j++) {
                g.drawRect(xPoints[i], yPoints[j], 1, 1);
            }
        }

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

    public Tile getTile(int x, int y){
        return (Tile) tiles.get(y*this.x + x);
    }
    
}
