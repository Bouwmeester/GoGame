package com.nedap.go.gogame;

public class Humanplayer implements Player {

	private String name;
	private Field field;
	
	public void Player(String name, Field field) {
		this.name = name;
		this.field = field; 	// to let know whether a player is black/white
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}


//	//private String name;
//	//private Field field;
//	
//	/**
//	 * Create a new player object, human or AI.
//	 */
//	//public Player(String name) //{ 		//field necessary?
//	//	this.name = name;
//		//this.field = field;
//	//}
//	
//	/**
//	 * Return the name of the player.
//	 */
//	public String getName(); //{
//		//return name;
//	//}
//	
////	public Field getField(){
////		return field;
////	}
//	
//	/**
//	 * Determine the field for the next move.
//	 * Necessary?
//	 */
//	//public abstract int determineMove(Board board);
//		
////	public void makeMove(Board board) {
////		int choice = determineMove(board);
////		int [x][y] = determineMove(board);
////		board.addStone(x, y, white);
////	}
//}
//
//////	private String name; 
//////	
//////	public Player(String name) {
//////		this.name = name;
//////	}
//////	
//////	public String getName
//////	public int makeMove
//////	
////}

