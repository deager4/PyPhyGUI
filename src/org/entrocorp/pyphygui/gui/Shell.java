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
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.entrocorp.pyphygui.communication.ShellParser;
import org.entrocorp.pyphygui.main.PyPhyGUI;

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
    private ShellParser parser;
    private InternalParser internalParser;
    
    public Shell() {
        super();
        
        // Basic Setup
        shellHistory = new History();
        parser = new ShellParser(PyPhyGUI.comm, this);
        internalParser = new InternalParser();
        
        // Basic GUI Setup
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
    
    /* ----------- Printing Methods ------------- */
    
    public void print(String str) {
        textArea.append("  " + str);
    }
    
    public void print(Object obj) {
        print(obj.toString());
    }
    
    public void print(char c) {
        print(Character.toString(c));
    }
    
    public void print(boolean b) {
        print(Boolean.toString(b));
    }
    
    public void print(int n) {
        print(Integer.toString(n));
    }
    
    public void print(long l) {
        print(Long.toString(l));
    }
    
    public void print(float f) {
        print(Float.toString(f));
    }
    
    public void print(double d) {
        print(Double.toString(d));
    }
    
    public void println(String str) {
        print(str);
        print("\n");
    }
    
    public void println(Object obj) {
        print(obj);
        print("\n");
    }
    
    public void println(char c) {
        print(c);
        print("\n");
    }
    
    public void println(boolean b) {
        print(b);
        print("\n");
    }
    
    public void println(int n) {
        print(n);
        print("\n");
    }
    
    public void println(long l) {
        print(l);
        print("\n");
    }
    
    public void println(float f) {
        print(f);
        print("\n");
    }
    
    public void println(double d) {
        print(d);
        print("\n");
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
                // Update TextFields
                textArea.append(">>> " + inputField.getText() + "\n");
                String command = inputField.getText();
                inputField.setText("");
                
                // Parse the command
                boolean didParseCommand = internalParser.parse(command);
                if (!didParseCommand) {
                    didParseCommand = parser.parse(command);
                }
                if (!didParseCommand) {
                    println("Command not recognized.  Type 'help' for a list of commands.");
                }
                
                // Update shellHistory
                shellHistory.push(command);
                shellHistory.resetPosition();
                
                break;
            case KeyEvent.VK_UP:
                if (shellHistory.isShowingCurrentCommand()) {
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
    
    /* ------------- Helper Classes -------------- */
    
    /**
     * Stores the shell's history
     */
    private class History implements Iterable<String> {
        private List<String> commands;
        String currentCommand; // temporary storage
        int index; // index of the currently being displayed command
        
        History() {
            super();
            
            index = 0;
            commands = new ArrayList<String>();
        }
        
        int size() {
            return commands.size();
        }
        
        String get(int i) {
            return commands.get(i);
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

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < commands.size();
                }

                @Override
                public String next() {
                    String returnVal = commands.get(i);
                    i++;
                    return returnVal;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Removal not supported by this Iterator");
                }
                
            };
        }
    }
    
    /**
     * Used to parse internal shell commands (ex. history, help, etc.)
     */
    private class InternalParser {
        
        /**
         * Parses the command, if possible.
         * 
         * @param command The command to parse.
         * @return true if the command was recognized as an internal shell command
         *         and successfully parsed; false otherwise.
         */
        boolean parse(String command) {
            command = command.trim();
            boolean returnVal = false;
            
            if (command.equals("history")) {
                // Print out the shell history
                for (int i = 0; i < shellHistory.size(); i++) {
                    println((i + 1) + " " + shellHistory.get(i));
                }
                returnVal = true;
            } else if (command.equals("help")) {
                // Display a list of commands
                println("help - display this help message");
                println("history- display a history of commands");
                returnVal = true;
            }
            
            return returnVal;
        }
        
    }
    
}
