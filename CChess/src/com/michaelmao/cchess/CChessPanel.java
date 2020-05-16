package com.michaelmao.cchess;

import java.awt.Graphics;

import javax.swing.JPanel;


public class CChessPanel extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBoard(g);
	}
	private void drawBoard(Graphics g) {
		for(int i = 0; i < 10; i++) {
			g.drawLine(30, (i+1) * 30, 270, (i+1) * 30);
		}
		
		for(int i = 0; i < 9; i++) {
			g.drawLine((i+1) * 30, 30, (i+1) * 30, 150);
			g.drawLine((i+1) * 30, 180, (i+1) * 30, 300);
		}
		
		g.drawLine(30, 5 * 30, 30, 6 * 30);
		g.drawLine(9 * 30, 5 * 30, 9 * 30, 6 * 30);
		
		g.drawLine(4 * 30, 30, 6 * 30, 3 * 30);
		g.drawLine(6 * 30, 30, 4 * 30, 3 * 30);
		g.drawLine(4 * 30, 8 * 30, 6 * 30, 10 * 30);
		g.drawLine(6 * 30, 8 * 30, 4 * 30, 10 * 30);
		
		
		drawArrow(g, 2 * 30, 3 * 30);
		drawArrow(g, 3 * 30, 4 * 30);
		drawArrow(g, 5 * 30, 4 * 30);
		drawArrow(g, 7 * 30, 4 * 30);
		drawArrow(g, 8 * 30, 3 * 30);
		
		drawArrow(g, 2 * 30, 8 * 30);
		drawArrow(g, 3 * 30, 7 * 30);
		drawArrow(g, 5 * 30, 7 * 30);
		drawArrow(g, 7 * 30, 7 * 30);
		drawArrow(g, 8 * 30, 8 * 30);
		
		drawHalfArrow(g, false, 1 * 30 , 4 * 30);
		drawHalfArrow(g, false, 1 * 30 , 7 * 30);
		
		drawHalfArrow(g, true, 9 * 30 , 4 * 30);
		drawHalfArrow(g, true, 9 * 30 , 7 * 30);
		
	}
    
	private void drawArrow(Graphics g, int x, int y) {
		drawHalfArrow(g, true, x, y);
		drawHalfArrow(g, false, x, y);
		
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
}
