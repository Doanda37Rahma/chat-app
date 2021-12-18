package chatroom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatServerThread extends Thread {
    
	Socket socket;

    public ChatServerThread(Socket socket) {
        super("ChatServerThread");
        this.socket = socket;
    }

    
    /**
     *  Sends the message that was received from a client to every client 
     */
    public void run(){
        String message;
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ){
        	ChatServer.addClientOutputStream(out);

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
            socket.close();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}