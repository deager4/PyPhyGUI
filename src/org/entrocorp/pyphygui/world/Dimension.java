/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * Represents the Dimensions of a 3D Bounding Box
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 21, 2012
 */
public class Dimension {
    /**
     * Length along the x-axis
     */
    public int width;
    
    /**
     * Length along the y-axis
     */
    public int height;
    
    /**
     * Length along the z-axis
     */
    public int depth;
    
    public Dimension() {
        this(1, 1, 1); // Might want to change this later
    }
    
    public Dimension(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    
    public Dimension copy() {
        return new Dimension(width, height, depth);
    }
    
}
