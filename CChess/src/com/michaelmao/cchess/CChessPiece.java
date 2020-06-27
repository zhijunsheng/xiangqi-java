package com.michaelmao.cchess;

enum Rank {
	rook, knight, bishop, guard, king, pawn, cannon;
}

public class CChessPiece {
	int row;
	int col;
	boolean isRed;
	Rank rank;
	
	public CChessPiece(int col, int row, boolean isRed, Rank rank) {
		this.col = col;
		this.row = row;
		this.isRed = isRed;
		this.rank = rank;
	}
}
