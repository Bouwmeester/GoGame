package com.nedap.go.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import general.Protocol;

public class Server { 				
	private static final String USAGE = 
			"Usage: java  " + Server.class.getName() + " <port>"; 

	private static String serverName = "Adrian";
	//Which is the most popular name for surf boys 
	protected BufferedReader serverIN;
	
	/**
	 * Start a server application.
	 * Port number is given as argument in run configurations.
	 */
	public static void main(String[] args) {
		if (args.length != 1) { 			
			System.out.println(USAGE);
			System.exit(0);
		}

		int port = 0;

		/**
		 * Check the input arguments and their order. 
		 * // parsInt = Input is string output is decimal integer
		 * // thrown if input string does not have appropriate format
		 */
	
		try {
			port = Integer.parseInt(args[0]);    
		} catch (NumberFormatException e) {      
			System.out.println(USAGE);
			System.out.println("Error: port " + args[0] + "is not an integer");
			System.exit(0);
		}

	
		/**
		 * Create a server to the client.
		 */
		
		Socket sock = null;
		ServerSocket ssock = null;
		Connection connection = null;		// each client/player new Connection 
		Lobby lobby = new Lobby();   		//check for 2 clients in GameUpdater.
		lobby.start();
		
		try {
			ssock = new ServerSocket(port);
			System.out.println("Waiting for client");
			
			while (true) {						
				sock = ssock.accept();
				//always true so new clients can connect, 
				System.out.println("Client is connected"); 
			
				//Create an echo object to communicate between Server and Client.
				connection = new Connection(sock, lobby);  
				lobby.addConnection(connection);	
				lobby.clientCheck();
				connection.start();
				
				// first message from server to client.
				connection.messageToClient(Protocol.Client.NAME + Protocol.General.DELIMITER1 
						+ serverName + Protocol.General.DELIMITER1 + Protocol.Client.VERSION + 
						Protocol.General.DELIMITER1 + Protocol.Client.VERSIONNO + 
						Protocol.General.DELIMITER1 + Protocol.Client.EXTENSIONS +
						Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 +
						0 + Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 + 0 +
						Protocol.General.DELIMITER1 + 0 + Protocol.General.DELIMITER1 + 0 +
						Protocol.General.DELIMITER1 + 0 + Protocol.General.COMMAND_END);
				
			}
			
		} catch (IOException f) {
			f.printStackTrace();
			
		}
	}
}
