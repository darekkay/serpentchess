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
package de.ovgu.serpentchess.presenter.chessboard;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.ovgu.serpentchess.model.chessboard.Chessboard;
import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.direction.DiagonalDirections;
import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.direction.StraightDirections;
import de.ovgu.serpentchess.model.chessboard.piece.Bishop;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Knight;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.BlackPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.GrayPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.Pawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import de.ovgu.serpentchess.model.chessboard.player.ChessPlayer;
import de.ovgu.serpentchess.presenter.Move;
import de.ovgu.serpentchess.presenter.events.game.GameFinishedEvent;
import de.ovgu.serpentchess.util.CircularArrayList;
import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.view.chessboard.ChessboardView;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Chessboard presenter default implementation.
 */
@Singleton
public class ChessboardPresenter implements IChessboardPresenter {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();
	private final ChessboardView view;
	protected Chessboard chessboard;
	private Field selectedField;

	private ChessPlayer currentPlayer;
	private CircularArrayList<ChessPlayer> players = new CircularArrayList<>();
	private final EventBus eventBus;

	@Inject
	public ChessboardPresenter(ChessboardView view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;
		eventBus.register(this);
		initChessboard(); // NOPMD cannot be final because of mocking
	}

	public void initChessboard() {
		chessboard = new Chessboard();
		selectedField = null;
		initFields();
		initPlayers();
		initPieces();
		view.print(chessboard);
	}

	protected void initFields() {
		initFieldsAboveMedianInclusive();
		initFieldsBelowMedian();
	}

	protected void initPlayers() {
		for (final PieceColor color : PieceColor.values()) {
			players.add(new ChessPlayer(color));
		}
		
		currentPlayer = players.getCurrent();
	}

