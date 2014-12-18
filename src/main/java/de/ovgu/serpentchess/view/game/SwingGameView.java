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
package de.ovgu.serpentchess.view.game;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.view.chessboard.ChessboardView;
import de.ovgu.serpentchess.view.chessboard.SwingChessboardView;

/**
 * Swing implementation of the GameView.
 */
public class SwingGameView extends JPanel implements GameView {

	@Inject
	public SwingGameView() {
		initWidgets();
	}

	private void initWidgets() {
		setLayout(new BorderLayout());
	}

	@Override
	public void addChessboardView(ChessboardView chessboardView) {
		add((SwingChessboardView) chessboardView, BorderLayout.CENTER);
	}

	@Override
	public void alertPlayerSkipped(PieceColor color) {
		JOptionPane.showMessageDialog(this, "No possible movements for " + color + " pieces. Next player has a turn.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
