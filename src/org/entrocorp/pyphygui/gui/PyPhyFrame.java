/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import org.entrocorp.pyphygui.main.PyPhyGUI;

/**
 * JFrame within which the application runs.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 21, 2012
 */
public class PyPhyFrame extends JFrame {
    
    public PyPhyFrame() {
        super();
        
        // Basic Setup
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        
        // Setup support for cmd-w/ctrl-w
        JRootPane rootPanel = getRootPane();
        KeyStroke closeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W,
                PyPhyGUI.commandMask);
        rootPanel.getInputMap().put(closeKeyStroke, "closeWindow");
        rootPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(closeKeyStroke, "closeWindow");
        rootPanel.getActionMap().put("closeWindow", 
                new AbstractAction("Close Window") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        close();
                    }
                });
        
        // Listen for the window to close so we can end the python application when it does
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitPython();
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
                quitPython();
            }
            
            private void quitPython() {
                PyPhyGUI.comm.sendMessage("shutdown");
            }
        });
        
        // Setup GUI
        add(new AppPanel(), BorderLayout.CENTER);
        
        // Setup MenuBar
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        validate();
    }
    
    public void close() {
        setVisible(false);
        dispose();
    }
    
}
