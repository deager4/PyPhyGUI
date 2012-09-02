/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Shell for entering commands into the program
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 29, 2012
 */
public class Shell extends JScrollPane implements KeyListener {
    
    JTextArea shell;
    
    public Shell() {
        super();
        
        shell = new JTextArea();
        shell.setEditable(false);
        shell.setLineWrap(true);
        shell.addKeyListener(this);
        setViewportView(shell);
        shell.requestFocus();
    }

    /* ------------ KeyListener for JTextArea -------------- */
    
    @Override
    public void keyTyped(KeyEvent e) {
        String typedChar = Character.toString(e.getKeyChar());
        int caretPosition = shell.getCaretPosition();
        String text = shell.getText();
        shell.setText(text.substring(0, caretPosition) + typedChar + text.substring(caretPosition));
        shell.setCaretPosition(caretPosition + typedChar.length());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        int key = e.getKeyCode();
        int newPosition;
        switch (key) {
            case KeyEvent.VK_BACK_SPACE:
                System.out.println("Backspace Pressed");
                int caretPosition = shell.getCaretPosition();
                if (caretPosition <= 0) {
                    break;
                }
                String text = shell.getText();
                shell.setText(text.substring(0,caretPosition - 1) + text.substring(caretPosition));
                shell.setCaretPosition(caretPosition - 1);
                break;
            case KeyEvent.VK_UP:
                // TODO: implement history
                break;
            case KeyEvent.VK_DOWN:
                // TODO: implement history
                break;
                
                
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    
    
}
