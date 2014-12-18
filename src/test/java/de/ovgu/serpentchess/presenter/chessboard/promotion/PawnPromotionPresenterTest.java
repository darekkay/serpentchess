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
package de.ovgu.serpentchess.presenter.chessboard.promotion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import de.ovgu.serpentchess.presenter.Move;
import de.ovgu.serpentchess.presenter.chessboard.ChessboardPresenter;
import de.ovgu.serpentchess.view.chessboard.promotion.PawnPromotionView;

public class PawnPromotionPresenterTest {

	private PawnPromotionPresenter pawnPromotionPresenter;
	private PawnPromotionView pawnPromotionViewMock;
	private ChessboardPresenter chessboardPresenterMock;

	@Before
	public void setUp() {
		pawnPromotionViewMock = mock(PawnPromotionView.class);
		chessboardPresenterMock = mock(ChessboardPresenter.class);
        EventBus eventBusMock = mock(EventBus.class);
		pawnPromotionPresenter = new PawnPromotionPresenter(pawnPromotionViewMock, chessboardPresenterMock, eventBusMock);
	}

	@Test
	public void testNotEnemyBaseLine() {
		Field destination = new Field(6, 2);
		Piece pawn = new WhitePawn();
		when(chessboardPresenterMock.getPiece(destination)).thenReturn(pawn);

		Move move = new Move(new Field(1, 1), pawn, destination);
		assertFalse(pawnPromotionPresenter.checkPromotionRequired(move));
	}

	@Test
	public void testEnemyBaseLine() {
		Field destination = new Field(6, 1);
		Piece pawn = new WhitePawn();
		when(chessboardPresenterMock.getPiece(destination)).thenReturn(pawn);
		when(chessboardPresenterMock.getFieldOnDirection(any(Field.class), any(Direction.class))).thenReturn(destination);

		Move move = new Move(new Field(1, 1), pawn, destination);
		assertTrue(pawnPromotionPresenter.checkPromotionRequired(move));
	}

	@Test
	public void testNotAPawn() {
		Field destination = new Field(6, 1);
		Piece queen = new Queen(PieceColor.WHITE);
		when(chessboardPresenterMock.getPiece(destination)).thenReturn(queen);
		when(chessboardPresenterMock.getFieldOnDirection(any(Field.class), any(Direction.class))).thenReturn(destination);

		Move move = new Move(new Field(1, 1), queen, destination);
		assertFalse(pawnPromotionPresenter.checkPromotionRequired(move));
	}

	/**
	 * Asking for a promotion piece should open the according view.
	 */
	@Test
	public void testOpenView() {
		Piece piece = new Queen(PieceColor.WHITE);
		Field destination = new Field(2, 2);
		Move move = new Move(new Field(1, 1), piece, destination);

		when(chessboardPresenterMock.getPiece(destination)).thenReturn(piece);
		pawnPromotionPresenter.askForPromotionPiece(move);
		verify(pawnPromotionViewMock).showView(move, piece.getColor());
	}

}
