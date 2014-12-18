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
package de.ovgu.serpentchess.view.chessboard.promotion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.Bishop;
import de.ovgu.serpentchess.model.chessboard.piece.Knight;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;
import de.ovgu.serpentchess.presenter.Move;
import de.ovgu.serpentchess.presenter.events.chessboard.PromotionPieceSelectEvent;
import de.ovgu.serpentchess.util.SwingUtil;
import de.ovgu.serpentchess.view.main.RootWindow;

/**
 * Swing implementation for the PawnPromotionView.
 */
public class SwingPawnPromotionView extends JDialog implements PawnPromotionView {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 80;

	private final EventBus eventBus;
	private Piece promotionPiece;
	private Move move;

	@Inject
	public SwingPawnPromotionView(@RootWindow Window owner, EventBus eventBus) {
		super(owner);
		this.eventBus = eventBus;
		initDialog();
	}

	private void initDialog() {
		initFireEventOnClosing();

		getContentPane().setLayout(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		SwingUtil.centerWindow(this);

		setModal(true);
		setAlwaysOnTop(true);
	}

	private void initFireEventOnClosing() {
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				eventBus.post(new PromotionPieceSelectEvent(move, promotionPiece));
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
	}

	private void initComponents(PieceColor pieceColor) {
		getContentPane().removeAll();
		getContentPane().add(new PiecePromotionButton(new Queen(pieceColor)));
		getContentPane().add(new PiecePromotionButton(new Rook(pieceColor)));
		getContentPane().add(new PiecePromotionButton(new Bishop(pieceColor)));
		getContentPane().add(new PiecePromotionButton(new Knight(pieceColor)));
		pack();
	}

	@Override
	public void showView(final Move move, PieceColor pieceColor) {
		this.move = move;
		setTitleDependingOnColor(pieceColor);
		initComponents(pieceColor);
		setVisible(true);
	}

	private void setTitleDependingOnColor(PieceColor color) {
		setTitle("Select " + color.name().toLowerCase() + " promotion piece");
	}

	/**
	 * Button which selects a promotion piece.
	 */
	private class PiecePromotionButton extends JButton {

		public PiecePromotionButton(final Piece piece) {
			setText(piece.getClass().getSimpleName());
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					promotionPiece = piece;
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					dispose();
				}
			});
		}
	}

}
