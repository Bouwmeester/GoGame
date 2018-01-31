package com.nedap.go.server;

import java.util.ArrayList;
import java.util.List;

import com.nedap.go.gogame.Game;


/**
 * Place to hold 2 clients before they can play a game.
 * If 2 clients request a game a new game is started. 
 * Check for legality and validity of moves.
 * Send information to all participating clients.
 * @author Bente.Bouwmeester
 *
 */

public class Lobby extends Thread {

	private List<Connection> clientlist;
	private List<Connection> gameList;
	
	/**
	 * Check for 2 clients and then start a game.
	 * Every game should have a separate thread 
	 */
	public Lobby() {
		this.clientlist = new ArrayList<Connection>();
		this.gameList = new ArrayList<Connection>();
	}
	
	/**
	 * List of available clients.
	 * @param client
	 */
	public void addConnection(Connection client) {
		clientlist.add(client);
		System.out.println("There is a new client added");	
	}
	
	/**
	 * List of clients who requested a game. 
	 * @param connection
	 */
	public void addtoGamelist(Connection connection) {
		gameList.add(connection); 	
		System.out.println("A player is ready to play the game");
	}

	/**
	 * List of clients who Requested a game.
	 * @param connection
	 */
	public void clientCheck() {
		if (clientlist.size() == 2) {
			clientlist.remove(0);
			clientlist.remove(0);
			System.out.println("There are enough players to play a Game ");
		}
	}
	
	/**
	 * If there are 2 clients in Gamelist a Game can be started.
	 */
	public void playRequestGame() {
		if (gameList.size() == 2) {
			startGame(gameList.get(0), gameList.get(1));
			gameList.remove(0);
			gameList.remove(0);
			System.out.println("Requested Game has started");  //add playerName s  
		}
	}
	
	/**
	 * Start a game thread. 
	 * Use setGame to create a game with connection player1 and player2.
	 * //@param player1
	 * //@param player2
	 */
	
	public void startGame(Connection player1, Connection player2) {
		Game game = new Game(player1, player2);
		player1.setGame(game);
		player1.setColor(true);
		player2.setGame(game);
		player2.setColor(false);
		player1.startMessageSetUp();
		//startMessageClient2 is not finished.
		//player2.startMessageCLient2();
		
	}
}

