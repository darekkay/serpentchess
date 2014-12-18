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
package de.ovgu.serpentchess.util;

import java.awt.Window;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.ovgu.serpentchess.presenter.chessboard.ChessboardPresenter;
import de.ovgu.serpentchess.presenter.chessboard.IChessboardPresenter;
import de.ovgu.serpentchess.view.chessboard.ChessboardView;
import de.ovgu.serpentchess.view.chessboard.SwingChessboardView;
import de.ovgu.serpentchess.view.chessboard.promotion.PawnPromotionView;
import de.ovgu.serpentchess.view.chessboard.promotion.SwingPawnPromotionView;
import de.ovgu.serpentchess.view.game.GameView;
import de.ovgu.serpentchess.view.game.SwingGameView;
import de.ovgu.serpentchess.view.main.MainView;
import de.ovgu.serpentchess.view.main.RootWindow;
import de.ovgu.serpentchess.view.main.SwingMainView;
import de.ovgu.serpentchess.view.newgame.NewGameView;
import de.ovgu.serpentchess.view.newgame.SwingNewGameView;

/**
 * Guice module file.
 */
public class GuiceModule extends AbstractModule {

	public GuiceModule() {

	}

	@Override
	protected void configure() {
		bind(Window.class).annotatedWith(RootWindow.class).to(SwingMainView.class);

		bind(MainView.class).to(SwingMainView.class);
		bind(GameView.class).to(SwingGameView.class);
		bind(ChessboardView.class).to(SwingChessboardView.class);
		bind(IChessboardPresenter.class).to(ChessboardPresenter.class);
		bind(PawnPromotionView.class).to(SwingPawnPromotionView.class);

		bind(NewGameView.class).to(SwingNewGameView.class);

		bind(EventBus.class).in(Singleton.class);
	}
}