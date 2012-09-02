/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
        JPanel topPanel = new JPanel();
        
        
        // Setup Shell
        shell = new Shell();
        
        // Setup Splitpane
        splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitpane.setDividerLocation(400);
        add(splitpane, BorderLayout.CENTER);
        splitpane.setTopComponent(topPanel);
        splitpane.setBottomComponent(shell);
    }
    
}
