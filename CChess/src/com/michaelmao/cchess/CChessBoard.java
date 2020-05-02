package com.michaelmao.cchess;

import java.util.ArrayList;

import java.util.List;

public class CChessBoard {
	List<CChessPiece> pieces = new ArrayList<CChessPiece>();
	
    
	
	String desc = "";
	
	
	
	List<Integer> pieceLocations = new ArrayList<Integer>();
	
	public void fillLocations() {
		for(int i = 0; i < pieces.size(); i++) {
			int row = pieces.get(i).row;
			int col = pieces.get(i).col;
			
			int total = row * 9 + col;
			
			pieceLocations.add(total);
		}
	}
	
	public void movePiece(int col, int row, int newCol, int newRow) {
		int total = row * 9 + col;
		
		for(int i = 0; i < pieceLocations.size(); i++) {
			if(total == pieceLocations.get(i)) {
				pieces.get(i).row = newRow;
				pieces.get(i).col = newCol;
				pieceLocations.clear();
				fillLocations();
				printBoard();
			}
		}
	}
	
	public boolean canMoveRook(int fromCol, int fromRow, int toCol, int toRow) {
		if(fromCol == toCol || fromRow == toRow) { 
			return true;
		}
		return false;
	}
	
	public void printBoard() {
		
		desc = "";
		
		System.out.print("   ");
		for(int col = 0; col < 9; col++) {
			if(col == 8) {
//				System.out.println(col + 1);
				desc += (col + 1) + "\n";
			} else {
//				System.out.print((col + 1) + " ");
				desc += (col + 1) + " ";
			}
		}
		
		for(int row = 0; row < 10; row++) {
			
			if(row == 9) {
//				System.out.print(row + 1 + " ");
				desc += (row + 1) + " ";
			} else {
//				System.out.print(" " + (row + 1) + " ");
				desc += " " + (row + 1) + " ";
			}
			
			for(int col = 0; col < 9; col++) {
				
				if(col == 8) {
//					System.out.println(".");
					
					if(checkLocation(row * 9 + col)) {
						desc += checkPiece(row * 9 + col) + ".\n";
					} else {
						desc += ".\n";
					}
				} else {
					if(checkLocation(row * 9 + col)) {
						desc += checkPiece(row * 9 + col) + " ";
					} else {
//						System.out.print(". "); 
						desc += ". ";
					}
				}
					
				
			}
		}
		System.out.println(desc);
	}
	
	private boolean checkLocation(int location) {
		for(int i = 0; i < pieceLocations.size(); i++) {
			if(location == pieceLocations.get(i)) {
				return true;
				
			}
		}
		
		return false;
	}
	
	private char checkPiece(int location) { 
		CChessPiece piece = new CChessPiece(0,0,false,'0');
		for(int i = 0; i < pieceLocations.size(); i++) {
			if(location == pieceLocations.get(i)) piece = pieces.get(i);
		}
		
		return piece.rank;
	}
}
