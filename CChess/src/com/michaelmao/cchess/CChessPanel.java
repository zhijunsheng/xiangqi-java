package com.michaelmao.cchess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;


public class CChessPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private static int squareSize = 60;
    private static int boardX = 40;
    private static int boardY = 40;
	
	CChessPanel() {
		addMouseListener(this);
	}
    
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
		
		for (int i = 0; i < 4; i++) {
			drawHalfArrow(g, false, i * 2, 3);
			drawHalfArrow(g, false, i * 2, 6);
			if(i == 0 || i == 1) {
				drawHalfArrow(g, false, i * 6 + 1, 2);
				drawHalfArrow(g, false, i * 6 + 1, 7);
			}
		}
		
		for (int i = 4; i > 0 ; i--) {
			drawHalfArrow(g, true, i * 2, 3);
			drawHalfArrow(g, true, i * 2, 6);
			if(i == 1 || i == 2) {
				drawHalfArrow(g, true, (i - 1) * 6 + 1, 2);
				drawHalfArrow(g, true, (i - 1) * 6 + 1, 7);
			}
		}
		
	}
	
	private void drawHalfArrow(Graphics g, Boolean isLeft, int x, int y) {
		int actualX = x * squareSize + boardX;
		int actualY = y * squareSize + boardY;
		
		int size = squareSize / 15;
		if(isLeft) {
			//top
			g.drawLine(actualX-size, actualY-size, actualX-size * 4, actualY-size);
			g.drawLine(actualX-size, actualY-size, actualX-size, actualY-size * 4);
			//bottom
			g.drawLine(actualX-size, actualY+size, actualX-size * 4, actualY+size);
			g.drawLine(actualX-size, actualY+size, actualX-size, actualY+size * 4);
			
		} else {
			
			//top
			g.drawLine(actualX+size, actualY-size, actualX+size * 4, actualY-size);
			g.drawLine(actualX+size, actualY-size, actualX+size, actualY-size * 4);
			//bottom
			g.drawLine(actualX+size, actualY+size, actualX+size * 4, actualY+size);
			g.drawLine(actualX+size, actualY+size, actualX+size, actualY+size * 4);
		}
	}
	
	private void drawImage(Graphics g, Image img, int locationX, int locationY) {
		g.drawImage(img, locationX * squareSize + boardX - Game.pieceSize / 2, locationY * squareSize + boardY - Game.pieceSize / 2, null);
	}
	
	private void drawPieces(Graphics g) {
	
		for(int i  = 0; i < 5; i++) {
			drawImage(g, Game.blackPawnImg, i * 2, 3);
			drawImage(g, Game.redPawnImg, i * 2, 6);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point mouseLocation = e.getPoint();
		int pieceX = (int)Math.round(mouseLocation.getX());
		int pieceY = (int)Math.round(mouseLocation.getY());
		
		
		System.out.print(gameLocation(true, pieceX));
		System.out.print(gameLocation(false, pieceY));
	}
	
	public int gameLocation(boolean isX, int location) {
		int gameValue = 0;
		int remainder = 0;
		int boardMargin = 0;
		if(isX) {
			boardMargin = boardX;
		} else {
			boardMargin = boardY;
		}
		
		remainder = (location - boardMargin) % squareSize;
		gameValue = (int)Math.floor((location - boardMargin) / squareSize);
		if(remainder > squareSize / 2) {
			gameValue++;
		}
		
		return gameValue;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
