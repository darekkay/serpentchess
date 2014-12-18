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
package de.ovgu.serpentchess.presenter.game;

import com.google.common.eventbus.EventBus;
import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.presenter.chessboard.ChessboardPresenter;
import de.ovgu.serpentchess.presenter.chessboard.promotion.PawnPromotionPresenter;
import de.ovgu.serpentchess.presenter.events.chessboard.FieldClickEvent;
import de.ovgu.serpentchess.view.game.GameView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GamePresenterTest {

	private GamePresenter gamePresenter;


	private EventBus eventBusMock;

	@Before
	public void setUp() {
        GameView gameViewMock = mock(GameView.class);
        PawnPromotionPresenter pawnPromotionPresenterMock = mock(PawnPromotionPresenter.class);
        ChessboardPresenter chessboardPresenterMock = mock(ChessboardPresenter.class);
		eventBusMock = mock(EventBus.class);
		gamePresenter = new GamePresenter(gameViewMock, chessboardPresenterMock, pawnPromotionPresenterMock, eventBusMock);
	}

	@Test
	public void testSelectField() {
		eventBusMock.post(new FieldClickEvent(new Field(5, 5)));
		assertEquals(gamePresenter.chessboardPresenter.getPiece(new Field(5, 5)), gamePresenter.chessboardPresenter.getSelectedPiece());
	}

	@Test
	public void testSelectEmptyField() {
		eventBusMock.post(new FieldClickEvent(new Field(4, 5)));
		assertEquals(null, gamePresenter.chessboardPresenter.getPiece(new Field(4, 5)));
	}

}
