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

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.ovgu.serpentchess.presenter.chessboard.promotion.PawnPromotionPresenter;
import de.ovgu.serpentchess.presenter.events.chessboard.PromotionPieceSelectEvent;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.player.ChessPlayer;
import de.ovgu.serpentchess.presenter.Move;
import de.ovgu.serpentchess.presenter.chessboard.ChessboardPresenter;
import de.ovgu.serpentchess.presenter.events.chessboard.FieldClickEvent;
import de.ovgu.serpentchess.presenter.events.game.GameFinishedEvent;
import de.ovgu.serpentchess.presenter.events.game.LoadGameEvent;
import de.ovgu.serpentchess.presenter.events.game.RedoEvent;
import de.ovgu.serpentchess.presenter.events.game.SaveGameEvent;
import de.ovgu.serpentchess.presenter.events.game.UndoEvent;
import de.ovgu.serpentchess.presenter.game.history.History;
import de.ovgu.serpentchess.presenter.game.history.HistoryReader;
import de.ovgu.serpentchess.presenter.game.history.HistoryWriter;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.view.game.GameView;

/**
 * Presenter for all game relevant mechanics.
 */
public class GamePresenter {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();
	private static final Logger ERROR_LOGGER = LoggerFactory.getErrorLogger();

	private final GameView view;
	protected final ChessboardPresenter chessboardPresenter;
	protected final PawnPromotionPresenter pawnPromotionPresenter;
	private History history = new History();
	private boolean gameFinished = false;

	@Inject
	public GamePresenter(final GameView view, final ChessboardPresenter chessboardPresenter,
			final PawnPromotionPresenter pawnPromotionPresenter, final EventBus eventBus) {
		this.view = view;
		this.chessboardPresenter = chessboardPresenter;
		this.pawnPromotionPresenter = pawnPromotionPresenter;
		eventBus.register(this);
		view.addChessboardView(chessboardPresenter.getView());
	}

	public final void startNewGame() {
		history.reset();
		chessboardPresenter.initChessboard();
		gameFinished = false;
		EVENT_LOGGER.info("New game started.");
	}

	public GameView getView() {
		return view;
	}

	@Subscribe
	public void onFieldClicked(final FieldClickEvent event) {
		if (gameFinished) {
			return;
		}
		final Field clickedField = event.getField();
		final Piece clickedPiece = chessboardPresenter.getPiece(clickedField);
		final Piece selectedPiece = chessboardPresenter.getSelectedPiece();
		// sprint implementation
		if (selectedPiece == null) {
			// check if player has a turn
			if (clickedPiece != null) {
				if (clickedPiece.getColor() == chessboardPresenter.getCurrentPlayerColor()) {
					if (chessboardPresenter.selectPiece(clickedField)) {
						// piece selected
						EVENT_LOGGER.debug(chessboardPresenter.getSelectedPiece() + " selected ");
					}
				}
			}
		} else {
			final Field selectedField = chessboardPresenter.getSelectedField();

			if (clickedPiece != null && clickedPiece.getColor() == chessboardPresenter.getCurrentPlayerColor()) {
				chessboardPresenter.selectPiece(clickedField);
				EVENT_LOGGER.info(chessboardPresenter.getSelectedPiece() + " selected ");
				return;
			}
			final Move move = new Move(selectedField, selectedPiece, clickedField, clickedPiece);
			if (chessboardPresenter.movePiece(move)) {
				if (pawnPromotionPresenter.checkPromotionRequired(move)) {
					pawnPromotionPresenter.askForPromotionPiece(move);
				}
				// clicked field in range
				history.addMove(move);
				EVENT_LOGGER.debug(selectedPiece + " moved to " + clickedField.getAbbreviation());

				boolean skippedPlayer = false;
				do {
					if (skippedPlayer) {
						view.alertPlayerSkipped(chessboardPresenter.getCurrentPlayerColor());
						history.addMove(new Move());
					}
					chessboardPresenter.setNextPlayer();
				} while (skippedPlayer = !chessboardPresenter.canMove(chessboardPresenter.getCurrentPlayerColor()));

				EVENT_LOGGER.info(chessboardPresenter.getCurrentPlayer().getPlayerColor() + " player in turn.");
			}
		}
	}

