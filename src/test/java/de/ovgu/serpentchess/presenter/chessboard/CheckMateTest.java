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

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for detecting check (mate) positions.
 */
public class CheckMateTest extends ChessboardPresenterTest {

	@Test
	public void testCheckMate() {
		putPiece(new Field(1, 1), new WhitePawn());
		putPiece(new Field(4, 4), new King(PieceColor.GRAY));
		putPiece(new Field(7, 7), new King(PieceColor.BLACK));

		assertTrue(chessboardPresenter.isCheckMate(PieceColor.WHITE));
		assertFalse(chessboardPresenter.isCheckMate(PieceColor.GRAY));
		assertFalse(chessboardPresenter.isCheckMate(PieceColor.BLACK));
	}

	@Test
	public void testCheck() {
		putPiece(new Field(1, 1), new King(PieceColor.WHITE));
		putPiece(new Field(1, 8), new King(PieceColor.GRAY));
		putPiece(new Field(6, 1), new King(PieceColor.BLACK));

		putPiece(new Field(8, 8), new Rook(PieceColor.WHITE));

		assertFalse(chessboardPresenter.isInCheck(PieceColor.WHITE));
		assertTrue(chessboardPresenter.isInCheck(PieceColor.GRAY));
		assertFalse(chessboardPresenter.isInCheck(PieceColor.BLACK));
	}

	public void testCheckNoKing() {
		assertFalse(chessboardPresenter.isInCheck(PieceColor.BLACK));
	}

}
