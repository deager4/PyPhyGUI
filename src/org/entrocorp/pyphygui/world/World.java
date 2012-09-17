/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.world;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;

/**
 * Represents a simulation world.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class World {
    // All the objects in this world
    private List<PObject> objects;
    
    public World() {
        objects = new ArrayList<PObject>();
    }
    
    /* --------- WorldListener ---------- */
    
    private EventListenerList listenerList = new EventListenerList();
    
    public void addWorldListener(WorldListener listener) {
        listenerList.add(WorldListener.class, listener);
    }
    
    public void removeWorldListener(WorldListener listener) {
        listenerList.remove(WorldListener.class, listener);
    }
   
}
