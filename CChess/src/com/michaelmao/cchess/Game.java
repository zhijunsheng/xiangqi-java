package com.michaelmao.cchess;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	
	static Image redRookImg;
	static Image redKnightImg;
	static Image redBishopImg;
	static Image redGuardImg;
	static Image redKingImg;
	static Image redPawnImg;
	static Image redCannonImg;
	
	static Image blackRookImg;
	static Image blackKnightImg;
	static Image blackBishopImg;
	static Image blackGuardImg;
	static Image blackKingImg;
	static Image blackPawnImg;
	static Image blackCannonImg;
	
	
	
	
	static CChessBoard board = new CChessBoard();
	
	static int pieceSize = 67;
	
	public static void main(String args[]) throws IOException {
		board.pieces.add(new CChessPiece(0, 0, true, Rank.rook));
		System.out.println(board.toString());
		
		
		redRookImg = imgConverter("rj");
		redKnightImg = imgConverter("rm");
		redBishopImg = imgConverter("rx");
		redGuardImg = imgConverter("rs");
		redKingImg = imgConverter("rb");
		redPawnImg = imgConverter("rz");
		redCannonImg = imgConverter("rp");
		
		blackRookImg = imgConverter("bj");
		blackKnightImg = imgConverter("bm");
		blackBishopImg = imgConverter("bx");
		blackGuardImg = imgConverter("bs");
		blackKingImg = imgConverter("bb");
		blackPawnImg = imgConverter("bz");
		blackCannonImg = imgConverter("bp");
		
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