	private void initFieldsAboveMedianInclusive() {
		int rowSize = 8;

		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= rowSize; j++) {
				createField(i, j);
			}
			rowSize++;
		}
	}

	private void initFieldsBelowMedian() {
		int rowSize = 13;
		int collumnStart = 2;
		for (int i = 7; i <= 13; i++) {
			for (int j = collumnStart; j <= rowSize; j++) {
				createField(i, j);
			}
			collumnStart++;
		}
	}

	private void createField(int row, int column) {
		chessboard.createField(row, column);
	}

	protected void initPieces() {
		initializePieces(new Field(1, 1), new Field(2, 1), new Direction(0, 1), PieceColor.WHITE);
		initializePieces(new Field(6, 13), new Field(5, 12), new Direction(1, 0), PieceColor.GRAY);
		initializePieces(new Field(13, 8), new Field(13, 9), new Direction(-1, -1), PieceColor.BLACK);
		EVENT_LOGGER.debug("Chessboard pieces initialized.");
	}

	/**
	 * Create the pieces in clockwise order.
	 * 
	 * @param startRook
	 *            The {@link Field} of the first Rook
	 * @param startPawn
	 *            The Field of the wirst pawn
	 * @param direction
	 *            The clockwise direction specific for the color
	 * @param color
	 *            The color of the pieces.
	 */
	private void initializePieces(final Field startRook, final Field startPawn, final Direction direction, final PieceColor color) {
		int j = 0;
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Rook(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Knight(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Bishop(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new King(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Queen(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Knight(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Bishop(color));
		putPiece(getFieldOnDirection(startRook, direction.multiply(j++)), new Rook(color));
		for (int i = 0; i <= 8; i++) {
			putPiece(getFieldOnDirection(startPawn, direction.multiply(i)), createPawn(color));
		}
	}

	protected Pawn createPawn(final PieceColor color) {
		switch (color) {
		case BLACK:
			return new BlackPawn();
		case GRAY:
			return new GrayPawn();
		case WHITE:
			return new WhitePawn();
		default:
			throw new IllegalArgumentException("Unknown color: " + color);
		}
	}

	public ChessboardView getView() {
		return view;
	}

	@CheckForNull
	public Field getSelectedField() {
		return selectedField;
	}

	@CheckForNull
	public Piece getSelectedPiece() {
		return getPiece(selectedField);
	}

	public boolean selectPiece(Field field) {
		Piece piece = getPiece(field);
		if (piece != null) {
			selectedField = field;
			view.print(chessboard);
			return true;
		}
		return false;
	}

	public boolean movePiece(final Move move) {
		return movePiece(move, true);
	}

	public boolean movePiece(final Move move, boolean repaint) {
		final Field source = move.getSource();
		final Field destination = move.getDestination();

		final Piece piece = chessboard.getPiece(source);
		final Set<Field> reachableFields = getReachableFields(source);
		if (reachableFields.contains(destination)) {
			chessboard.putPiece(destination, piece);
			chessboard.removePiece(source);
			piece.setMoved();
			selectedField = null;
			if (repaint) {
				print();
			}
			return true;
		}
		return false;
	}

	/**
	 * Performs a movement, even if it's against the rules.
	 */
	private void movePieceWithoutValidityCheck(final Move move) {
		final Piece piece = chessboard.getPiece(move.getSource());
		chessboard.putPiece(move.getDestination(), piece);
		chessboard.removePiece(move.getSource());
		piece.setMoved();
	}

	/**
	 * Resets the move done by movePiece().
	 */
	public void undoMove(final Move move) {
		undoMove(move, true);
	}

	/**
	 * Resets the move done by movePiece().
	 * 
	 * @param repaint
	 *            <code>true</code> if the board will be repainted
	 */
	public void undoMove(final Move move, final boolean repaint) {
		final Piece piece = chessboard.getPiece(move.getDestination());
		if (!move.isMoved()) {
			piece.resetMoved();
		}
		chessboard.putPiece(move.getSource(), piece);
		if (move.getTakenPiece() == null) {
			chessboard.removePiece(move.getDestination());
		} else {
			chessboard.putPiece(move.getDestination(), move.getTakenPiece());
		}
		selectedField = null;
		if (repaint) {
			print();
		}
	}

	@Override
	public Set<Field> getReachableFields(Field field) {
		return getReachableFields(field, true);
	}

	/**
	 * Returns all reachable fields for the piece on the given field. If
	 * verifyCheckFree is true, then it will be made sure, that a movement
	 * doesn't lead to putting onself in check.
	 */
	public Set<Field> getReachableFields(Field field, boolean verifyCheckFree) {
		Piece piece = chessboard.getPiece(field);
		if (piece == null) {
			return Collections.emptySet();
		}

		Set<Field> reachableFields = new HashSet<>();

		for (Direction direction : piece.getDirections()) {
			reachableFields.addAll(getReachableFieldsForDirection(field, piece, direction));
		}

		if (piece instanceof Pawn) {
			reachableFields.addAll(getAdditionalPawnReachableFields((Pawn) piece, field));
		}
		if (piece instanceof King) {
			reachableFields.addAll(getCastlingReachableFields((King) piece, field));
		}

		if (verifyCheckFree) {
			removeCheckMoves(piece, field, reachableFields);
		}

		return reachableFields;
	}

	/**
	 * Returns reachable fields only for one given direction.
	 */
	private Set<Field> getReachableFieldsForDirection(Field field, Piece piece, Direction direction) {
		Set<Field> reachableFields = new HashSet<>();
		Direction tempDirection = direction;
		do {
			Field fieldToCheck = getFieldOnDirection(field, tempDirection);

			if (fieldToCheck == null) {
				break; // field outside the chessboard
			}

			if (!canMoveDiagonally(direction, fieldToCheck)) {
				break; // fields on the diagonal direction are taken
			}

			Piece pieceOnFieldToCheck = getPiece(fieldToCheck);
			if (pieceOnFieldToCheck == null) {
				reachableFields.add(fieldToCheck);
			} else {
				if (pieceOnFieldToCheck.getColor() == piece.getColor()) {
					break; // own piece in the way
				} else {
					if (piece instanceof Pawn) {
						break; // pawns cannot take pieces straight
					}

					reachableFields.add(fieldToCheck); // opponent piece in the
														// way
					break;
				}
			}

			tempDirection = tempDirection.add(direction);

		} while (piece.canMoveMultipleSteps());
		return reachableFields;
	}

	/**
	 * Removes movements, which would lead to putting oneself in check.
	 */
	private void removeCheckMoves(Piece piece, Field field, Set<Field> reachableFields) {
		Field tempSelectedField = selectedField;

		Iterator<Field> iterator = reachableFields.iterator();
		while (iterator.hasNext()) {
			Field destination = iterator.next();
			Move move = new Move(field, piece, destination, chessboard.getPiece(destination));
			movePieceWithoutValidityCheck(move); // simulate move
			if (isInCheck(piece.getColor())) {
				iterator.remove();
			}
			undoMove(move, false);
		}
		selectedField = tempSelectedField;
	}

	/**
	 * Returns true, if the king of the given color stands in check in the
	 * current game state.
	 */
	protected boolean isInCheck(final PieceColor color) {
		final Field kingField = chessboard.getField(new King(color));
		if (kingField == null) {
			return false;
		}

		for (final Field field : chessboard.getAllTakenFields()) {
			final Piece piece = chessboard.getPiece(field);
			if (color == piece.getColor() || isCheckMate(piece.getColor())) {
				continue;
			}
			if (getReachableFields(field, false).contains(kingField)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true, if the player with the given color has lost.
	 */
	public boolean isCheckMate(final PieceColor color) {
		return !chessboard.containsPiece(new King(color));
	}

	/**
	 * Returns true, if there is at least one piece of the given color that can
	 * move.
	 */
	public boolean canMove(final PieceColor color) {
		for (final Field field : chessboard.getAllTakenFields()) {
			Piece piece = chessboard.getPiece(field);
			if (color == piece.getColor() && !getReachableFields(field, true).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check for other pawn movements (first move, taking pieces, en passant).
	 */
	private Set<Field> getAdditionalPawnReachableFields(final Pawn pawn, final Field field) {
		Set<Field> fields = new HashSet<>();

		if (!pawn.isMoved()) {
			fields.addAll(getPawnFirstMoveReachableFields(pawn, field));
		}

		fields.addAll(getPawnTakingReachableFields(pawn, field));
		// TODO: en passant implementieren
		return fields;
	}

	/**
	 * Handles the special taking movements of pawns.
	 */
	private Set<Field> getPawnTakingReachableFields(final Pawn pawn, final Field field) {
		final Set<Field> fields = new HashSet<>();

		for (Direction direction : pawn.getTakingDirections()) {
			Field fieldOnDirection = getFieldOnDirection(field, direction);
			if (fieldOnDirection == null) {
				continue; // field outside the chessboard
			}
			if (!canMoveDiagonally(direction, fieldOnDirection)) {
				continue; // diagonal direction is blocked by other pieces
			}

			Piece pieceOnDirection = getPiece(fieldOnDirection);
			if (pieceOnDirection != null && pieceOnDirection.getColor() != pawn.getColor()) {
				fields.add(fieldOnDirection);
			}

		}

		return fields;
	}

	/**
	 * Returns all additional fields that can be reached by a pawn on his first
	 * move.
	 */
	private Set<Field> getPawnFirstMoveReachableFields(Pawn pawn, Field field) {
		Set<Field> fields = new HashSet<>();

		for (Direction direction : pawn.getDirections()) {
			if (StraightDirections.get().contains(direction)) {
				if (getPiece(getFieldOnDirection(field, direction)) != null) {
					continue; // piece in the way - pawns cannot jump over
				}
				Direction nextDirection = direction.add(direction);
				Field nextField = getFieldOnDirection(field, nextDirection);
				if (nextField != null && getPiece(nextField) == null) {
					// the field has to be empty to move the pawn two fields
					fields.add(nextField);
				}
			}
		}

		return fields;
	}

	/**
	 * Returns fields for long/short castling, if possible.
	 */
	private Set<Field> getCastlingReachableFields(King king, Field field) {
		// TODO: rochade implementieren
		return new HashSet<>();
	}

	/**
	 * A diagonal movement is not possible, if both fields on the path are
	 * blocking it. This method returns true, if a movement to the given field
	 * in the given direction is possible.
	 */
	private boolean canMoveDiagonally(Direction direction, Field field) {
		List<Direction> blockingFieldsDirections = DiagonalDirections.getBlockingDirections(direction);
		if (blockingFieldsDirections == null)
			return true; // not a diagonal movement

		int blockedFields = 0;
		for (Direction directionToBlockingField : blockingFieldsDirections) {
			Field blockingField = getFieldOnDirection(field, directionToBlockingField);
			if (chessboard.getPiece(blockingField) != null)
				blockedFields++;
		}

		return blockedFields < 2;
	}

	/**
	 * Returns the field, which lies in direction from the given field.
	 */
	public Field getFieldOnDirection(final Field field, final Direction direction) {
		return chessboard.getField(field.getX() + direction.getX(), field.getY() + direction.getY());
	}

	public void putPiece(final Field field, final Piece piece) {
		chessboard.putPiece(field, piece);
	}

	@CheckForNull
	public Piece getPiece(final Field field) {
		return chessboard.getPiece(field);
	}

	public void setNextPlayer() {
		ChessPlayer nextPlayer = players.getNext();
		if (nextPlayer.getPlayerColor() == currentPlayer.getPlayerColor()) {
			eventBus.post(new GameFinishedEvent(currentPlayer));
			return;
		}
		if (isCheckMate(nextPlayer.getPlayerColor())) {
			// skip players the are out of the game
			setNextPlayer();
			return;
		}
		currentPlayer = nextPlayer;
	}

	public void setPreviousPlayer() {
		ChessPlayer previousPlayer = players.getPrevious();
		if (isCheckMate(previousPlayer.getPlayerColor())) {
			// skip players the are out of the game
			setPreviousPlayer();
			return;
		}
		currentPlayer = previousPlayer;
	}

	public ChessPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public PieceColor getCurrentPlayerColor() {
		return getCurrentPlayer().getPlayerColor();
	}

	/**
	 * Repaints the current chessboard state on the view.
	 */
	public void print() {
		view.print(chessboard);
	}
}
