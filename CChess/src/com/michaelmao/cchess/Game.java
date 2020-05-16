package com.michaelmao.cchess;

public class Game {
	
	
	
	
	static CChessBoard board = new CChessBoard();
	
	public static void main(String args[]) {
		board.pieces.add(new CChessPiece(0, 0, false, 'R'));
		board.fillLocations();
		board.printBoard();
		board.movePiece(0, 0, 1, 1);
	}
	
	//uppercase is black
	//lowercase is red
}
