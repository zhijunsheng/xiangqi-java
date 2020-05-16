package com.michaelmao.cchess;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	
	static Image img;
	
	
	static CChessBoard board = new CChessBoard();
	
	public static void main(String args[]) throws IOException {
		board.pieces.add(new CChessPiece(0, 0, false, 'R'));
		board.fillLocations();
		board.printBoard();
		
//		File imgFile = new File("bb.png");
//		
//		img = ImageIO.read(imgFile);
		
		JFrame frame = new JFrame("Chinese Chess");
		
		frame.setVisible(true);
		frame.setSize(600, 600);
		
		frame.add(new CChessPanel());
		
	}
	
	//uppercase is black
	//lowercase is red
}
