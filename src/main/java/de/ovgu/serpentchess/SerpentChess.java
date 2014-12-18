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
package de.ovgu.serpentchess;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import de.ovgu.serpentchess.presenter.main.MainPresenter;
import de.ovgu.serpentchess.presenter.main.newgame.NewGamePresenter;
import de.ovgu.serpentchess.util.GuiceModule;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.util.SwingUtil;

/**
 * Main class.
 */
public class SerpentChess {
	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();

	@Inject
	public SerpentChess(MainPresenter presenter) {
		presenter.start();
	}

	public static void main(String[] args) {
		EVENT_LOGGER.info("SerpentChess started");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SwingUtil.setLookAndFeelWindows();
				injectApplication();
			}
		});
	}

	private static void injectApplication() {
		Injector injector = Guice.createInjector(new GuiceModule());
		injector.getInstance(SerpentChess.class);
		injector.getInstance(NewGamePresenter.class);
	}
}
