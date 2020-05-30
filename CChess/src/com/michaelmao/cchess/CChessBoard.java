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
	
	public boolean canMoveBishop(int fromCol, int fromRow, int toCol, int toRow) {
		if((toCol - fromCol == 2 || toCol - fromCol == -2) && (toRow - fromRow == 2 || toRow - fromRow == -2)) {
			return true;
		}
		
		return false;
	}
	
	public boolean canMoveGuard(int fromCol, int fromRow, int toCol, int toRow) {
		if((toCol - fromCol == 1 || toCol - fromCol == -1) && (toRow - fromRow == 1 || toRow - fromRow == -1)) {
			if(toCol > 2 &&  toCol < 6) {
				if(toRow >= 0 && toRow < 3 || toRow > 6 && toRow <= 9) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean canMoveCannon(int fromCol, int fromRow, int toCol, int toRow) {
		if(fromCol == toCol || fromRow == toRow) { 
			return true;
		}
		return false;
	}
	
	public boolean canMovePawn(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
		if(isRed) {
			
			//red
			if(fromRow < 5) {
				if((fromCol == toCol && toRow == fromRow - 1) || (fromRow == toRow && (toCol == fromCol + 1 || toCol == fromCol - 1))) {
					return true;
				}
			} else if(fromCol == toCol && toRow == fromRow - 1) {
				return true;
			}
		} else {
			
			//black
			if(fromRow > 4) {
				if((fromCol == toCol && toRow == fromRow + 1) || (fromRow == toRow && (toCol == fromCol + 1 || toCol == fromCol - 1))) {
					return true;
				}
			} else if(fromCol == toCol && toRow == fromRow + 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canMoveKnight(int fromCol, int fromRow, int toCol, int toRow) {
		if((fromCol - toCol == 2 && fromRow - toRow == 1) || (fromRow - toRow == 2 && fromCol - toCol == 1)) {
			return true;
		} else if((toCol - fromCol == 2 && fromRow - toRow == 1) || (fromRow - toRow == 2 && toCol - fromCol == 1)) {
			return true;
		} else if((toCol - fromCol == 2 && toRow - fromRow == 1) || (toRow - fromRow == 2 && toCol - fromCol == 1)) {
			return true;
		} else if((fromCol - toCol == 2 && toRow - fromRow == 1) || (fromRow - toRow == 2 && fromCol - toCol == 1)) {
			return true;
		}
		return false;
	}
	
	public boolean canMoveKing(int fromCol, int fromRow, int toCol, int toRow) {
		if((toCol > 2 && toCol < 6) && ((toRow >= 0 && toRow < 3) || (toRow > 6 && toRow < 10))) {
			if((toCol - fromCol == 1 && toRow - fromRow == 1) || (toCol - fromCol == -1 && toRow - fromRow == -1) || (toCol - fromCol == -1 && toRow - fromRow == 1) || (toCol - fromCol == 1 && toRow - fromRow == -1)) {
				if((fromCol == 4 && fromRow == 0) || (fromCol == 3 && fromRow == 1) || (fromCol == 4 && fromRow == 2) || (fromCol == 5 && fromRow == 1) || (fromCol == 4 && fromRow == 9) || (fromCol == 3 && fromRow == 8) || (fromCol == 4 && fromRow == 7) || (fromCol == 5 && fromRow == 8)) {                        
					return false;
				} else {
					return true;
				}
			} else if((toCol - fromCol == 1 && toRow == fromRow) || (toCol - fromCol == -1 && toRow == fromRow) || (toCol == fromCol && toRow - fromRow == 1) || (toCol == fromCol && toRow - fromRow == -1)) {
				return true;
			}
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
