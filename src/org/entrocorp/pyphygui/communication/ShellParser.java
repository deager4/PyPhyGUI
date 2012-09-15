/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.communication;

import org.entrocorp.pyphygui.gui.Shell;

/**
 * Parses messages typed into the shell
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Sep 3, 2012
 */
public class ShellParser {
    /**
     * If this string appears at the beginning of a shell command, that command
     * (minus this string) is forwarded to the PyPhy program.
     */
    private static String forwardingString = "pyc/";
    
    private Communicator comm;
    
    public ShellParser(Communicator comm, Shell shell) {
        super();
        
        this.comm = comm;
    }
    
    /**
     * Parses the command, if possible.
     * 
     * @param command The command to parse.
     * @return true if the command was successfully parsed, false otherwise.
     */
    public boolean parse(String command, Shell shell) {
        String sentCommand;
        if (command.substring(0, forwardingString.length()).equals(forwardingString)) {
            sentCommand = command.substring(forwardingString.length());
            comm.sendMessage(sentCommand);
            shell.println("Forwarding command '" + sentCommand + "' to PyPhy");
            return true;
        }
        return false;
    }
}
