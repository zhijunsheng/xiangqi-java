package com.michaelmao.cchess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CChessBoardTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		CChessBoard board = new CChessBoard();
		assertTrue(board.canMoveRook(0, 0, 1, 0));
		assertTrue(board.canMoveRook(0, 0, 8, 0));
		assertFalse(board.canMoveRook(0, 0, 2, 3));
	}

}
