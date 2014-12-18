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
package de.ovgu.serpentchess.presenter.chessboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.GrayPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.Pawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import de.ovgu.serpentchess.presenter.Move;

public class ChessboardPresenterMainTest extends ChessboardPresenterTest {

	@Test
	public void testFieldCount() {
		assertEquals(126, chessboardPresenter.chessboard.getFieldCount());
	}

	@Test
	public void testEmptyReachableFields() {
		Field testField = new Field(1, 1);
		assertTrue(chessboardPresenter.getReachableFields(testField).isEmpty());
		putPiece(testField, new WhitePawn());
		assertFalse(chessboardPresenter.getReachableFields(testField).isEmpty());
		chessboardPresenter.chessboard.clearPieces();
		assertTrue(chessboardPresenter.getReachableFields(testField).isEmpty());
	}

	@Test
	public void testGetSelectedField() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		putPiece(testField, king);
		chessboardPresenter.selectPiece(testField);
		assertEquals(testField, chessboardPresenter.getSelectedField());
	}

	@Test
	public void testGetSelectedPiece() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		putPiece(testField, king);
		chessboardPresenter.selectPiece(testField);
		assertEquals(king, chessboardPresenter.getSelectedPiece());
	}

	@Test
	public void testSelectPiece() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		putPiece(testField, king);
		assertTrue(chessboardPresenter.selectPiece(testField));
	}

	@Test
	public void testHighlightedFields() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		putPiece(testField, king);
		chessboardPresenter.selectPiece(testField);
	}

	@Test
	public void testSelectNoPiece() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		Field testField2 = new Field(5, 4);
		putPiece(testField, king);
		assertFalse(chessboardPresenter.selectPiece(testField2));
	}

	@Test
	public void testNoHighlightedFields() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		Field testField2 = new Field(5, 4);
		putPiece(testField, king);
		chessboardPresenter.selectPiece(testField2);
	}

	@Test
	public void testMovePiece() {
		Piece king = new King(PieceColor.WHITE);
		Field source = new Field(5, 7);
		Field destiantion = new Field(5, 8);
		putPiece(source, king);
		chessboardPresenter.selectPiece(source);
		chessboardPresenter.movePiece(new Move(source, king, destiantion));
		assertEquals(king, chessboardPresenter.getPiece(destiantion));
		assertNull(chessboardPresenter.getPiece(source));
		assertNull(chessboardPresenter.getSelectedPiece());
		assertNull(chessboardPresenter.getSelectedField());
	}

	@Test
	public void testUndoMoveTakenPiece() {
		Piece king = new King(PieceColor.WHITE);
		Piece pawn = new GrayPawn();
		Field source = new Field(5, 7);
		Field destiantion = new Field(5, 8);
		putPiece(source, king);
		putPiece(destiantion, pawn);
		Move move = new Move(source, king, destiantion, chessboardPresenter.getPiece(destiantion));
		chessboardPresenter.movePiece(move);
		chessboardPresenter.undoMove(move);
		assertEquals(king, chessboardPresenter.getPiece(source));
		assertEquals(pawn, chessboardPresenter.getPiece(destiantion));
	}

	@Test
	public void testUndoMovePiece() {
		Piece king = new King(PieceColor.WHITE);
		Field source = new Field(5, 7);
		Field destiantion = new Field(5, 8);
		putPiece(source, king);
		Move move = new Move(source, king, destiantion);
		chessboardPresenter.movePiece(move);
		chessboardPresenter.undoMove(move);
		assertEquals(king, chessboardPresenter.getPiece(source));
		assertNull(chessboardPresenter.getPiece(destiantion));
	}

	@Test
	public void testMovePieceToNotReachableField() {
		Piece king = new King(PieceColor.WHITE);
		Field source = new Field(5, 7);
		Field destiantion = new Field(1, 8);
		putPiece(source, king);
		chessboardPresenter.selectPiece(source);
		chessboardPresenter.movePiece(new Move(source, king, destiantion));
		assertEquals(king, chessboardPresenter.getPiece(source));
		assertEquals(null, chessboardPresenter.getPiece(destiantion));
		assertEquals(king, chessboardPresenter.getSelectedPiece());
		assertEquals(source, chessboardPresenter.getSelectedField());
	}

	@Test
	public void testPutPiece() {
		Piece king = new King(PieceColor.WHITE);
		Field testField = new Field(5, 7);
		chessboardPresenter.putPiece(testField, king);
		assertEquals(king, chessboardPresenter.getPiece(testField));
		assertEquals(1, chessboardPresenter.chessboard.getPieceCount());
	}

	@Test
	public void testGetView() {
		assertEquals(chessboardViewMock, chessboardPresenter.getView());
	}

	@Test
	public void testCanMove() {
		chessboardPresenter.putPiece(new Field(1, 1), new King(PieceColor.WHITE));
		chessboardPresenter.putPiece(new Field(8, 8), new King(PieceColor.GRAY));
		chessboardPresenter.putPiece(new Field(3, 3), new Queen(PieceColor.GRAY));
		assertFalse(chessboardPresenter.canMove(PieceColor.WHITE));
		assertTrue(chessboardPresenter.canMove(PieceColor.GRAY));
		assertFalse(chessboardPresenter.canMove(PieceColor.BLACK));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPawnTest() {
		for (PieceColor color : PieceColor.values()) {
			Pawn pawn = chessboardPresenter.createPawn(color);
			assertTrue(pawn.getColor() == color);
		}
		chessboardPresenter.createPawn(PieceColor.valueOf(""));
		// Test invalid input
	}

	@Test
	public void testGetCurrentPlayerColor() {
		ChessboardPresenter cp = new ChessboardPresenter(chessboardViewMock, eventBusMock);
		cp.initPlayers();
		assertEquals(PieceColor.WHITE, cp.getCurrentPlayerColor());
	}

	@Test
	public void testSetNextPlayer() {
		ChessboardPresenter cp = new ChessboardPresenter(chessboardViewMock, eventBusMock);
		cp.initPlayers();
		cp.setNextPlayer();
		assertEquals(PieceColor.GRAY, cp.getCurrentPlayerColor());
	}

	@Test
	public void testSetPreviousPlayer() {
		ChessboardPresenter cp = new ChessboardPresenter(chessboardViewMock, eventBusMock);
		cp.initPlayers();
		cp.setNextPlayer();
		cp.setPreviousPlayer();
		assertEquals(PieceColor.WHITE, cp.getCurrentPlayerColor());
	}

}
