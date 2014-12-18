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
package de.ovgu.serpentchess.view.newgame;

import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.google.inject.Inject;

import de.ovgu.serpentchess.view.main.RootWindow;

/**
 * Swing implementation for the new game view.
 */
public class SwingNewGameView extends JDialog implements NewGameView {

	@Inject
	public SwingNewGameView(@RootWindow Window owner) {
		super(owner, "Create new game");
		initComponents();

		setPreferredSize(new Dimension(200, 200));
		setMinimumSize(new Dimension(200, 200));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setAlwaysOnTop(true);
	}

	private void initComponents() {
		pack();
	}

	@Override
	public void showView() {
		setVisible(true);
	}
}
