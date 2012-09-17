/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * An object in 3D space.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class PObject {
    
    /**
     * The next id number to be assigned to the next object.
     */
    private static int nextID = 0;
    
    /**
     * The identification number unique to this object
     */
    public int id;
    
    /**
     * This object's name.  This can be used by the user in lieu of its id to
     * identify the object.
     */
    public String name;
    
    /**
     * Point representing the top, left, front corner of this box
     */
    public Point corner;
    
    /**
     * Dimensions of this box
     */
    public Dimension dimension;
    
    /**
     * Rotation of this box
     */
    public Rotation rotation;
    
    public PObject() {
        this(new Point(), new Dimension(), new Rotation());
    }
    
    public PObject(Point corner, Dimension dimension, Rotation rotation) {
        this("Object" + nextID, corner, dimension, rotation);
    }
    
    public PObject(String name, Point corner, Dimension dimension, Rotation rotation) {
        this.id = nextID;
        this.name = name;
        this.corner = corner.copy();
        this.dimension = dimension.copy();
        this.rotation = rotation.copy();
        
        nextID++;
    }
    
    public PObject copy() {
        return new PObject(corner, dimension, rotation);
    }
    
    public PObject copy(String newName) {
        return new PObject(newName, corner, dimension, rotation);
    }
    
}
