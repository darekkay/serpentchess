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

import de.ovgu.serpentchess.model.chessboard.piece.Bishop;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;

/**
 * Testing reachable fields when multiple pieces are on the chessboard.
 */
public class MultiplePiecesReachableFieldsTest extends ChessboardPresenterTest {

	@Test
	public void testOwnPieceCannotBeTaken() {
		// fields containing own pieces are not reachable
		Field piece1Field = new Field(5, 7); // E7
		putPiece(piece1Field, new King(PieceColor.WHITE));

		Field piece2Field = new Field(4, 7); // D7
		putPiece(piece2Field, new King(PieceColor.WHITE));

		assertEquals(11, chessboardPresenter.getReachableFields(piece1Field).size());
		assertEquals(11, chessboardPresenter.getReachableFields(piece2Field).size());
	}

	@Test
	public void testOpponentPieceCanBeTaken() {
		// the field of an opponent piece is reachable
		Field piece1Field = new Field(5, 7); // E7
		putPiece(piece1Field, new King(PieceColor.WHITE));

		Field piece2Field = new Field(4, 7); // D7
		putPiece(piece2Field, new King(PieceColor.BLACK));

		assertEquals(6, chessboardPresenter.getReachableFields(piece1Field).size());
		assertEquals(6, chessboardPresenter.getReachableFields(piece2Field).size());
	}

	@Test
	public void testCannotJumpOverOwnPieces() {
		// own pieces cannot be taken, and further fields are not reachable
		Field piece1Field = new Field(2, 7); // B7
		putPiece(piece1Field, new Rook(PieceColor.WHITE));
		Field piece2Field = new Field(5, 7); // E7
		putPiece(piece2Field, new Rook(PieceColor.WHITE));

		assertEquals(18, chessboardPresenter.getReachableFields(piece1Field).size());
		assertEquals(30, chessboardPresenter.getReachableFields(piece2Field).size());
	}

	@Test
	public void testCannotJumpOverOpponentPieces() {
		// the opponent piece can be taken, but further fields are not reachable
		Field piece1Field = new Field(2, 7); // B7
		putPiece(piece1Field, new Rook(PieceColor.WHITE));
		Field piece2Field = new Field(5, 7); // E7
		putPiece(piece2Field, new Rook(PieceColor.BLACK));

		assertEquals(19, chessboardPresenter.getReachableFields(piece1Field).size());
		assertEquals(31, chessboardPresenter.getReachableFields(piece2Field).size());
	}

	@Test
	public void testCannotMoveBishopThroughBlockedFields() {
		// diagonal movement is not possible, if the two fields in between are  taken
		putPiece(new Field(8, 7), new Rook(PieceColor.WHITE)); // H7
		putPiece(new Field(8, 8), new Rook(PieceColor.BLACK)); // H8

		Field pieceField1 = new Field(1, 4); // A4
		putPiece(pieceField1, new Bishop(PieceColor.WHITE));
		assertEquals(10, chessboardPresenter.getReachableFields(pieceField1).size());

		Field pieceField2 = new Field(1, 5); // B4
		putPiece(pieceField2, new Bishop(PieceColor.WHITE));
		assertEquals(13, chessboardPresenter.getReachableFields(pieceField2).size());
	}

	@Test
	public void testCannotMovePawnThroughBlockedFields() {
		// diagonal movement is not possible, if the two fields in between are taken
		putPiece(new Field(8, 7), new Rook(PieceColor.WHITE)); // H7
		putPiece(new Field(8, 8), new Rook(PieceColor.BLACK)); // H8
		putPiece(new Field(9, 8), new Rook(PieceColor.BLACK)); // I8
		putPiece(new Field(9, 9), new Rook(PieceColor.BLACK)); // I9

		Field pieceField1 = new Field(7, 7); // G7
		putPiece(pieceField1, new WhitePawn());
		assertEquals(0, chessboardPresenter.getReachableFields(pieceField1).size());

		Field pieceField2 = new Field(7, 8); // E7
		putPiece(pieceField2, new WhitePawn());
		assertEquals(3, chessboardPresenter.getReachableFields(pieceField2).size());
	}

}
