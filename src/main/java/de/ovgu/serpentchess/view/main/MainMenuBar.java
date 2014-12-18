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

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import com.google.common.eventbus.EventBus;

import de.ovgu.serpentchess.presenter.events.game.LoadGameEvent;
import de.ovgu.serpentchess.presenter.events.game.RedoEvent;
import de.ovgu.serpentchess.presenter.events.game.SaveGameEvent;
import de.ovgu.serpentchess.presenter.events.game.UndoEvent;
import de.ovgu.serpentchess.presenter.events.main.ShutdownEvent;
import de.ovgu.serpentchess.presenter.events.main.StartNewGameEvent;
import de.ovgu.serpentchess.util.SwingUtil;

/**
 * Menu bar for the main view.
 */
public class MainMenuBar extends JMenuBar {

	// Create a file chooser
	final JFileChooser fc = new JFileChooser();

	private final Window window;
	private final EventBus eventBus;

	public MainMenuBar(final Window window, final EventBus eventBus) {
		this.window = window;
		this.eventBus = eventBus;

		createFileMenu();
		createGameMenu();
	}

	private void createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		add(fileMenu);

		SwingUtil.addMenuItem(window, fileMenu, "New game", null, 0, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventBus.post(new StartNewGameEvent());
					}
				});

		SwingUtil.addMenuItem(window, fileMenu, "Exit", "/images/exit.png", 0,
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventBus.post(new ShutdownEvent());
					}
				});
	}

	private void createGameMenu() {
		JMenu gameMenu = new JMenu("Game");
		add(gameMenu);

		SwingUtil.addMenuItem(window, gameMenu, "Undo", null, 0, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventBus.post(new UndoEvent());
					}
				});

		SwingUtil.addMenuItem(window, gameMenu, "Redo", null, 0, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventBus.post(new RedoEvent());
					}
				});

		SwingUtil.addMenuItem(window, gameMenu, "Load", null, 0, null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
					eventBus.post(new LoadGameEvent(fc.getSelectedFile()));
				}
			}
		});

		SwingUtil.addMenuItem(window, gameMenu, "Save", null, 0, null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fc.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
					eventBus.post(new SaveGameEvent(fc.getSelectedFile()));
				}
			}
		});
	}
}
