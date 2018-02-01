package com.nedap.go.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import com.nedap.go.gui.GoGUIIntegrator;

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
	private String name;
	private String color;
	private String boardSize;
	private GoGUIIntegrator gogui;

	
	public ClientConnection(Socket csockArg, String name) throws IOException {
		csock = csockArg;
		clientIn = new BufferedReader(new InputStreamReader(csock.getInputStream()));
		clientOut = new PrintWriter(new OutputStreamWriter(csock.getOutputStream()));
		this.name = name;
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
					if(serverInputs.length > 3) {
						//SPEL starten
						// Bord maken met bord dim 13
						// kleur client connection
						color = serverInputs[2];
						boardSize = serverInputs[3];
						gogui = new GoGUIIntegrator(true, true, Integer.parseInt(boardSize));
						gogui.startGUI();
						
						System.out.println("Second start message");
					} else {
						messageToServer(general.Protocol.Client.SETTINGS +
							General.DELIMITER1 + "BLACK" + General.DELIMITER1 + 13);
						// Standard Settings BLACK 13
						// Should be be chosen by player1 after Start message
					}
				} else if (serverInputs[0].equals(Server.TURN)) {
					if (!serverInputs[2].equals(Server.FIRST)) {
						String[] moveInputs = serverInputs[2].split(General.DELIMITER2);
						int x = Integer.parseInt(moveInputs[1]);
						int y = Integer.parseInt(moveInputs[0]);
						boolean isWhite = false;
						if (name.equals(serverInputs[1])) {
							if (color.equals("white")) {
								isWhite = true;
							} else {
								isWhite = false;
							}
						}
						gogui.addStone(x, y, isWhite);
					}
					System.out.println("TURN player1 x_y player2");
					if (serverInputs[3].equals(name)) {
						
						System.out.println("Make a Move");
					}
					//TURN naar move
					
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


