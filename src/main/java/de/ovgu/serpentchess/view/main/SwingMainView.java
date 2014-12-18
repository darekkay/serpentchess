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
package de.ovgu.serpentchess.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.ovgu.serpentchess.presenter.events.main.ShutdownEvent;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.util.SwingUtil;
import de.ovgu.serpentchess.view.game.GameView;
import de.ovgu.serpentchess.view.game.SwingGameView;

/**
 * Swing implementation for the main view.
 */
@Singleton
public class SwingMainView extends JFrame implements MainView {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();
	private static final int MIN_WIDTH = 550;
	private static final int MIN_HEIGHT = 530;

	private final EventBus eventBus;

	@Inject
	public SwingMainView(EventBus eventBus) {
		super("SerpentChess");
		this.eventBus = eventBus;

		initView();
		initWidgets();
		initEvents();

		EVENT_LOGGER.debug("GUI created");
	}

	private void initView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		SwingUtil.setWindowIcon(this, "/images/icon.png");
		SwingUtil.centerWindow(this);
	}

	private void initWidgets() {
		setJMenuBar(new MainMenuBar(this, eventBus));
		setLayout(new BorderLayout());
	}

	private void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				eventBus.post(new ShutdownEvent());
			}
		});
	}

	@Override
	public void addGameView(GameView gameView) {
		getContentPane().add((SwingGameView) gameView, BorderLayout.CENTER);
	}

	@Override
	public void showView() {
		pack();
		setVisible(true);
	}

	@Override
	public void closeView() {
		WindowEvent windowClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		dispatchEvent(windowClosing);
	}

}
