/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Shell for entering commands into the program
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 29, 2012
 */
public class Shell extends JPanel implements KeyListener {
    
    private JTextArea textArea;
    private JTextField inputField;
    private History shellHistory;
    
    public Shell() {
        super();
        
        // Basic Setup
        shellHistory = new History();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // TextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(textArea, BorderLayout.CENTER);
        
        // TextField
        inputField = new JTextField();
        inputField.addKeyListener(this);
        inputField.setFocusable(true);
        inputField.requestFocus();
        add(inputField, BorderLayout.SOUTH);
    }
    
    /* ------------ KeyListener for JTextArea -------------- */
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        String newText;
        switch (key) {
            case KeyEvent.VK_ENTER:
                textArea.append(">>> " + inputField.getText() + "\n");
                shellHistory.push(inputField.getText());
                inputField.setText("");
                shellHistory.resetPosition();
                break;
            case KeyEvent.VK_UP:
                if (shellHistory.isShowingCurrentCommand()) {
                    System.out.println("Setting current command");
                    shellHistory.setCurrentCommand(inputField.getText());
                }
                newText = shellHistory.getPrev();
                if (newText != null) {
                    inputField.setText(newText);
                }
                break;
            case KeyEvent.VK_DOWN:
                newText = shellHistory.getNext();
                if (newText != null) {
                    inputField.setText(newText);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                inputField.setCaretPosition(inputField.getText().length());
                break;
        }
    }
    
    private class History {
        private List<String> commands;
        String currentCommand; // temporary storage
        int index; // index of the currently being displayed command
        
        History() {
            super();
            
            index = 0;
            commands = new ArrayList<String>();
        }
        
        void push(String command) {
            commands.add(command); // adds to the end
            index++;
        }
        
        void setCurrentCommand(String currentCommand) {
            this.currentCommand = currentCommand;
        }
        
        boolean isShowingCurrentCommand() {
            return index == commands.size();
        }
        
        String getPrev() {
            if (index > 0) {
                index--;
                return commands.get(index);
            } else {
                return null;
            }
        }
        
        String getNext() {
            if (index < commands.size()) {
                index++;
                if (index == commands.size()) {
                    return currentCommand;
                } else {
                    return commands.get(index);
                }
            } else {
                return null;
            }
        }
        
        void resetPosition() {
            index = commands.size();
        }
        
        void clear() {
            commands = new ArrayList<String>();
            index = 0;
        }
    }
    
}
