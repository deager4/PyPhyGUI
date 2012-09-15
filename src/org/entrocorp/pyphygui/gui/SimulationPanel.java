/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.entrocorp.pyphygui.world.World;
import org.entrocorp.pyphygui.world.WorldListener;

/**
 * Panel displaying the actual simulation.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Sep 14, 2012
 */
public class SimulationPanel extends JPanel implements WorldListener {
    
    private World world;
    
    public SimulationPanel(World world) {
        super();
        
        this.world = world;
        world.addWorldListener(this);
        
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2); // X-Axis
        g2.drawLine(getWidth()/2, 0, getWidth()/2, getHeight()); // Y-Axis
        
        // Save the current graphics transform
        AffineTransform originalTransform = g2.getTransform();
        
        // Makes it so this Graphics object's origin is at the center of the
        // panel
        g2.translate(getWidth()/2, getHeight()/2);
        g2.setColor(Color.BLACK);
        
        // Restore the original transform
        g2.setTransform(originalTransform);
    }
    
    /* ------------- WorldListener ----------- */
    
    
    
}
