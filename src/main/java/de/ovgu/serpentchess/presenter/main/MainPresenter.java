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
package de.ovgu.serpentchess.presenter.main;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.ovgu.serpentchess.presenter.events.main.StartNewGameEvent;
import de.ovgu.serpentchess.presenter.events.main.ShutdownEvent;
import de.ovgu.serpentchess.presenter.game.GamePresenter;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.view.main.MainView;
import org.apache.log4j.Logger;

/**
 * Presenter for the main view.
 */
public class MainPresenter {
	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();
	private final MainView view;
	private final GamePresenter gamePresenter;

	@Inject
	public MainPresenter(MainView view, GamePresenter gamePresenter, EventBus eventBus) {
		this.view = view;
		this.gamePresenter = gamePresenter;

		eventBus.register(this);

		view.addGameView(gamePresenter.getView());
	}

	@Subscribe
	public void startNewGame(final StartNewGameEvent event) {
		gamePresenter.startNewGame();
	}

	@Subscribe
	public void shutdown(final ShutdownEvent event) {
		onBeforeShutdown();
		EVENT_LOGGER.info("SerpentChess closed");
		view.closeView();
	}

	private void onBeforeShutdown() {
		// TODO: eventuelle Konfiguration speichern
	}

	public void start() {
		view.showView();
	}
}
