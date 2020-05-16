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
		
		
		assertTrue(board.canMoveRook(0, 0, 1, 0));
		assertTrue(board.canMoveRook(0, 0, 8, 0));
		assertFalse(board.canMoveRook(0, 0, 2, 3));



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
}
