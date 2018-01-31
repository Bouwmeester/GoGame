package com.nedap.go.gogame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Game extends Thread {

	/**
	 * Class for the go game, where some of the rules are taken into account.
	 * Is a move a valid move, is there a group of stones completely surrounded by other stones
	 * and should thereby be removed.
	 */
	public static final int NUMBER_OF_PLAYERS = 2; 

	/**
	 * The used board.
	 */
	private Board board;

	/**
	 * List of players of the game, human or non human.
	 */
	private Player[] player; // connection
	
	/**
	 * The index of the current player.
	 * Used to determine which Turn of the player
	 */
	private int current;

	// -----Constructors----

	/**
	 * Create a new game object with 2 players. player1 and player2
	 */

	public Game(Player player1, Player player2) {
		board = new Board(13); // Start moet dim meegeven
		player = new Player[NUMBER_OF_PLAYERS];
		player[0] = player1;
		player[1] = player2;
		current = 0;
		// gameList can be added to make a multi player game,
	}

	/**
	 * Who s turn is it. The player who has the black stones starts the game.
	 * Does not work. 
	 */
	// public void turnPlayer() {
	// while (playGame()) {
	// player[current].addStone(int x, int y, color white);
	// current = (current + 1) % NUMBER_OF_PLAYERS;
	// }
	// }
	

	/**
	 * Information about the size and board.
	 * @return
	 */
	public int getBoardSize() {
		return board.getBoardSize();
	}
	
	public Board getBoard() {
		return board;
	}

	/**
	 * Valid moves.
	 */
	public boolean legalMove(int x, int y) {
		return board.isWithinDim(x, y) && board.getField(x, y).equals(Field.EMPTY);
	}

	/**
	 * Public point method.
	 */
	public Point pointAt(int x, int y) {
		return new Point(x, y);
		
	}
	
	
	/**
	 * Breadth first search and loop over the entire board. Create a queue with
	 * points(x,y) of the other color who could possibly be part of a group. Create
	 * a queue of points who are checked, to prevent double checking use boolean visited. 
	 * If a point is of other color, and is not already in one of the lists; add. 
	 * If a pointCheck (point fulfills all requirements add to the currentColorGroups list.
	 * 
	 */

	public Set<List<Point>> groupChecker(Field currentColor) {
		// point.x use the x of this certain point
		LinkedList<Point> checkedPoints = new LinkedList<Point>();
		Set<List<Point>> currentColorGroups = new HashSet<List<Point>>();

		// Mark all [x] [y] as not visited
		boolean[][] visited = new boolean[board.getBoardSize()][board.getBoardSize()];

		for (int x = 0; x < board.getBoardSize(); x++) {
			for (int y = 0; y < board.getBoardSize(); y++) {
				if (visited[x][y]) {
					continue;
				}
				// Every group in new list.
				LinkedList<Point> group = new LinkedList<Point>();
				LinkedList<Point> queueToCheck = new LinkedList<Point>();
				if (board.getField(x, y) == currentColor) {
					queueToCheck.add(new Point(x, y));
				}
				// for the whole list
				while (queueToCheck.size() != 0) {
					// poll is remove first point out queueToCheck list
					// pointCheck is the current point which is going to be checked
					Point pointCheck = queueToCheck.poll();
					visited[pointCheck.x][pointCheck.y] = true;
					// Set of points of the neighbors of the current point (this)
					ArrayList<Point> neighborSet = this.getNeighbor(pointCheck);
					// for the whole set
					for (Point neighbor : neighborSet) {
						// if current move (x,y field of the board) is the other color,
						// and is not already in one of the lists, add
						if (board.getField(neighbor.x, neighbor.y) == currentColor 
								&& !visited[neighbor.x][neighbor.y]) { 
							queueToCheck.add(neighbor);
						}
					}
					checkedPoints.add(pointCheck);
					group.add(pointCheck);
				}
				// Mark current point as visited and enqueue
				if (!group.isEmpty()) {
					currentColorGroups.add(group);
				}
			}
		}
		return currentColorGroups;
	}

	/**
	 * Input is point to check Output is surrounding points
	 * check whether the current point is surrounded by colors of the same color,
	 * borders etc.
	 * Returns only the neighbors of the same color. 
	 * @return
	 */
	public ArrayList<Point> getNeighbor(Point point) {
	// point.x gives only x of the point back 
		ArrayList<Point> list = new ArrayList<Point>();
		
		int dim = board.getBoardSize();
		int x = point.x;
		int y = point.y;
		
		
		//left neighbor
		if (x > 0) {
			list.add(pointAt(x - 1, y));
		} 
		
		//top neighbor
		if (y > 0) {
			list.add(pointAt(x, y - 1));
		}
		
		//right neighbor
		if (x < dim - 1) {
			list.add(pointAt(x + 1, y));
		}
		
		//lower
		if (y < dim - 1) {
			list.add(pointAt(x, y + 1));
		}
		return list;
	}

	/**
	 * Captured groups. Check all points of a group for liberties.
	 * If liberties > 0 is false.
	 */

	public boolean capturedGroup(List<Point> group) {
		for (Point point : group) {
			if (countLiberties(point) > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * For 1 point getNeighbors check if the field is empty .
	 * For each not empty field, liberties + 1
	 * 
	 * @param pointer
	 * @return
	 */
	public int countLiberties(Point pointer) {
		ArrayList<Point> libertyPoint = getNeighbor(pointer);
		int liberties = 0;
		for (Point point : libertyPoint) {
			if (board.getField(point.x, point.y) == Field.EMPTY) {
				liberties = liberties + 1;
			}
		}
		return liberties;
	}
	
	/**
	 * For all groups of list of potential capturedGroups.
	 * Check if the group is captured 
	 * @param currentColorGroups
	 */
	public void captruredGroupChecker(Set<List<Point>> currentColorGroups) {
		for (List<Point> group : currentColorGroups) {
			if (capturedGroup(group)) {
				removeGroup(group);
			}
		}
	}

	/**
	 * Remove certain group of the board when captured.
	 * For all points of the group change the field to empty 
	 * @param group
	 */
	public void removeGroup(List<Point> group) {
		for (Point point : group) {
			board.removeStone(point.x, point.y);
		}
	}
	

	/**
	 * Add stones to the board.
	 * For a certain field x,y check if this move surrounds a stone of the other color.
	 * Also check if the move x,y results in a suicide move, in that case the stone also
	 * should be removed. 
	 * @param x
	 * @param y
	 * @param white
	 */
	public void addStone(int x, int y, boolean white) {
		if (legalMove(x, y)) {
			board.addStone(x, y, white);
			Set<List<Point>> groupOfGroups = groupChecker(board.getField(x, y).other());
			captruredGroupChecker(groupOfGroups);
			Set<List<Point>> potentialSuicide = groupChecker(board.getField(x, y));
			captruredGroupChecker(potentialSuicide);
			
		}
	}
	
	/**
	 * Not finished methods which also could be included in Game.
	 * A winningMove --> this move creates a direct Win for a player 
	 * could be used to make a smart strategy.
	 * 
	 * endTheGame --> The game should end if either of the players has won
	 * or if there is a draw. Something similar should be used to end a game
	 * in case one of the players quits or the client gets disconnected 
	 */
	// public boolean winningMove(int x, int y) {
	// if (totalArea1 > totalArea2 ) {
	// player 1 wins
	// } else if (totalArea2 > totalArea1 ) {
	// player 2 wins
	// }
	// }

	// public void endTheGame() { //create has won method
	// if (Black.hasWon()) { //player1.hasWon
	// System.out.println("Player" + <name> + "has won!");
	// } else if (White.hasWon() {
	// System.out.println("Player" + <name > + "has won!" );
	// } else if (draw()) {
	// System.out.println("No winners, no losers.");
	// System.exit(); //or enough to shutDown()?
	// }
	// }
	
	
}


