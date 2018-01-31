package com.nedap.go.gogame;

//public class Uitprobeer {
//
//	Board board = new Board;
//	Game game = new Game;
//
//	board.addStone(x,y)
//}

//Restjes

//Client 
//echoClient = new Connection("Andres", csock); //Change name 
//Thread streamInputHandler = new Thread(echoClient);
//streamInputHandler.start();
//echoClient.handleConsoleInput();		//correct name, handleTerminalInput?
//echoClient.shutDown();

//Lobby
//public void run() {
//System.out.println("Run GameUpdater");		//ends when player1 or 2 has won?
//while (true) {
//	System.out.println("Client list size " + clientlist.size());
//	if (clientlist.size() == 2) {
//		//startGame(clientlist.get(0) player1, clientlist.get(1)player2);
//		clientlist.remove(0);
//		clientlist.remove(0);
//		System.out.println("Game has started");
//	}
//	try {
//		Thread.sleep(1000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//
//}

//Board (used in Game)

///**
// * Check if input (row,col) is valid input.
// * Valid if input (row, col) is within dimensions.
// * Valid if input is not already used, so input EMPTY
// * (Valid if input does not result in previous set)
// * 
// * Or dim-1 ? 
// */
//public boolean isField(int x, int y) {
//	if (0 <= x && x < DIM && 0 <= y && y < DIM) {
//		return true; 
//	} else {
//		return false;
//	}
//}
//
//public boolean isEmpty(int x, int y) {
//	return fields[x][y]== Field.EMPTY; 
//
//}

//public boolean isValid(int x, int y) {
//	if (this.isField(x, y) && this.isEmpty(x, y)) {
//		Board.makeMove(x,y); 		//create makeMove or addStone (exists?)
//		Board.update					// update client boards 
//	}
//}
//
//public boolean winningMove(int x, int y) {
//	if (totalArea1 > totalArea2  ) {
//		 player 1 wins
//	} else if (totalArea2 > totalArea1 ) {
//		player 2 wins 
//	}
//}

//Board (not used)


//public void setField(int i, Field f) {
//	fields[i] = f;
//}
//
//public Field getField(int i) {
//	return this.fields[i];
//} 

//public int index(int x, int y) {
//return DIM * (row + col);				//take Dim into account?
//}


///**
// * Check row,col around certain field for surrounding stones.
// */
//for(
//
//int i = 0;i<DIM-1;i++)
//{
//	for (int j = 0; i < DIM - 1; j++ ) {
//	if (stones [i + row] [col + j ] == Field.BLACK { 
//		
//			
//	} else stones [i + row ] [col + j ] == Field.WHITE)	{
//		
//	}
//	}
//}

// Connection

/**
 * Read a line from the default input. 
 */
//static public String readString(String teskt) {
//	System.out.println(teskt);
//	String antw = null;
//	try {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		antw = in.readLine();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	return (antw == null) ? " "	: antw;  // wat doet dit 
//}


/**
 * Reads a string from the console (client output?) and sends the string 
 * to the socket-connection to Echo. 
 * Make a Exit method to stop reading (or read per line?)
 */

//public void handleConsoleInput() {
//	String consolline;
//	try {
//		while ((consolline = readString(" ")) != null) {   //&& !consolline.equals(EDXIT) {
//			out.write(consolline + "\n");
//			out.flush(); //to make sure all commands are taken into account
//		}
//	} catch (IOException f) {
//		f.printStackTrace();
//		shutDown(); 	
//	}
//}

/**
 * Reads a string form the terminal (client input) and sends the string
 * to the socket-connection to Echo.
 * or is handleConsoleInput enough?
 */

//public void handleTerminalInput() {
//	//todo
//}


//GAME
// niet naar boven als het de rand is 
// zelfde kleur verder kijken 
// niet dezelfde kleur geen liberty --> liberties opslaan
// leeg --> 1 liberty


// steen neer leggen
// buren andere kleur --> hoeveel liberties heeft dit groepje
// heeft deze steen gezorgd dat er een groepje is ingesloten

// x + 1
//if (move [x + 1] [y] != currentColor && move [x + 1] [y] != Field.EMPTY) {
//	  move to x + 1 + 1
//} else (move [x + 1] [y] == DIM ) {
//	  randen 
//}
//
//// x -1 
//if (move [x - 1] [y] != currentColor && move [x - 1] [y] != Field.EMPTY) {
//	  move to x - 1 + 1 --> niet nuttig 
//} else (move [x - 1] [y] == DIM ) {
//	  randen 
//}
//
//// y + 1
//if (move [x] [y + 1] != currentColor && move [x] [y + 1] != Field.EMPTY) {
//	  move to Y + 1 + 1  
//} else (move [x] [y + 1 ] == DIM ) {
//	  randen 
//}
//
//// y - 1
//if (move [x] [y - 1] != currentColor && move [x] [y - 1] != Field.EMPTY) {
//	  move to Y - 1 + 1  --> niet nuttig 
//} else (move [x] [y - 1 ] == DIM ) {
//	  randen 
//}
//}
//
//if (alle 4 de bovenstaande is is waar) {
//liberties = 0;
//}
//
//private int liberties = 0; 
//public int getLiberties() {
//
//}
//
//public void setLiberties (final int Liberties) {
//this.liberties = liberties;
//}

