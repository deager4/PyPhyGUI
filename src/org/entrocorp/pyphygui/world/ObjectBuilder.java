/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * Used for building PObjects.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Sep 16, 2012
 */
public class ObjectBuilder {
    
    /**
     * Name of the object being created.
     */
    private String name;
    
    /**
     * Point representing the top, left, front corner of the object being created.
     */
    private Point corner;
    
    /**
     * Dimensions of the bounding box of the object being created.
     */
    private Dimension dimension;
    
    /**
     * Rotation of the object being created.
     */
    private Rotation rotation;
    
    public ObjectBuilder() {
        corner = new Point();
        dimension = new Dimension();
        rotation = new Rotation();
    }
    
    public ObjectBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public ObjectBuilder corner(Point corner) {
        this.corner = corner;
        return this;
    }
    
    public ObjectBuilder x(int x) {
        this.corner.x = x;
        return this;
    }
    
    public ObjectBuilder y(int y) {
        this.corner.y = y;
        return this;
    }
    
    public ObjectBuilder z(int z) {
        this.corner.z = z;
        return this;
    }
    
    public ObjectBuilder dimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }
    
    public ObjectBuilder width(int width) {
        this.dimension.width = width;
        return this;
    }
    
    public ObjectBuilder height(int height) {
        this.dimension.height = height;
        return this;
    }
    
    public ObjectBuilder depth(int depth) {
        this.dimension.depth = depth;
        return this;
    }
    
    public ObjectBuilder rotation(Rotation rotation) {
        this.rotation = rotation;
        return this;
    }
    
    public ObjectBuilder r1(int r1) {
        this.rotation.r1 = r1;
        return this;
    }
    
    public ObjectBuilder r2(int r2) {
        this.rotation.r2 = r2;
        return this;
    }
    
    public ObjectBuilder r3(int r3) {
        this.rotation.r3 = r3;
        return this;
    }
    
    public PObject build() {
        if (name == null) {
            return new PObject(corner, dimension, rotation);
        } else {
            return new PObject(name, corner, dimension, rotation);
        }
    }
}
