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

import static org.mockito.Mockito.mock;

import org.junit.Before;

import com.google.common.eventbus.EventBus;

import de.ovgu.serpentchess.model.chessboard.Chessboard;
import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.view.chessboard.ChessboardView;

/**
 * Provides common functionality for all chessboard chessboardPresenter tests.
 */
public class ChessboardPresenterTest {

	protected ChessboardPresenter chessboardPresenter;
	protected ChessboardView chessboardViewMock;
	protected EventBus eventBusMock;

	@Before
	public void setUp() {
		chessboardViewMock = mock(ChessboardView.class);
		chessboardPresenter = new ChessboardPresenter(chessboardViewMock, mock(EventBus.class));
		chessboardPresenter.initChessboard();
		chessboardPresenter.chessboard.clearPieces();
		eventBusMock = mock(EventBus.class);
	}

	protected void putPiece(Field field, Piece piece) {
		Chessboard chessboard = chessboardPresenter.chessboard;
		chessboard.putPiece(field, piece);
	}
}
