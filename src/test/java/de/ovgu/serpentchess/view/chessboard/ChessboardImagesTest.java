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
package de.ovgu.serpentchess.view.chessboard;

import static org.junit.Assert.assertNotNull;

import java.awt.Image;

import org.junit.Test;

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

public class ChessboardImagesTest {

	@Test
	public void highlightingImagesExistTest() {
		assertNotNull(ChessboardImages.INSTANCE.getScaledBeatPieceImage());
		assertNotNull(ChessboardImages.INSTANCE.getScaledReachableFieldImage());
		assertNotNull(ChessboardImages.INSTANCE.getScaledSelectedFieldImage());
	}

	@Test
	public void chessboardImageExistsTest() {
		assertNotNull(ChessboardImages.INSTANCE.getScaledChessboardImage());
	}

	@Test
	public void whitePawnImagesExistTest() {
		assertNotNull(getPieceImage(new WhitePawn()));
	}

	@Test
	public void blackPeceImagesExistTest() {
		testPieces(PieceColor.BLACK);
		assertNotNull(getPieceImage(new BlackPawn()));
	}

	@Test
	public void grayPeceImagesExistTest() {
		testPieces(PieceColor.GRAY);
		assertNotNull(getPieceImage(new GrayPawn()));
	}

	private void testPieces(final PieceColor color) {
		assertNotNull(getPieceImage(new King(color)));
		assertNotNull(getPieceImage(new Bishop(color)));
		assertNotNull(getPieceImage(new Queen(color)));
		assertNotNull(getPieceImage(new Rook(color)));
		assertNotNull(getPieceImage(new Knight(color)));
	}

	private Image getPieceImage(final Piece piece) {
		return ChessboardImages.INSTANCE.getScaledPieceImage(piece);
	}
}
