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
		assertTrue(board.canMoveRook(0, 0, 1, 0, true)); 
	}
	
	@Test 
	void testRook2() {
		CChessBoard board = new CChessBoard();
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
		assertTrue(board.canMoveRook(0, 0, 8, 0, true));
	}
	
	@Test 
	void testRook3() {
		CChessBoard board = new CChessBoard();
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
		assertFalse(board.canMoveRook(0, 0, 2, 3, true));
	}
	
	@Test 
	void testRookBlocking() {
		CChessBoard board = new CChessBoard();
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
		assertFalse(board.canMoveRook(0, 0, 6, 0, true));
	}
	
	
	@Test
	void testCannonMovement() {
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
		assertTrue(board.canMoveCannon(1, 1, 2, 1, true));
		
		
	}
	
	@Test
	void testCannonMovement2() {
		CChessBoard board = new CChessBoard();
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
		assertFalse(board.canMoveCannon(0, 0, 6, 0, true));
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
		assertFalse(board.canMoveCannon(0, 0, 7, 0, true));
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
		assertFalse(board.canMoveCannon(0, 0, 7, 0, false));
	}
	
	@Test
	void testCannonCapturing2() {
		CChessBoard board = new CChessBoard();
//  	0 1 2 3 4 5 6 7 8
//	  0 F . b B . . . t . 
//	  1 . . . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		
		board.pieces.add(new CChessPiece(2, 0, false, Rank.bishop));
		board.pieces.add(new CChessPiece(3, 0, true, Rank.bishop));
		assertFalse(board.canMoveCannon(0, 0, 3, 0, true));
		
	}



	@Test
	void testBishop() {
		CChessBoard board = new CChessBoard();
//		    0 1 2 3 4 5 6 7 8
//		  0 f . . . . . . . . 
//		  1 . . . . . . . . . 
//		  2 . . t . . . . . . 
//		  3 . . . . . . . . . 
//		  4 . . . . . . . . . 
//		  5 . . . . . . . . . 
//		  6 . . . . . . . . . 
//		  7 . . . . . . . . . 
//		  8 . . . . . . . . . 
//		  9 . . . . . . . . . 

		assertTrue(board.canMoveBishop(0, 0, 2, 2));
	}
	
	@Test
	void testBishop2() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . . . . . . . 
//	  2 . f . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . t . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveBishop(1, 2, 3, 4));
	}
	
	@Test
	void testBishop3() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . t . . . . . . . 
//	  1 . . . . . . . . . 
//	  2 . . . f . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveBishop(3, 3, 1, 1));
	}
	
	@Test
	void testGuard() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . . f . . . . 
//	  2 . . . . . t . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveGuard(4, 1, 5, 2));
	}
	
	@Test
	void testGuard2() {
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . f . . . . . 
//	  1 . . . . 1 . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMoveGuard(3, 0, 4, 1));
	}
	
	
	@Test
	void testPawn() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 f . . . . . . . . 
//	  4 t . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMovePawn(0, 3, 0, 4, false));
	}
	
	@Test
	void testPawn2() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 f t . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertFalse(board.canMovePawn(0, 1, 1, 1, false));
	}
	
	@Test
	void testKnight() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 f . . . . . . . . 
//	  1 . . t . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveKnight(0, 0, 2, 1));

	}
	
	@Test
	void testKnight2() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . . . . . . . 
//	  2 . . . . . t . . . 
//	  3 . . . f . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveKnight(3, 3, 5, 2));
	}
	
	@Test
	void testKnight3() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . . t . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . f . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveKnight(3, 3, 1, 4));
	}
	
	@Test
	void testKnight4() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . f . . . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . t . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertFalse(board.canMoveKnight(1, 1, 2, 7));
	}
	
	@Test
	void testKing() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . f . . . . 
//	  1 . . . t . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertFalse(board.canMoveKing(4, 0, 3, 1));
	}
	
	@Test
	void testKing2() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . . . . . . 
//	  1 . . . f . . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . t . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertFalse(board.canMoveKing(3, 1, 4, 3));
	}
	
	@Test
	void testKing3() {
		CChessBoard board = new CChessBoard();
//	    0 1 2 3 4 5 6 7 8
//	  0 . . . . f . . . . 
//	  1 . . . . t . . . . 
//	  2 . . . . . . . . . 
//	  3 . . . . . . . . . 
//	  4 . . . . . . . . . 
//	  5 . . . . . . . . . 
//	  6 . . . . . . . . . 
//	  7 . . . . . . . . . 
//	  8 . . . . . . . . . 
//	  9 . . . . . . . . . 
		assertTrue(board.canMoveKing(4, 0, 4, 1));
	}
	
	
	
}