// GAME


//Things
///**
//* Check if input (row,col) is valid input.
//* Valid if input (row, col) is within dimensions.
//* Valid if input is not already used, so input EMPTY
//* (Valid if input does not result in previous set)
//* 
//* Or dim-1 ? 
//*/

//
//public boolean isEmpty(int x, int y) {
//	return fields[x][y]== Field.EMPTY; 
//
//}

//public boolean isValid(int x, int y) {
//	if (this.isField(x, y) && this.isEmpty(x, y)) {
//		Board.makeMove(x,y); 		//create makeMove or addStone (exists?)
//		Board.update					// update client boards 
//	}
//}
//
//public boolean winningMove(int x, int y) {
//if (totalArea1 > totalArea2  ) {
//	 player 1 wins
//} else if (totalArea2 > totalArea1 ) {
//	player 2 wins 
//}
//}


///**
//* Plays the Tic Tac Toe game. <br>
//* First the (still empty) board is shown. Then the game is played
//* until it is over. Players can make a move one after the other. 
//* After each move, the changed game situation is printed.
//*/
//private void play() {
//	this.update();
//	while (board.gameOver() != true) {
//		players[current].makeMove(board);
//		current = (current + 1) % NUMBER_PLAYERS;
//		this.update();
//	}
//	printResult();
// 		


//public class GameHandler {
//
//	private Connection currentPlayer;
//	
//	/**
//	 * Current state of the player.
//	 * Use connection class? 
//	 */
//	public GameHandler (Connection currentPlayer) {
//		currentPlayer = gameList.get[0];
//	}
//	
//	
//	public void playerState(Connection currentPlayer) {
//		currentPlayer = Field.BLACK;
//	}
//	
//	/**
//	 * Who s turn is it. Accept turn 1 (this is assigned to player "BLACK".
//	 */
//	public void turnPlayer(Connection currentPlayer) {
//		if (currentPlayer == Field.BLACK) {		//create currentPlayer
//			currentPlayer = Field.WHITE;
//		} else {
//			currentPlayer = Field.BLACK;
//		}
//	}
//	
//}
 

//		//if loopje for borders and corners

//left corner x= 0 , y = 0
//if (point.equals(point(0, 0))) {
//set.add(point(0, 1));
//set.add(point(1, 0));
//} else {
//	
//}
//
//// for all points x >0 1 till x < DIM - 1 with y = 0.
//if (point.equals(point(x + 1, 0))) {
//set.add(point(x - 1, 0));
//set.add(point(center));
//set.add(point(x + 1 + 1, 0));
//} else {
//
//}
//
////right corner points, x = dim y = 0
//if (point.equals(point(DIM,0))) {
//set.add(DIM - 1 , 0);
//set.add(DIM, 1)
//} else {
//
//}
//
////for all x = 0 , y + 1 till y = DIM - 1
//if (point.equals(point(x , y + 1))) {
//set.add(point(x- 1, y - 1));
//set.add(point(x + 1 , y));
//set.add(point(x, y + 1 + 1));
//} else {
//
//}
//
//// for all x,y no corners or border HOW 
//if(point.equals(point(x,y)))) {
//set.add(point(x, y - 1));
//set.add(point(x + 1, y));
//set.add(point(x, y + 1));
//set.add(point(x - 1, y));
//} else {
//
//}
//
//// for all x = DIM , y + 1 till y = DIM - 1
//if(point.equals(point(DIM, y + 1))) {
//set.add(point(DIM, y - 1));
//set.add(point(DIM - 1, y + 1));
//set.add(point(DIM, y + 1));
//} else {
//
//}
//
//// for left lower corner
//if (point.equals(point(0 , DIM))) {
//set.add(point(0, DIM - 1));
//set.add(point(1 , DIM));
//} else {
//
//}
//
//// for all x = 1 till x = DIM - 1 and y = DIM
//if (point.equals(point(x + 1, DIM))) {
//set.add(point(x , DIM ));
//set.add(point(x + 1 , DIM - 1));
//set.add(point(x + 1 + 1 , DIM));
//} else {
//
//}
//
//// for right lower corner
//if (point.equals(point(DIM, DIM))) {
//set.add(point(DIM, DIM - 1));
//set.add(point(DIM - 1, DIM));
//}


//GAME
/**
 * Starts the Go game. Continues until the user indicates doesn't want to play
 * anymore.
 */

// public void playGame() { // every game new thread
// boolean playTheGame = true;
// while (playTheGame) {
// //reset(); // create reset,play and readboolean method.
// //play();
// //doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
// }
// }
//
