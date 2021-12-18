package chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *	Runnable to handle clients
 *
 */
public class ClientThreadHandler implements Runnable{
    BufferedReader in;
    PrintWriter out;
    Socket clientSocket;
    
    /**
     * 
     * @param socket
     */
    public ClientThreadHandler(Socket socket, PrintWriter writer){
        try{
            clientSocket = socket;
            out = writer;
            in = new BufferedReader(
            		new InputStreamReader(clientSocket.getInputStream()));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     *  Sends the message that was received from a client to every client 
     */
    public void run(){
        String message;
        try{
            while((message = in.readLine()) != null){
            	if (message.equals("EXIT")) {
            		break;
            	} else {
            		System.out.println("read " + message);
            		ChatServer.tellEveryone(message);
            	}
            }
            // if a client exited the chatroom;
            System.out.println(out.toString() + " has been deleted");
            ChatServer.removeClientOutputStream(out);
            clientSocket.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
