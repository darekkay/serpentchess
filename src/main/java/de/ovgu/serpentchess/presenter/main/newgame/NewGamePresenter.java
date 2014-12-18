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
package de.ovgu.serpentchess.presenter.main.newgame;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.ovgu.serpentchess.presenter.events.main.OpenNewGameViewEvent;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.view.newgame.NewGameView;
import org.apache.log4j.Logger;

/**
 * Presenter for the new game view.
 */
public class NewGamePresenter {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();

	private final NewGameView view;

	@Inject
	public NewGamePresenter(NewGameView view, EventBus eventBus) {
		this.view = view;
		eventBus.register(this);
	}

	@Subscribe
	public void openNewGameView(OpenNewGameViewEvent event) {
		EVENT_LOGGER.debug("New game dialog opened");
		view.showView();
	}
}
