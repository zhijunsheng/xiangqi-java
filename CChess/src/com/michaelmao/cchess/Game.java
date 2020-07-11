package com.michaelmao.cchess;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	
	static CChessBoard board = new CChessBoard();
	
	static int pieceSize = 67;
	
	public static void main(String args[]) throws IOException {
		

		for(int i  = 0; i < 5; i++) {
			board.pieces.add(new CChessPiece(i * 2, 3, false, Rank.pawn, "bz"));
			board.pieces.add(new CChessPiece(i * 2, 6, true, Rank.pawn, "rz"));
		}
	
	
		for(int i = 0; i < 2; i++) {
			board.pieces.add(new CChessPiece(i * 8, 0, false, Rank.rook, "bj"));
			board.pieces.add(new CChessPiece(i * 6 + 1, 0, false, Rank.knight, "bm"));
			board.pieces.add(new CChessPiece(i * 4 + 2, 0, false, Rank.bishop, "bx"));
			board.pieces.add(new CChessPiece(i * 2 + 3, 0, false, Rank.guard, "bs"));
			board.pieces.add(new CChessPiece(i * 6 + 1, 2, false, Rank.cannon, "bp"));
			
			board.pieces.add(new CChessPiece(i * 8, 9, true, Rank.rook, "rj"));
			board.pieces.add(new CChessPiece(i * 6 + 1, 9, true, Rank.knight, "rm"));
			board.pieces.add(new CChessPiece(i * 4 + 2, 9, true, Rank.bishop, "rx"));
			board.pieces.add(new CChessPiece(i * 2 + 3, 9, true, Rank.guard, "rs"));
			board.pieces.add(new CChessPiece(i * 6 + 1, 7, true, Rank.cannon, "rp"));
		}
	
		    board.pieces.add(new CChessPiece(4, 0, false, Rank.king, "bb"));
		    board.pieces.add(new CChessPiece(4, 9, true, Rank.king, "rb"));
			System.out.println(board.toString());
			
			
			JFrame frame = new JFrame("Chinese Chess");
			
			frame.setVisible(true);
			frame.setSize(600, 800);
			
			frame.add(new CChessPanel());
			
		}
	
	static private Image imgConverter(String pngName) throws IOException{
		File file = new File("C:\\Users\\michm\\git\\xiangqi-java\\CChess\\src\\com\\michaelmao\\cchess\\" + pngName + ".png");
		return ImageIO.read(file);
//		return ImageIO.read(file).getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
	}
	

	
}
