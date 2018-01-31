package com.nedap.go.gogame;

import com.nedap.go.gui.GoGUIIntegrator;

/**
 * 
 * @author Bente.Bouwmeester
 *
 */

public class Board {

	/**
	 * Creates an empty board, with enum class Fields. 
	 * enum = (BLACK,WHITE,EMPTY)
	 * The board is connected to the GUI where new stones are 
	 * added and removed. 
	 */
	
	private int dim = 13;
	//public static int SIZE = 13;  //create somewhere in settings 
	private Field[][] fields;
	private static GoGUIIntegrator gogui;
	
	public Board(int dimension) {
		this.dim = dimension;
		//DIM = SIZE;
		try {
			fields = new Field[dimension][dimension]; 
			for (int x = 0; x < dimension; x++) {
				for (int y = 0; y < dimension; y++) {
					fields[x][y] = Field.EMPTY;
				}
			}
		} catch (NegativeArraySizeException e) {
			e.printStackTrace();
		}
		// start a GUI.
		gogui = new GoGUIIntegrator(true, true, dim);
		gogui.startGUI();
		
	}
	
	/**
	 * Returns the dimension of the board.
	 * @return
	 */
	public int getBoardSize() {
		return dim;
	}
	
	/**
	 * Is the move within dimension of the board.
	 */
	public boolean isWithinDim(int x, int y) {
		if (0 <= x && x < dim && 0 <= y && y < dim) {
			return true; 
		} else {
			return false;
		}
	}	
	
	/**
	 * Get and Set a certain field [x][y].
	 * @param x
	 * @param y
	 * @return
	 */
	public Field getField(int x, int y) {
		return this.fields[x][y];
	}
	
	public void setField(int x, int y, Field colorField) {
		fields[x][y] = colorField;
	}
	

	/**
	 * Add or remove a stone on the the GUI.
	 * @param x
	 * @param y
	 * @param white
	 */
	public void addStone(int x, int y, boolean white) {
		fields[x][y] = white ? Field.WHITE : Field.BLACK;		
		//if white plays put field.white else field.black
		gogui.addStone(x, y, white);	
		// add 1 stone
	}
	
	public void removeStone(int x, int y) {
		fields[x][y] = Field.EMPTY;
		gogui.removeStone(x, y);
	}
	
	/**
	 * Main method of the board.
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		Board board = new Board(13);			//13 = size of board

	}

	
	/**
	 * Not finished;
	 * Create a deep copy of the board, for strategy testing. 
	 */
//	public Board deepCopy() {
//		Board newboard = new Board();
//		for (int x = 0; x < DIM; x++) {
//			for (int y = 0; y < DIM; y++;		
//			newboard.setField(i, this.getField(int x, int y));
//		}	
//		return newboard;
//	}

}


