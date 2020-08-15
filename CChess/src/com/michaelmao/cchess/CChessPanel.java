package com.michaelmao.cchess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CChessPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static int squareSize = 60;
    private static int boardX = 40;
    private static int boardY = 40;
    
    private Point fromColRow;
           
    private CChessPiece movingPiece = null;
    private Point movingPieceXY = null;
    
    // iOS: on screen we use CGFloat for coordinates (23.6, 78.0), Int for logical coordinates (3, 5)
    // Swing: int, replace logical things with (col, row), (x, y)
	
	CChessPanel() {
		addMouseListener(this);
	}
    
    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		drawPieces(g);
		this.addMouseMotionListener(this);
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
	
	private void drawImage(Graphics g, Image img, int locationCol, int locationRow) {
		g.drawImage(img, locationCol * squareSize + boardX - Game.pieceSize / 2, locationRow * squareSize + boardY - Game.pieceSize / 2, null);
	}
	
	private void drawPieces(Graphics g) {
		for (CChessPiece piece : Game.board.pieces) {
			if(movingPiece != null && piece.col == movingPiece.col && piece.row == movingPiece.row ) {
				continue;
			}
			try {
				drawImage(g, imgConverter(piece.imageName), piece.col, piece.row);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(movingPiece != null) {
			try {
				g.drawImage(imgConverter(movingPiece.imageName), movingPieceXY.x - Game.pieceSize / 2, movingPieceXY.y - Game.pieceSize / 2, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		}
		
	}
	
	static private Image imgConverter(String pngName) throws IOException{
		File file = new File("C:\\Users\\michm\\git\\xiangqi-java\\CChess\\src\\com\\michaelmao\\cchess\\" + pngName + ".png");
		return ImageIO.read(file);
//		return ImageIO.read(file).getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override 
	public void mousePressed(MouseEvent e) {
		Point mouseLocation = e.getPoint();
		fromColRow = xyToColRow(mouseLocation);
		System.out.print("From " + xyToColRow(mouseLocation) + " to ");
		for (CChessPiece piece : Game.board.pieces) {
			if(piece.col == fromColRow.x && piece.row == fromColRow.y) {
				movingPiece = new CChessPiece(piece.col, piece.row, piece.isRed, piece.rank, piece.imageName);
			}
		}
	}
	
	private Point xyToColRow(Point xy) {
		return new Point((xy.x - boardX + squareSize/2)/squareSize, (xy.y - boardY + squareSize/2)/squareSize);
//		return new Point(((int)Math.floor(xy.getX()) - boardX) / squareSize  + ((int)Math.floor(xy.getX()) > squareSize / 2 ? 1 : 0) - 1, ((int)Math.floor(xy.getY()) - boardY) / squareSize + ((int)Math.floor(xy.getY()) > squareSize / 2 ? 1 : 0) - 1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point mouseLocation = e.getPoint();
		
		Point toColRow = xyToColRow(mouseLocation);
		System.out.println(xyToColRow(mouseLocation));
		Game.board.movePiece(fromColRow.x, fromColRow.y, toColRow.x, toColRow.y, false);
		System.out.println(Game.board.toString());
		
		movingPiece = null;
		movingPieceXY = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point mouseLocation = e.getPoint();
		if(movingPiece != null) {
			movingPieceXY = new Point(mouseLocation.x, mouseLocation.y);
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
