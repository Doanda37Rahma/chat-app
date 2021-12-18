package chatroom;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer{
	
	// Array of every clients' output streams
	static ArrayList<PrintWriter> clientOutputStreams;
    
    /**
     * Sends message to every client
     * 
     * @param message The message
     */
    public static void tellEveryone(String message){
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()){
            try{
                PrintWriter out = (PrintWriter) it.next();
                out.println(message);
                out.flush();
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Removes an output stream from array so that clients that are 
     * down / exited are no longer sent responses
     * @param out The socket output stream
     */
    public static void removeClientOutputStream(PrintWriter out) {
    	try {
        	clientOutputStreams.remove(out);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	if (clientOutputStreams.size() == 0) {
    		System.out.println("STREAMS ARE EMPTY");
    		
    	}
    }
    
    /**
     * Adds an output stream to array so that clients that joined can be sent responses
     * @param out The socket output stream
     */
    public static void addClientOutputStream(PrintWriter out) {
    	try {
        	clientOutputStreams.add(out);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    public static void main(String[] args) throws IOException {
    	clientOutputStreams = new ArrayList<PrintWriter>();
    	
    	int portNumber = 5500;
        
    	try{
            ServerSocket serverSocket = new ServerSocket(portNumber);

            boolean listening = true;

            while(listening){
	            new ChatServerThread(serverSocket.accept()).start();
                System.out.println("Got a connection");
            }
            
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            
            System.exit(-1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
