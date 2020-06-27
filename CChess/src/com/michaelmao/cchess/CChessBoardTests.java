package com.michaelmao.cchess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CChessBoardTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRook() {
		CChessBoard board = new CChessBoard();
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 f t . . . . . . . 
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		assertTrue(board.canMoveRook(0, 0, 1, 0)); 
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 f . . . . . . . t
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		assertTrue(board.canMoveRook(0, 0, 8, 0));
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 f . . . . . . . . 
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . t . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		assertFalse(board.canMoveRook(0, 0, 2, 3));
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 f . . b . . t . . 
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		board.pieces.add(new CChessPiece(3, 0, true, Rank.rook));
		assertFalse(board.canMoveRook(0, 0, 6, 0));
	}
	
	@Test
	void testCannon() {
		CChessBoard board = new CChessBoard();
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 . . . . . . . . . 
			1 . f t . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		assertTrue(board.canMoveCannon(1, 1, 2, 1));
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 f . . b . . t . . 
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		board.pieces.add(new CChessPiece(3, 0, true, Rank.bishop));
		assertFalse(board.canMoveCannon(0, 0, 6, 0));
		
		/*
		 *    0 1 2 3 4 5 6 7 8
			0 . . . . . . . . . 
			1 . . . . . . . . . 
			2 . . . . . . . . . 
			3 . . . . . . . . . 
			4 . . . . . . . . . 
			5 . . . . . . . . . 
			6 . . . . . . . . . 
			7 . . . . . . . . . 
			8 . . . . . . . . . 
		 */
		assertFalse(board.canMoveCannon(1, 1, 2, 2));
		
//		    0 1 2 3 4 5 6 7 8
//		  0 f . b b . . . t . 
//		  1 . . . . . . . . . 
//		  2 . . . . . . . . . 
//		  3 . . . . . . . . . 
//		  4 . . . . . . . . . 
//		  5 . . . . . . . . . 
//		  6 . . . . . . . . . 
//		  7 . . . . . . . . . 
//		  8 . . . . . . . . . 
//		  9 . . . . . . . . . 
		
	}
	
	@Test
	void testCannonBlocking() {
		CChessBoard board = new CChessBoard();
//  	0 1 2 3 4 5 6 7 8
//	  0 f . b b . . . t . 
//	  1 . . . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		
	    board.pieces.add(new CChessPiece(0, 0, false, Rank.cannon));
		board.pieces.add(new CChessPiece(2, 0, true, Rank.rook));
		board.pieces.add(new CChessPiece(3, 0, true, Rank.rook));
		board.pieces.add(new CChessPiece(7, 0, true, Rank.rook));
		assertFalse(board.canMoveCannon(0, 0, 7, 0));
		
		assertTrue(board.canMoveCannon(0, 0, 3, 0));
		
		
	}
	
	@Test
	void testCannonCapturing() {
		CChessBoard board = new CChessBoard();
//  	0 1 2 3 4 5 6 7 8
//	  0 f . b b . . . t . 
//	  1 . . . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		
	    board.pieces.add(new CChessPiece(0, 0, true, Rank.cannon));
		board.pieces.add(new CChessPiece(2, 0, true, Rank.rook));
		board.pieces.add(new CChessPiece(3, 0, true, Rank.rook));
		board.pieces.add(new CChessPiece(7, 0, true, Rank.rook));
		assertFalse(board.canMoveCannon(0, 0, 7, 0));
		
		assertFalse(board.canMoveCannon(0, 0, 3, 0));
	}



	@Test
	void testBisop() {
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMoveBishop(0, 0, 2, 2));
		assertTrue(board.canMoveBishop(1, 2, 3, 4));
		assertTrue(board.canMoveBishop(3, 3, 1, 1));
	}
	
	@Test
	void testGuard() {
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMoveGuard(3, 0, 4, 1));
		assertTrue(board.canMoveGuard(3, 0, 4, 1));
	}
	
	@Test
	void testPawn() {
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMovePawn(0, 3, 0, 4, false));
		assertFalse(board.canMovePawn(0, 1, 1, 1, false));
	}
	
	@Test
	void testKnight() {
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMoveKnight(0, 0, 2, 1));
		assertTrue(board.canMoveKnight(3, 3, 5, 2));
		assertTrue(board.canMoveKnight(3, 3, 1, 4));
		assertFalse(board.canMoveKnight(1, 1, 2, 7));
	}
	
	@Test
	void testKing() {
		CChessBoard board = new CChessBoard();
		assertFalse(board.canMoveKing(4, 0, 3, 1));
		assertFalse(board.canMoveKing(3, 1, 4, 3));
		assertTrue(board.canMoveKing(4, 0, 4, 1));
	}
	
	@Test
	void testPiecesBetween() {
		CChessBoard board = new CChessBoard();
	}
}
