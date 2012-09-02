/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

import java.util.ArrayList;
import java.util.List;

/**
 * Insert class description here
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class World {
    // All the object in this world
    List<PObject> objects;
    
    public World() {
        objects = new ArrayList<PObject>();
    }
}
