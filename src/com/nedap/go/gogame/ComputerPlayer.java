package com.nedap.go.gogame;


public class ComputerPlayer implements Player {

	private int dim = 13;
	Board board = new Board(dim); 
	Field colorField;
	
	public ComputerPlayer(String name, boolean color) {
		
	}
	
	@Override
	public String getName() {
		return name; 
		
	}   // comment
	
	public int determineMove(int x, int y) {
		
		for (int i = 0; i <  board.getBoardSize() * board.getBoardSize(); i++) {
			// for random x with bijpassende y
			//for(int x = Math.random())
			if (board.getField(x, y).equals(Field.EMPTY)) {
				board.setField(x, y, colorField);
			}
			
		}

		return 0;
	}
	

}


