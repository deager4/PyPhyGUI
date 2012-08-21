/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * Insert class description here
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class PObject {
    
    /**
     * Point representing the top, left, front corner of this box
     */
    public Point corner;
    
    /**
     * Dimensions of this box
     */
    public Dimension dimension;
    
    // TODO: rotation
    
    public PObject() {
        this(new Point(), new Dimension());
    }
    
    public PObject(Point corner) {
        this(corner, new Dimension());
    }
    
    public PObject(Dimension dimension) {
        this(new Point(), dimension);
    }
    
    public PObject(Point corner, Dimension dimension) {
        this.corner = corner.copy();
        this.dimension = dimension.copy();
    }
    
    public PObject copy() {
        return new PObject(corner, dimension);
    }
    
}
