package com.michaelmao.cchess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

enum MoveDirection {
	left, right, up, down;
}

public class CChessBoard {
//	List<CChessPiece> pieces = new ArrayList<CChessPiece>();
	Set<CChessPiece> pieces = new HashSet<>();

	String desc = "";
	
	public void movePiece(int col, int row, int newCol, int newRow) {
		CChessPiece movingPiece = pieceAt(col, row);
		
		if(movingPiece == null) {
			return;
		}
		
		if(canMovePiece(col, row, newCol, newRow, movingPiece.isRed)) {
			pieces.remove(movingPiece);
			pieces.add(new CChessPiece(newCol, newRow, movingPiece.isRed, movingPiece.rank));
			System.out.println("Piece moved");
		}
	}
	
	public boolean canMovePiece(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
		CChessPiece movingPiece = pieceAt(fromCol, fromRow);
		switch (movingPiece.rank) {
		case rook:
			return canMoveRook(fromCol, fromRow, toCol, toRow, isRed);
		case knight:
		case bishop:
			break;
		case guard:
			break;
		case king:
			break;
		case pawn:
			break;
		case cannon:
			break;
		}
		return false;
	}
	
	public boolean canMoveRook(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
		for (CChessPiece piece : pieces) {
			if(piece.col == toCol && piece.row == toRow && piece.isRed == isRed) {
				return false;
			}
		}
		
		String direction = "";
		if(fromCol > toCol && fromRow == toRow) {
			direction = "left";
		} else if(toCol > fromCol && fromRow == toRow) {
			direction = "right";
		} else if(fromRow > toRow && fromCol == toCol) {
			direction = "up";
		} else if(toRow > fromRow && fromCol == toCol) {
			direction = "down";
		}
		
		
		if(direction != "") {
			if(piecesBetween(direction, fromCol, fromRow, toCol, toRow) == 0) {
				return true;
			}
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
	
	public boolean canMoveCannon(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
		for (CChessPiece piece : pieces) {
			if(piece.col == toCol && piece.row == toRow && piece.isRed == isRed) {
				return false;
			}
		}
		
		String direction = "";
		if(fromCol > toCol && fromRow == toRow) {
			direction = "left";
		} else if(toCol > fromCol && fromRow == toRow) {
			direction = "right";
		} else if(fromRow > toRow && fromCol == toCol) {
			direction = "up";
		} else if(toRow > fromRow && fromCol == toCol) {
			direction = "down";
		}
		
		if(checkCapture(toCol, toRow, isRed)) {
			if(piecesBetween(direction, fromCol, fromRow, toCol, toRow) == 1) { 
				return true;
			}
		} else if(direction != "") {
			if(piecesBetween(direction, fromCol, fromRow, toCol, toRow) == 0) {
				return true;
			}
		}
			
		return false;
		
	}
	
	int piecesBetween(String direction, int fromCol, int fromRow, int toCol, int toRow) {
		int piecesBetween = 0;
		
		for (CChessPiece piece : pieces) {
			int pieceX = piece.col;
			int pieceY = piece.row;
			if(direction == "left") {
				if((pieceX > toCol && pieceX < fromCol) && pieceY == toRow) {
					piecesBetween++;
				}
			} else if(direction == "right") {
				if((pieceX < toCol && pieceX > fromCol) && pieceY == toRow) {
					piecesBetween++;
				}
			} else if(direction == "up") {
				if((pieceY < fromRow && pieceY > toRow) && pieceX == toCol) {
					piecesBetween++;
				}
			} else if(direction == "down") {
				if((pieceY > fromRow && pieceY < toRow) && pieceX == toCol) {
					piecesBetween++;
				}
			}
		}

		return piecesBetween;
	}
	
	
	
	boolean checkCapture(int toCol, int toRow, boolean isRed) {
		for (CChessPiece piece : pieces) {
			if(piece.col == toCol && piece.row == toRow) {
				if(isRed && !piece.isRed) {
					return true;
				} else if(!isRed && piece.isRed) {
					return true;
				}
			}
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
		
		String direction = "";
		
		if(fromCol - toCol == 2 && fromRow - toRow == 1) {
			direction = "left";
		} else if(fromRow - toRow == 2 && fromCol - toCol == 1){
			direction = "up";
		} else if(toCol - fromCol == 2 && fromRow - toRow == 1) {
			direction = "right";
		} else if(fromRow - toRow == 2 && toCol - fromCol == 1){
			direction = "up";
		} else if(toCol - fromCol == 2 && toRow - fromRow == 1) {
			direction = "right";
		} else if(toRow - fromRow == 2 && toCol - fromCol == 1){
			direction = "down";
		} else if((fromCol - toCol == 2 && toRow - fromRow == 1)) {
			direction = "left";
		} else if(toRow - fromRow == 2 && fromCol - toCol == 1) {
			direction = "down";
		}
		
		if(direction != "") {
			if(checkBlockingKnight(direction, fromCol, fromRow)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkBlockingKnight(String direction, int locationX, int locationY) {
		int blockX = 0;
		int blockY = 0;
		
		if(direction == "left") {
			blockX = locationX - 1;
			blockY = locationY;
		} else if(direction == "right") {
			blockX = locationX + 1;
			blockY = locationY;
		} else if(direction == "up") {
			blockX = locationX;
			blockY = locationY - 1;
		} else if(direction == "down") {
			blockX = locationX;
			blockY = locationY + 1;
		}
		
		for(CChessPiece piece: pieces) {
			if(piece.col == blockX && piece.row == blockY) {
				return true;
			}
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
	
	@Override
	public String toString() {
		desc = "";
		
		desc += "  ";
		
		for(int col = 0; col < 9; col++) {
			if(col == 8) {
				desc += (col) + "\n";
			} else {
				desc += (col) + " ";
			}
		}
		
		for(int row = 0; row < 10; row++) {
			desc += row + " ";
			for(int col = 0; col < 9; col++) {
				
				CChessPiece piece = pieceAt(col, row);
				if(piece == null) {
					desc += ". ";
				} else {
					switch (piece.rank) {
					
					case rook:
						desc += piece.isRed ? "R" : "r";
						break;
					case knight:
						desc += piece.isRed ? "N" : "n";
						break;
					case bishop:
						desc += piece.isRed ? "B" : "b";
						break;
					case guard:
						desc += piece.isRed ? "G" : "g";
						break;
					case king:
						desc += piece.isRed ? "K" : "k";
						break;
					case pawn:
						desc += piece.isRed ? "P" : "p";
						break;
					case cannon:
						desc += piece.isRed ? "C" : "c";
						break;
					}
				}
				if(col == 8) {
					desc += "\n";
				}
				
			}
		}
		return desc;
	}
	
	
	
	CChessPiece pieceAt(int locationCol, int locationRow) { 
		for (CChessPiece piece : pieces) {
			if(locationCol == piece.col && locationRow == piece.row) {
				return piece;
			}
		}
		
		return null;
	}
	
	
}
