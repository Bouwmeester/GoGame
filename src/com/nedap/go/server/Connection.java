package com.nedap.go.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import com.nedap.go.gogame.Field;
import com.nedap.go.gogame.Game;
import com.nedap.go.gogame.Player;
import general.Protocol.General;
import general.Protocol.Server;


/**
 * Connection for client server communication.
 * @author Bente.Bouwmeester
 *
 */

public class Connection extends Thread implements Player {
	protected Socket sock;
	protected BufferedReader in;			//reads text from character input stream
	protected PrintWriter out;		//writes text to a character output stream 
	protected String playerName = " "; 
	protected Lobby lobby;
	protected int x;
	protected int y;
	protected String[] moveInputs;
	protected Game game;
	private boolean white;
	
	
	public Connection(Socket sockArg, Lobby lobby) throws IOException {
		this.lobby = lobby;
		sock = sockArg;
		// in reads a string of the socket-connection and prints to default output
		//out prints on the output stream.
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
	}
	
	/**
	 * Sets the game for 2 connections. 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Set color, default color is white.
	 */
	public void setColor(boolean white) {
		this.white = white; 
	}
	
	/**
	 * Which color is this player.
	 */
	public boolean isWhite() {
		return white;
	}
	
	/**
	 * Handle different input of the command line.
	 */
	public void run() {
		String socketinput = "";
		
		try {
			while ((socketinput = in.readLine()) != null) {
				String[] commandInputs = socketinput.split("\\" + General.DELIMITER1); 
				System.out.println(playerName + ":" + Arrays.toString(commandInputs));
				// delimiter1 = $
				if (commandInputs[0].equals("NAME")) {
					playerName = commandInputs[1];
					// System.out.println(playerName);
					System.out.println(playerName + ":" + Arrays.toString(commandInputs));
					//Not working, who's turn is it. 
//				} else if (commandInputs[0].equals(Server.TURN)) {
//					if (commandInputs[2].equals(Server.TURN)) {
//					Create turn checker method in Game					
//						System.out.println(Server.TURN + General.DELIMITER1 +
//								playerName + General.DELIMITER1 + Server.TURN +
//								playerName.other + General.DELIMITER1 );
//					} else if (commandInputs[2].equals(general.Protocol.Client.MOVE){
//						System.out.println(Server.TURN + General.DELIMITER1 +
//								playerName + General.DELIMITER1 + general.Protocol.Client.MOVE  +
//								playerName.other + General.DELIMITER1);
//					} else if (commandInputs[2].equals(Server.PASS)) {
//						game.Pass (make no Move)
//						System.out.println(Server.TURN + General.DELIMITER1 +
//								playerName + General.DELIMITER1 + Server.Pass +
//								playerName.other + General.DELIMITER1);
//					}
										
				} else if (commandInputs[0].equals(general.Protocol.Client.REQUESTGAME)) { 
					// "REQUESTGAME"
					lobby.addtoGamelist(this);
					lobby.playRequestGame();
				} else if (commandInputs[0].equals(general.Protocol.Client.MOVE)) {
					// split 1_3 met delimiter2 
					moveInputs = commandInputs[1].split(General.DELIMITER2);
					x = Integer.parseInt(moveInputs[1]);
					y = Integer.parseInt(moveInputs[0]);
					if (game.legalMove(x, y)) {
						game.addStone(x, y, white);
						// TODO 
						// notify all participating clients of the move
						// MOVE$x_y + fieldColor 
					} else {
						System.out.println(playerName + " made an invalid move");
						out.println(Server.ERROR + General.DELIMITER1 + Server.INVALID);
					}
					
//				} else if (commandInputs[0].equals(general.Protocol.Client.SETTINGS)) {
//						Does not work, should be the start message for the second client.
//						String colorPlayer = commandInputs[1];
//						Field colorPlayer2 = Field.WHITE; //player1 is black, should be Field.other
//						String boardDim = commandInputs[2];
////						messageToClient(general.Protocol.Server.START + 
////						General.DELIMITER1 + "2" + General.DELIMITER1 + colorPlayer2 +
////						General.DELIMITER1 + boardDim + General.DELIMITER1 +
////						player1.playerName + General.DELIMITER1 
////						+ player2.playerName)  
//						
				
				} else if (commandInputs[0].equals(Server.ENDGAME)) {
					if (commandInputs[1].equals(Server.FINISHED)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + 
								Server.FINISHED + General.DELIMITER1 + 
								"player1 score player2 score");
					} else if (commandInputs[1].equals(Server.ABORTED)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + Server.ABORTED);
					} else if (commandInputs[1].equals(Server.TIMEOUT)) {
						System.out.println(Server.ENDGAME + General.DELIMITER1 + Server.TIMEOUT);
					}
					
				} else if (commandInputs[0].equals(Server.NAMETAKEN)) {
					// if playerName player1 equals playerName player2
					// print Server.NAMETAKEN + playerName
					System.out.println(Server.NAMETAKEN + General.DELIMITER1 + "playername");
				} else if (commandInputs[0].equals(Server.UNKNOWN)) {
					System.out.println(Server.ERROR + General.DELIMITER1 + Server.UNKNOWN +
							General.DELIMITER1 + "COMMAND");
				} else if (commandInputs[0].equals(Server.INCOMPATIBLEPROTOCOL)) {
					System.out.println(Server.INCOMPATIBLEPROTOCOL + General.DELIMITER1);
				} else if (commandInputs[0].equals(Server.OTHER)) {
					System.out.println(Server.OTHER + "");
				}
				
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			shutDown();
		}
	}

	/**
	 * Message to the client.
	 * @param message
	 */
	public void messageToClient(String message) {
		out.println(message);
		out.flush();
	}
	
	/**
	 * Send a Start message to the first player.
	 * Different Start message to the second or > player.
	 * Print on 1 specific connection (player1 and 2 get different message)
	 */
	public void startMessageSetUp() {
		messageToClient(general.Protocol.Server.START +
				general.Protocol.General.DELIMITER1 + "2");
	}
		
//	public void startMessageCLient2() {	
//		messageToClient(general.Protocol.Server.START + 
//				General.DELIMITER1 + "2" + General.DELIMITER1 + colorPlayer2 +
//				General.DELIMITER1 + game.getBoardSize() + General.DELIMITER1 +
//				player1.playerName + General.DELIMITER1 
//				+ player2.playerName);
//		out.flush();
//	}
	
	
	/**
	 * Closes the connection, terminate sockets.
	 * Also needs to be done when client abruptly disconnects
	 * shutDown should also be called when the response is not within 
	 * appropriate time frame. 
	 */
	
	public void shutDown() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
