package chatroom;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatGUI {

	JButton sendButton;
	JButton exitButton;
    JTextArea chatHistory;		
    JTextField messageField;	
    JTextField usernameField;
    
    
    public ChatGUI() {
    	
        JFrame frame = new JFrame("Java Chatroom");
        JPanel mainPanel = new JPanel();
        
        // The central part of the chatroom that renders chat history
        chatHistory = new JTextArea(15, 50);
        chatHistory.setLineWrap(true);
        chatHistory.setWrapStyleWord(true);
        chatHistory.setEditable(false);
        
        JScrollPane scrollableChat = new JScrollPane(chatHistory);
        scrollableChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Field for username
        usernameField = new JTextField(5);
        
        // Field for submitting a new message
        messageField = new JTextField(30);
        
        // Send button
        sendButton = new JButton("Send");
        
        // Exit button
        exitButton = new JButton("Exit");
        
        // Add components to main panel
        mainPanel.add(scrollableChat);
        mainPanel.add(usernameField);
        mainPanel.add(messageField);
        mainPanel.add(sendButton);
        mainPanel.add(exitButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(600, 400);
        frame.setVisible(true);
        
    }
    
    
}

