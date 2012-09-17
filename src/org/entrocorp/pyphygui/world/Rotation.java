/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

/**
 * Represents the rotation of an object in 3D space.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Sep 16, 2012
 */
public class Rotation {
    
    public int r1, r2, r3;
    
    public Rotation() {
        this(90, 90, 90); // This should be whatever un-rotated is
    }

    public Rotation(int r1, int r2, int r3) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
    }
    
    public Rotation copy() {
        return new Rotation(r1, r2, r3);
    }
    
}
