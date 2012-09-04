/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.communication;

import java.io.InputStream;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.entrocorp.pyphygui.main.PyPhyGUI.DEBUG;

/**
 * Insert class description here
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class Communicator extends Thread {
    
    /**
     * String used to delimit messages sent and received to/from PyPhy
     */
    private static final String DELIMITER = "|nln|";
    private static final String DELIMITER_REGEX = "\\|nln\\|";
    
    private boolean pythonRunning;
    
    Socket pyPhySocket;
    Scanner input;
    InputStream inputStream;
    PrintWriter output;
    Queue<String> messages;
    
    
    public Communicator() {
        super("PyPhyGUI Communicator");
        
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
        // Currently only setup for macs
        String command = "python /Users/" + System.getProperty("user.name") + "/Desktop/PyPhy/src/__main__.py";
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
        pythonRunning = true;
        
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
            inputStream = pyPhySocket.getInputStream();
            input = new Scanner( new InputStreamReader( inputStream ) );
            input.useDelimiter(DELIMITER_REGEX);
        } catch (IOException ex) {
            System.err.println("Failed to create input stream from pyPhySocket."
                    + "Either an I/O error occurred when creating the input stream,"
                    + "the socket is closed, the socket is not connected, "
                    + "or the socket input has been shutdown.  IOException: " + ex);
            exit();
        }
        try {
            // Messages aren't sent if you don't pass true here (I'm not sure why) -Nick
            output = new PrintWriter( pyPhySocket.getOutputStream(), true );
        } catch (IOException ex) {
            System.err.println("Failed to create output stream from pyPhySocket."
                    + "Either an I/O error occurred when creating the output stream"
                    + " or the socket is not connected.  IOException: " + ex);
            exit();
        }
    }

    public void quitPython() {
        System.out.println("Quitting Python");
        sendMessage("shutdown");
        while (pythonRunning) {
            try {
                // This isn't best practice but it shouldn't be a big deal since
                // this loop isn't run often
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.err.println("Error putting thread to sleep.  InterruptedException: " + ex);
            }
        }
        try {
            pyPhySocket.close(); // Also closes input and output streams
        } catch (IOException ex) {
            System.err.println("Failed to close pyPhySocket.  IOException: " + ex);
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
        String messageToSend;
        while(true) {
            try {
                // Read in all sent messages
                while (inputStream.available() > 0 && input.hasNext()) {
                    // hasNext() is blocking when there isn't any data to read
                    // so we first check whether there is any data available
                    receivedMessage(input.next());
                }
            } catch (IOException ex) {
                System.err.println("Failed while reading data.  IOException: " + ex);
            }
            
            // Send all queued messages to PyPhy
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
