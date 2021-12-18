package chatroom;

import java.io.*;
import java.net.*;
import java.awt.Frame;
import java.awt.event.*;

public class ChatClient{
	
    BufferedReader in;	
    PrintWriter out;		
    Socket chatSocket;	
    ChatGUI gui;
    
    public static void main(String[] args) throws IOException{
        ChatClient client = new ChatClient();
        client.go();
    }
    
    public void go() throws IOException{
        // Set up socket network with the server
        setUpNetwork();
        
        // Set up the thread
        Thread readerThread = new Thread(new ChatHistoryReader());
        readerThread.start();
        
        // Set up the GUI
        gui = new ChatGUI();
        gui.sendButton.addActionListener(new SendButtonListener());  
        gui.exitButton.addActionListener(new ExitButtonListener());
        
        out.println("someone has entered the chat!");
        out.flush();
    }
    
    /**
     * Sets up the socket network
     */
    private void setUpNetwork() throws IOException{

    	String hostName = "localhost";
    	int portNumber = 5500;

    	try{

        	chatSocket = new Socket(hostName, portNumber);
            
            InputStreamReader streamReader = new InputStreamReader(chatSocket.getInputStream());
            in = new BufferedReader(streamReader);
            in = new BufferedReader(
                    new InputStreamReader(chatSocket.getInputStream()));            
            out = new PrintWriter(chatSocket.getOutputStream());
            System.out.println("Network established");
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
    
    /**
     * Class for send button interaction
     *
     */
    public class SendButtonListener implements ActionListener{
    	
    	/**
    	 * Sends a string "username : message" to the server and empty the textfield.
    	 */
    	public void actionPerformed(ActionEvent event){
            try{
            	String username;
            	// if username is empty, replace with "anonymous"
            	if (gui.usernameField.getText().equals(""))
            		username = "anonymous";
            	else
            		username = gui.usernameField.getText();

            	out.println(username + " : " + gui.messageField.getText());
                out.flush();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            gui.messageField.setText("");
            gui.messageField.requestFocus();
        }
    }
    
    /**
     * Class for exit button interaction
     */
    public class ExitButtonListener implements ActionListener{
    	
    	/**
    	 * Sends a string "username has left the chat"
    	 * then issues a command to terminate the client socket
    	 */
    	public void actionPerformed(ActionEvent event) {
    		try {
            	String username;
            	// if username is empty, replace with "anonymous"
            	if (gui.usernameField.getText().equals(""))
            		username = "anonymous";
            	else
            		username = gui.usernameField.getText();
            	
            	out.println(username + " has left the chat!");
            	out.flush();
    			out.println("EXIT");
    			out.flush();
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
        	System.exit(0); 
    	}
    }
    
    /**
     * Runnable Reads message received from server and adds to client's chat history
     */
    public class ChatHistoryReader implements Runnable {
        public void run(){
            String message;
            try{
                while((message = in.readLine()) != null){
                    System.out.println("read " + message);
                    
                    if (message == "EXIT")
                    	break;
                	else 
                		// adds message received from server to client's chat history
                		gui.chatHistory.append(message + "\n");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
