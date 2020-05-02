package com.michaelmao.cchess;

public class CChessPiece {
	int row;
	int col;
	boolean isRed;
	char rank;
	
	public CChessPiece(int row, int col, boolean isRed, char rank) {
		this.row = row;
		this.col = col;
		this.isRed = isRed;
		this.rank = rank;
	}
}
