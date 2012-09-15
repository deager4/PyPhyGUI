/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.entrocorp.pyphygui.main.PyPhyGUI;

/**
 * Insert class description here
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 29, 2012
 */
public class AppPanel extends JPanel {
    
    JSplitPane splitpane;
    Shell shell;
    
    public AppPanel() {
        super();
        setLayout(new BorderLayout());
        
        // Setup display
        SimulationPanel simPanel = new SimulationPanel(PyPhyGUI.getWorld());
        
        // Setup Shell
        shell = new Shell();
        
        // Setup Splitpane
        splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitpane.setDividerLocation(400);
        add(splitpane, BorderLayout.CENTER);
        splitpane.setBackground(Color.WHITE);
        splitpane.setTopComponent(simPanel);
        splitpane.setBottomComponent(shell);
    }
    
}
