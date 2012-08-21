/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * Represents a point in 3d space
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 21, 2012
 */
public class Point {
    public int x, y, z;
    
    public Point() {
        this(0, 0, 0);
    }
    
    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point copy() {
        return new Point(x, y, z);
    }
}
