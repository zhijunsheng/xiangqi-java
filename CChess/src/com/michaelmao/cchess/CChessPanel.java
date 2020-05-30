package com.michaelmao.cchess;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.JPanel;


public class CChessPanel extends JPanel{
	
    private static int squareSize = 60;
    private static int boardX = 40;
    private static int boardY = 40;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBoard(g);
		
		drawPieces(g);
		
		
	}
	private void drawBoard(Graphics g) {
		for(int i = 0; i < 10; i++) {
			g.drawLine(boardX, i * squareSize + boardY, 8 * squareSize + boardX, i * squareSize + boardY);
		}
		
		for(int i = 0; i < 9; i++) {
			g.drawLine(i * squareSize + boardX, boardY, i * squareSize + boardX, 4 * squareSize + boardX);
			g.drawLine(i * squareSize + boardX, 5 * squareSize + boardY, i * squareSize + boardX, 9 * squareSize + boardY);
		}
		

		
		g.drawLine(boardX, 4 * squareSize + boardY, boardX, 5 * squareSize + boardY);
		g.drawLine(8 * squareSize + boardX, 4 * squareSize + boardY, 8 * squareSize + boardX, 5 * squareSize + boardY);
		
		g.drawLine(3 * squareSize + boardX, boardY, 5 * squareSize + boardX, 2 * squareSize + boardY);
		g.drawLine(5 * squareSize + boardX, boardY,  3 * squareSize + boardX, 2 * squareSize + boardY);
		g.drawLine(3 * squareSize + boardX, 7 * squareSize + boardY, 5 * squareSize + boardX, 9 * squareSize + boardY);
		g.drawLine(5 * squareSize + boardX, 7 * squareSize + boardY, 3 * squareSize + boardX, 9 * squareSize + boardY);
		
		
		drawArrow(g, 1, 2, squareSize, boardX, boardY);
		drawArrow(g, 7, 2, squareSize, boardX, boardY);
		drawArrow(g, 2, 3, squareSize, boardX, boardY);
		drawArrow(g, 4, 3, squareSize, boardX, boardY);
		drawArrow(g, 6, 3, squareSize, boardX, boardY);
		
		drawArrow(g, 2, 6, squareSize, boardX, boardY);
		drawArrow(g, 4, 6, squareSize, boardX, boardY);
		drawArrow(g, 6, 6, squareSize, boardX, boardY);
		drawArrow(g, 1, 7, squareSize, boardX, boardY);
		drawArrow(g, 7, 7, squareSize, boardX, boardY);
		
		drawHalfArrow(g, true, 8 * squareSize + boardX, 3 * squareSize + boardY);
		drawHalfArrow(g, false, 0 * squareSize + boardX, 3 * squareSize + boardY);
		
		drawHalfArrow(g, true, 8 * squareSize + boardX, 6 * squareSize + boardY);
		drawHalfArrow(g, false, 0 * squareSize + boardX, 6 * squareSize + boardY);
		
	}
    
	private void drawArrow(Graphics g, int squaresX, int squaresY, int squareSize, int boardX, int boardY) {
		int actualX = squaresX * squareSize + boardX;
		int actualY = squaresY * squareSize + boardY;
		drawHalfArrow(g, true, actualX, actualY);
		drawHalfArrow(g, false, actualX, actualY);
		
	}
	
	private void drawHalfArrow(Graphics g, Boolean isLeft, int x, int y) {
		if(isLeft) {
			//top
			g.drawLine(x-3, y-3, x-12, y-3);
			g.drawLine(x-3, y-3, x-3, y-12);
			//bottom
			g.drawLine(x-3, y+3, x-12, y+3);
			g.drawLine(x-3, y+3, x-3, y+12);
			
		} else {
			
			//top
			g.drawLine(x+3, y-3, x+12, y-3);
			g.drawLine(x+3, y-3, x+3, y-12);
			//bottom
			g.drawLine(x+3, y+3, x+12, y+3);
			g.drawLine(x+3, y+3, x+3, y+12);
		}
	}
	
	private void drawImage(Graphics g, Image img, int locationX, int locationY) {
		g.drawImage(img, locationX * squareSize + boardX - Game.pieceSize / 2, locationY * squareSize + boardY - Game.pieceSize / 2, null);
	}
	
	private void drawPieces(Graphics g) {
	
		for(int i  = 0; i < 10; i+=2) {
			drawImage(g, Game.blackPawnImg, i, 3);
			drawImage(g, Game.redPawnImg, i, 6);
		}
		
		for(int i = 0; i < 2; i++) {
			drawImage(g, Game.blackRookImg, i * 8, 0);
			drawImage(g, Game.blackKnightImg, i * 6 + 1, 0);
			drawImage(g, Game.blackBishopImg, i * 4 + 2, 0);
			drawImage(g, Game.blackGuardImg, i * 2 + 3, 0);
			drawImage(g, Game.blackCannonImg, i * 6 + 1, 2);
			
			drawImage(g, Game.redRookImg, i * 8, 9);
			drawImage(g, Game.redKnightImg, i * 6 + 1, 9);
			drawImage(g, Game.redBishopImg, i * 4 + 2, 9);
			drawImage(g, Game.redGuardImg, i * 2 + 3, 9);
			drawImage(g, Game.redCannonImg, i * 6 + 1, 7);

		}
		
		drawImage(g, Game.blackKingImg, 4, 0);
		drawImage(g, Game.redKingImg, 4, 9);
	}
}
