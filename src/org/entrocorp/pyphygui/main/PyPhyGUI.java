/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.main;

import java.io.IOException;
import org.entrocorp.pyphygui.communication.Communicator;
import org.entrocorp.pyphygui.world.World;

/**
 * Starts the program.  Also used as a hub for communication between the GUI
 * and the communication classes.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class PyPhyGUI {
    
    // Used to communicate with the python section
    public static final Communicator comm;
    
    // Models the current world containing only the information necessary to render it
    private static World world;
    
    static {
        comm = new Communicator();
        world = new World();
    }
    
    /**
     * Returns the current world being modeled.
     * 
     * @return The current world.
     */
    public static World getWorld() {
        return world;
    }
    
    
    /**
     * Creates a new world, sets the currently being rendered world to this new
     * world, and returns it.
     * 
     * @return The world that was just created, now the result of calling getWorld().
     */
    public static World newWorld() {
        world = new World();
        return world;
    }
    
    /**
     * Loads the world specified by path, sets the currently being rendered world
     * to the loaded world, and returns it.
     * 
     * @param path The path to the world to load.
     * @return Thw world that was just loaded, now the results of calling getWorld().
     */
    public static World loadWorld(String path) {
        // TODO: implement this
        return new World(); // placeholder
    }
    
    /**
     * Starts up the python section of the program and sets up the GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        String runPyPhyCommand;
        if (os.indexOf("win") > 0) {
            // Windows
            runPyPhyCommand = "";
        } else {
            // Unix based
            runPyPhyCommand = "";
        }
        try {
            Runtime.getRuntime().exec(runPyPhyCommand);
        } catch (IOException ex) {
            System.err.println("Failed to run pyPhy using command " + runPyPhyCommand + ".  IOException: " + ex);
        }
        
        //TODO: setup GUI   
    }
}
