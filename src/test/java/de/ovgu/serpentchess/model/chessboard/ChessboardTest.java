/**
 * Copyright (C) 2013 Dariusz Krolikowski, Anna Geringer, Jens Meinicke
 *
 * This file is part of SerpentChess.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.serpentchess.model.chessboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;

public class ChessboardTest {

	private static final Field TEST_FIELD = new Field(2, 4);
	private static final Piece TEST_PIECE = new King(PieceColor.WHITE);
	private Chessboard chessboard;

	private void createTestField() {
		final int x = TEST_FIELD.getX();
		final int y = TEST_FIELD.getY();
		chessboard.createField(x, y);
	}

	@Before
	public void setUp() {
		chessboard = new Chessboard();
	}

	@Test
	public void testGetField() {
		createTestField();
		assertEquals(TEST_FIELD, chessboard.getField(TEST_FIELD.getX(), TEST_FIELD.getY()));
	}

	@Test(expected = IllegalStateException.class)
	public void testPutPieceOnNonExistingField() {
		chessboard.putPiece(TEST_FIELD, TEST_PIECE);
	}

	@Test
	public void testGetPiece() {
		createTestField();
		chessboard.putPiece(TEST_FIELD, TEST_PIECE);
		assertEquals(TEST_PIECE, chessboard.getPiece(TEST_FIELD));
	}

	@Test
	public void testClearPieces() {
		createTestField();
		chessboard.putPiece(TEST_FIELD, TEST_PIECE);
		assertNotNull(chessboard.getPiece(TEST_FIELD));
		chessboard.clearPieces();
		assertNull(chessboard.getPiece(TEST_FIELD));
	}

	@Test
	public void testPieceCount() {
		chessboard.createField(1, 2);
		chessboard.createField(3, 4);
		chessboard.createField(5, 6);
		chessboard.createField(7, 8);
		assertEquals(0, chessboard.getPieceCount());

		chessboard.putPiece(chessboard.getField(1, 2), new King(PieceColor.WHITE));
		assertEquals(1, chessboard.getPieceCount());

		chessboard.putPiece(chessboard.getField(3, 4), new King(PieceColor.BLACK));
		chessboard.putPiece(chessboard.getField(5, 6), new King(PieceColor.GRAY));
		assertEquals(3, chessboard.getPieceCount());
	}

	@Test
	public void testEqualPieceCount() {
		chessboard.createField(1, 2);
		chessboard.createField(3, 4);
		chessboard.putPiece(chessboard.getField(1, 2), TEST_PIECE);
		chessboard.putPiece(chessboard.getField(3, 4), TEST_PIECE);
		assertEquals(2, chessboard.getPieceCount());
	}

	@Test
	public void testFieldCount() {
		assertEquals(0, chessboard.getFieldCount());
		chessboard.createField(1, 2);
		assertEquals(1, chessboard.getFieldCount());
		chessboard.createField(3, 4);
		assertEquals(2, chessboard.getFieldCount());
	}

	@Test
	public void testRemovePiece() {
		createTestField();
		chessboard.putPiece(TEST_FIELD, TEST_PIECE);
		assertEquals(1, chessboard.getPieceCount());
		chessboard.removePiece(TEST_FIELD);
		assertEquals(0, chessboard.getPieceCount());
	}

	@Test
	public void testContainsPiece() {
		createTestField();
		assertFalse(chessboard.containsPiece(TEST_PIECE));
		chessboard.putPiece(TEST_FIELD, TEST_PIECE);
		assertTrue(chessboard.containsPiece(TEST_PIECE));
		chessboard.removePiece(TEST_FIELD);
		assertFalse(chessboard.containsPiece(TEST_PIECE));
	}

}
