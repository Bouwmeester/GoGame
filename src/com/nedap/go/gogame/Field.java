package com.nedap.go.gogame;


public enum Field {

	/*
	 * @ ensures this == Field.EMPTY ==> \result == Field.EMPTY; 
	 * ensures this == Field.BLACK ==> \result == Field.BLACK; 
	 * ensures this == Field.WHITE ==> \result == Field.White;
	 */
	
	EMPTY, BLACK, WHITE;

	public Field other() {
		if (this == BLACK) {
			return WHITE;
		} else if (this == WHITE) {
			return BLACK;
		} else {
			return EMPTY;
		}
	}
}

