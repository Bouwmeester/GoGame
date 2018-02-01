package com.nedap.go.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.nedap.go.gui.GoGUIIntegrator;

import general.Protocol;

public class Client {
	private static final String USAGE =
			"Usage: java Client " + Client.class.getName() + " name port";
	
	private static String name;
	
	//private static GoGUIIntegrator gogui;

	/**
	 * Start a Client application.
	 */
	
	// client GUI
 
	public static void main(String[] args) {
		// start a GUI is currently empty. 
//		gogui = new GoGUIIntegrator(true, true, 13);
//		gogui.startGUI();
		
		if (args.length != 1) { 			
			System.out.println(USAGE);
			System.exit(0);
		}
		
		System.out.println("Please enter your name");
		BufferedReader nameInput = new BufferedReader(new InputStreamReader(System.in));
		String playerName = " ";
		try {
			playerName = nameInput.readLine();
			name = playerName;
			System.out.println(playerName);

			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Port to connect to 
		int port = 0;
		
		/**
		 * Check the input arguments (what are the input arguments).
		 * parsInt = Input is String output is decimal integer
		 * Exception thrown if input is not an integer
		 */

		try {
			port = Integer.parseInt(args[0]);		 
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			System.out.println("Error: port" + args[0] + "is not an integer");
			System.exit(0);
		}
		
		/**
		 * Try to open a socket connection between client and server.
		 */
		
		Socket csock = null;
		ClientConnection clientConnection = null;

		try {
			csock = new Socket("NVC3497", port);			//change "127.0.0.1" //"10.7.16.121" //"
			System.out.println("Connected to server " + playerName);  
			// new thread to handle server output without client input.			
			clientConnection = new ClientConnection(csock, name);
			clientConnection.start();
		} catch (IOException e) {
			System.out.println("Error: could not create a socket on port" + port);
		}

		// If 2 players Request a new game is started.
		System.out.println("Please enter REQUESTGAME$2$RANDOM"); 

		
		try {
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			
			// first message from server to client. 
			clientConnection.messageToServer(Protocol.Client.NAME + Protocol.General.DELIMITER1 
					+ playerName + Protocol.General.DELIMITER1 + Protocol.Client.VERSION + 
					Protocol.General.DELIMITER1 + Protocol.Client.VERSIONNO + 
					Protocol.General.DELIMITER1 + Protocol.Client.EXTENSIONS +
					Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 +
					0 + Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 + 0 +
					Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 + 0 +
					Protocol.General.DELIMITER1 + 0 ); //Protocol.General.COMMAND_END);
			
			
			String commandInput;
			while ((commandInput = userInput.readLine()) != null) {
				clientConnection.messageToServer(commandInput);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