	@Subscribe
	public void onPromotionPieceSelected(final PromotionPieceSelectEvent event) {
		Move move = event.getMove();
		Piece promotionPiece = event.getPromotionPiece();
		Field field = event.getField();
		move.setPawnPromotion(promotionPiece);
		chessboardPresenter.putPiece(field, promotionPiece);
		chessboardPresenter.print();
	}

	@Subscribe
	public void loadEvent(final LoadGameEvent event) {
		try {
			final HistoryReader reader = new HistoryReader();
			final History newHistory = reader.read(event.getFile());

			startNewGame();

			for (final Move move : newHistory.getMoves()) {
				if (move.getMovedPiece() != null) {
					chessboardPresenter.movePiece(move, false);
					Piece promotionPiece = move.getPromotionPiece();
					if (promotionPiece != null) {
						chessboardPresenter.putPiece(move.getDestination(), promotionPiece);
					}
				}
				chessboardPresenter.setNextPlayer();
				history.addMove(move);
			}
			chessboardPresenter.print();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "History could not be read from file:\n" + event.getFile().getName(),
					"Loading history failed", JOptionPane.ERROR_MESSAGE);
			ERROR_LOGGER.error("History could not be read.", e);
		}
	}

	@Subscribe
	public void saveEvent(final SaveGameEvent event) {
		try {
			HistoryWriter writer = new HistoryWriter(history);
			writer.write(event.getFile());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "History could not written to file:\n" + event.getFile().getName(),
					"Writing history failed", JOptionPane.ERROR_MESSAGE);
			ERROR_LOGGER.error("History could not be written.", e);
		}
	}

	@Subscribe
	public void redoEvent(final RedoEvent event) {
		final Move move = history.redo();
		if (move != null) {
			if (move.getMovedPiece() == null) {
				chessboardPresenter.setNextPlayer();
				redoEvent(null);
			} else {
				chessboardPresenter.movePiece(move, false);
				Piece promotionPiece = move.getPromotionPiece();
				if (promotionPiece != null) {
					chessboardPresenter.putPiece(move.getDestination(), promotionPiece);
				}
				chessboardPresenter.setNextPlayer();
			}
		}
		chessboardPresenter.print();
	}

	@Subscribe
	public void undoEvent(final UndoEvent event) {
		final Move move = history.undo();
		if (move != null) {
			if (move.getMovedPiece() == null) {
				chessboardPresenter.setPreviousPlayer();
				undoEvent(null);
			} else {
				Piece promotionPiece = move.getPromotionPiece();
				if (promotionPiece != null) {
					chessboardPresenter.putPiece(move.getDestination(), move.getMovedPiece());
				}
				chessboardPresenter.undoMove(move);
				chessboardPresenter.print();
				
				if (gameFinished) {
					gameFinished = false;
				} else {
					chessboardPresenter.setPreviousPlayer();
				}
			}
		}
	}
	
	@Subscribe
	public void gameFinishedEvent(final GameFinishedEvent event) {
		ChessPlayer winner = event.getPlayer();
		final String color = winner.getPlayerColor().name().substring(0, 1);
		final ImageIcon icon = createImageIcon("/images/piece/King-" + color + ".png",
	            "a pretty but meaningless splat");
		JOptionPane.showMessageDialog(null, "Player " + winner.getPlayerColor() + " has won the game.",
				"Game finished", JOptionPane.INFORMATION_MESSAGE, icon);
		EVENT_LOGGER.info("Game finished");
		gameFinished  = true;
	}
	
/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			ERROR_LOGGER.error("file does not exist");
			return null;
		}
	}

}
