/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import static org.entrocorp.pyphygui.main.PyPhyGUI.DEBUG;

/**
 * Insert class description here
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class Communicator extends Thread {
    
    /**
     * String used to delimit messages sent to PyPhy
     */
    private static final String DELIMITER = "\n";
    
    Socket pyPhySocket;
    BufferedReader input;
    PrintWriter output;
    Queue<String> messages;
    
    
    public Communicator() {
        super();
        
        // Setup server
        System.out.println("Setting up server");
        ServerSocket server = null;
        try {
            server = new ServerSocket(5275);
        } catch (IOException ex) {
            System.err.println("Failed to setup ServerSocket. IOException: " + ex);
            exit();
        }
        
        // Run PyPhy
        System.out.println("Attempting to run PyPhy (python section)");
        // Currently only setup for Carl's computer
        String command = "python /Users/karlnotturno/Desktop/PyPhy/src/__main__.py";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            System.err.println("Failed to run PyPhy using command: " + command + " IOException: " + ex);
            exit();
        }
        
        // Listen for a connection from PyPhy
        System.out.println("Listening for a connection from PyPhy");
        try {
            pyPhySocket = server.accept();
        } catch (IOException ex) {
            System.err.println(ex);
            exit();
        }
        
        // Succesfully connected to PyPhy
        System.out.println("Successfully connected to PyPhy");
        System.out.println("Closing server");
        try {
            server.close();
        } catch (IOException ex) {
            System.err.println("Failed to close server.  IOException: " + ex);
        }
        
        // Do initialization
        messages = new ArrayDeque();
        try {
            input = new BufferedReader( new InputStreamReader( pyPhySocket.getInputStream() ) );
        } catch (IOException ex) {
            System.err.println("Failed to create input stream from pyPhySocket."
                    + "Either an I/O error occurred when creating the input stream,"
                    + "the socket is closed, the socket is not connected, "
                    + "or the socket input has been shutdown.  IOException: " + ex);
            exit();
        }
        try {
            output = new PrintWriter( pyPhySocket.getOutputStream() );
        } catch (IOException ex) {
            System.err.println("Failed to create output stream from pyPhySocket."
                    + "Either an I/O error occurred when creating the output stream"
                    + " or the socket is not connected.  IOException: " + ex);
            exit();
        }
    }

    private void exit() {
        System.err.println("Exiting Program");
        System.exit(1);
    }
    
    public void sendMessage(String message) {
        if (DEBUG) {
            System.out.println("Adding message to queue: " + message);
        }
        messages.add(message);
    }
    
    public void receivedMessage(String message) {
        System.out.println("Received Message: " + message);
    }
    
    /**
     * Loop infinitely, sending and receiving messages as necessary
     */
    @Override
    public void run() {
        System.out.println("Running Communicator Thread");
        String receivedMessage;
        String messageToSend;
        while(true) {
            try {
                // Read in all sent messages
                // Messages must be delimited by a newline
                receivedMessage = input.readLine();
                while (receivedMessage != null) {
                    receivedMessage(receivedMessage);
                    receivedMessage = input.readLine();
                }
            } catch (IOException ex) {
                System.err.println("Failure receiving message from PyPhy.");
                System.err.println("IOException: " + ex);
            }
            
            
            // Send all queued messages to PyPhy (delimited by newlines)
            messageToSend = messages.poll(); // Returns null if queue is empty
            while (messageToSend != null) {
                if (DEBUG) {
                    System.out.println("Sending message: " + messageToSend);
                }
                output.print(messageToSend + DELIMITER);
                messageToSend = messages.poll();
            }
            output.flush();
        }
    }
    
}
