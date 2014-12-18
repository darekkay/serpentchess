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
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.Bishop;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Knight;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.BlackPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.GrayPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;

/**
 * Testing reachable fields when only a single piece is on the chessboard.
 */
public class SinglePieceReachableFieldsTest extends ChessboardPresenterTest {

	@Test
	public void testEmptyFieldReachableFields() {
		assertTrue(chessboardPresenter.getReachableFields(new Field(5, 7)).isEmpty());
	}

	@Test
	public void testKingMiddleReachableFields() {
		Field field = new Field(5, 7); // E7
		putPiece(field, new King(PieceColor.WHITE));

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(4, 7));
		reachableFields.add(new Field(4, 6));
		reachableFields.add(new Field(5, 6));
		reachableFields.add(new Field(6, 7));
		reachableFields.add(new Field(6, 8));
		reachableFields.add(new Field(5, 8));
		reachableFields.add(new Field(4, 8));
		reachableFields.add(new Field(3, 6));
		reachableFields.add(new Field(4, 5));
		reachableFields.add(new Field(6, 6));
		reachableFields.add(new Field(7, 8));
		reachableFields.add(new Field(6, 9));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testKingCornerReachableFields() {
		Field field = new Field(1, 1); // A1
		putPiece(field, new King(PieceColor.WHITE));

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(1, 2));
		reachableFields.add(new Field(2, 1));
		reachableFields.add(new Field(2, 2));
		reachableFields.add(new Field(2, 3));
		reachableFields.add(new Field(3, 2));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testBishopReachableFields() {
		Field field = new Field(2, 7); // B7
		putPiece(field, new Bishop(PieceColor.WHITE));

		Set<Field> reachableFields = chessboardPresenter.getReachableFields(field);

		assertEquals(14, reachableFields.size());

		// check the ends of each direction
		assertTrue(reachableFields.contains(new Field(1, 5)));
		assertTrue(reachableFields.contains(new Field(1, 8)));
		assertTrue(reachableFields.contains(new Field(4, 11)));
		assertTrue(reachableFields.contains(new Field(7, 2)));
		assertTrue(reachableFields.contains(new Field(12, 12)));
	}

	@Test
	public void testRookReachableFields() {
		Field field = new Field(2, 7); // B7
		putPiece(field, new Rook(PieceColor.WHITE));

		Set<Field> reachableFields = chessboardPresenter.getReachableFields(field);

		assertEquals(26, reachableFields.size());

		// check the ends of each direction
		assertTrue(reachableFields.contains(new Field(1, 7)));
		assertTrue(reachableFields.contains(new Field(12, 7)));
		assertTrue(reachableFields.contains(new Field(1, 6)));
		assertTrue(reachableFields.contains(new Field(8, 13)));
		assertTrue(reachableFields.contains(new Field(2, 1)));
		assertTrue(reachableFields.contains(new Field(2, 9)));
	}

	@Test
	public void testQueenReachableFields() {
		Field field = new Field(2, 7); // B7
		putPiece(field, new Queen(PieceColor.WHITE));

		Set<Field> reachableFields = chessboardPresenter.getReachableFields(field);

		assertEquals(40, reachableFields.size());

		// check the ends of each straight direction
		assertTrue(reachableFields.contains(new Field(1, 7)));
		assertTrue(reachableFields.contains(new Field(12, 7)));
		assertTrue(reachableFields.contains(new Field(1, 6)));
		assertTrue(reachableFields.contains(new Field(8, 13)));
		assertTrue(reachableFields.contains(new Field(2, 1)));
		assertTrue(reachableFields.contains(new Field(2, 9)));

		// check the ends of each diagonal direction
		assertTrue(reachableFields.contains(new Field(1, 5)));
		assertTrue(reachableFields.contains(new Field(1, 8)));
		assertTrue(reachableFields.contains(new Field(4, 11)));
		assertTrue(reachableFields.contains(new Field(7, 2)));
		assertTrue(reachableFields.contains(new Field(12, 12)));
	}

	@Test
	public void testKnightReachableFields() {
		Field field = new Field(2, 7); // B7
		putPiece(field, new Knight(PieceColor.WHITE));

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(3, 10));
		reachableFields.add(new Field(4, 10));
		reachableFields.add(new Field(5, 8));
		reachableFields.add(new Field(5, 9));
		reachableFields.add(new Field(3, 5));
		reachableFields.add(new Field(4, 6));
		reachableFields.add(new Field(1, 4));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testBlackPawnReachableFields() {
		Piece blackPawn = new BlackPawn();
		blackPawn.setMoved();
		Field field = new Field(4, 4); // D4
		putPiece(field, blackPawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(3, 4));
		reachableFields.add(new Field(4, 5));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testBlackPawnCornerReachableFields() {
		Piece blackPawn = new BlackPawn();
		blackPawn.setMoved();
		Field field = new Field(1, 1); // A1
		putPiece(field, blackPawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(1, 2));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testBlackPawnFirstMoveReachableFields() {
		Piece blackPawn = new BlackPawn();
		Field field = new Field(8, 4); // H4
		putPiece(field, blackPawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(8, 5));
		reachableFields.add(new Field(7, 4));
		reachableFields.add(new Field(8, 6));
		reachableFields.add(new Field(6, 4));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testWhitePawnReachableFields() {
		Piece whitePawn = new WhitePawn();
		whitePawn.setMoved();
		Field field = new Field(4, 4); // D4
		putPiece(field, whitePawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(5, 4));
		reachableFields.add(new Field(5, 5));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testWhitePawnCornerReachableFields() {
		Piece whitePawn = new WhitePawn();
		whitePawn.setMoved();
		Field field = new Field(6, 1); // F1
		putPiece(field, whitePawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(7, 2));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testWhitePawnFirstMoveReachableFields() {
		Piece whitePawn = new WhitePawn();
		Field field = new Field(2, 4); // B4
		putPiece(field, whitePawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(3, 4));
		reachableFields.add(new Field(4, 4));
		reachableFields.add(new Field(3, 5));
		reachableFields.add(new Field(4, 6));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testGrayPawnReachableFields() {
		Piece grayPawn = new GrayPawn();
		grayPawn.setMoved();
		Field field = new Field(4, 4); // D4
		putPiece(field, grayPawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(3, 3));
		reachableFields.add(new Field(4, 3));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testGrayPawnCornerReachableFields() {
		Piece grayPawn = new GrayPawn();
		grayPawn.setMoved();
		Field field = new Field(13, 8); // M8
		putPiece(field, grayPawn);

		Set<Field> reachableFields = new HashSet<>();
		reachableFields.add(new Field(12, 7));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

	@Test
	public void testGrayPawnFirstMoveReachableFields() {
		Piece grayPawn = new GrayPawn();
		Field field = new Field(8, 12); // H12
		putPiece(field, grayPawn);

		Set<Field> reachableFields = new HashSet<>();

		reachableFields.add(new Field(8, 11));
		reachableFields.add(new Field(8, 10));
		reachableFields.add(new Field(7, 11));
		reachableFields.add(new Field(6, 10));

		assertEquals(reachableFields, chessboardPresenter.getReachableFields(field));
	}

}
