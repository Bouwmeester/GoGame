package com.nedap.go.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import general.Protocol.General;
import general.Protocol.Server;

/**
 * ClientConnection to handle server client communication client side. 
 * @author Bente.Bouwmeester
 *
 */
public class ClientConnection extends Thread {
	
	private Socket csock;
	private BufferedReader clientIn;
	private PrintWriter clientOut;

	
	public ClientConnection(Socket csockArg) throws IOException {
		csock = csockArg;
		clientIn = new BufferedReader(new InputStreamReader(csock.getInputStream()));
		clientOut = new PrintWriter(new OutputStreamWriter(csock.getOutputStream()));
	}

	/**
	 * Handle server output without client input.
	 * According to protocol  
	 */
	
	public void run() {
		String serverInput = " ";  
		try {
			while ((serverInput = clientIn.readLine()) != null) {
				String[] serverInputs = serverInput.split("\\" + General.DELIMITER1);
				System.out.println("Server: " + Arrays.toString(serverInputs));
				if (serverInputs[0].equals(Server.ERROR)) {
					System.out.println(Server.ERROR + General.DELIMITER1 + 
							Server.INVALID + General.DELIMITER1 + "try again ");
				} else if (serverInputs[0].equals(Server.START)) {
					// Standard Settings BLACK 13
					// Should be be chosen by player1 after Start message
					messageToServer(general.Protocol.Client.SETTINGS +
							General.DELIMITER1 + "BLACK" + General.DELIMITER1 + 13);
					
				} else if (serverInputs[0].equals(Server.FIRST)) {
					System.out.println("Firt turn of player1");
				} else if (serverInputs[0].equals(Server.TURN)) {
					System.out.println("TURN player1 x_y player2");
				} else if (serverInputs[0].equals(Server.PASS)) {
					System.out.println("TURN player1 PASS player2");
				} else if (serverInputs[0].equals(Server.ENDGAME)) {
					if (serverInputs[1].equals(Server.FINISHED)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + 
								Server.FINISHED + General.DELIMITER1 + 
								"player1 score player2 score");
					} else if (serverInputs[1].equals(Server.ABORTED)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + Server.ABORTED);
					} else if (serverInputs[1].equals(Server.TIMEOUT)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + Server.TIMEOUT);
					}
							
				} else if (serverInputs[0].equals(Server.NAMETAKEN)) {
					System.out.println(Server.NAMETAKEN + General.DELIMITER1 + "playername");
				} else if (serverInputs[0].equals(Server.UNKNOWN)) {
					System.out.println(Server.ERROR + General.DELIMITER1 + Server.UNKNOWN +
							General.DELIMITER1 + "COMMAND");
				} else if (serverInputs[0].equals(Server.INCOMPATIBLEPROTOCOL)) {
					System.out.println(Server.INCOMPATIBLEPROTOCOL + General.DELIMITER1);
				} else if (serverInputs[0].equals(Server.OTHER)) {
					System.out.println(Server.OTHER + "");
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
			shutDown();
		}

	}

	/**
	 * Sends message to Server.
	 * @param message
	 */
	public void messageToServer(String message) {
		clientOut.println(message);
		clientOut.flush();
	}
	

	/**
	 * Closes all threads. 
	 */
	public void shutDown() {
		try {
			clientIn.close();
			clientOut.close();
			csock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}


