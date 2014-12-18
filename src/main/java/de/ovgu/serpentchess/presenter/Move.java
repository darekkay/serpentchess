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
package de.ovgu.serpentchess.presenter;

import java.io.Serializable;

import javax.annotation.Nonnull;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;

/**
 * Representation of a move form one field to another.
 * 
 * @author Jens Meinicke
 * 
 */
@SuppressWarnings("serial")
public class Move implements Serializable {

	private final Field source;
	private final Piece movedPiece;

	private final Field destination;
	private final Piece takenPiece;

	private final boolean moved;
	private Piece promotionPiece;

	/**
	 * 
	 * @param source
	 *            The source of the move
	 * @param movedPiece
	 *            the piece is moved
	 * @param destination
	 *            The destination of the move
	 */
	public Move(final Field source, final Piece movedPiece, final Field destination) {
		this(source, movedPiece, destination, null);
	}

	/**
	 * 
	 * @param source
	 *            The source of the move
	 * @param movedPiece
	 *            the piece is moved
	 * @param destination
	 *            The destination of the move
	 * @param takenPiece
	 *            The taken piece
	 */
	public Move(final Field source, @Nonnull final Piece movedPiece, final Field destination, final Piece takenPiece) {
		this.movedPiece = movedPiece;
		this.source = source;
		this.destination = destination;
		this.takenPiece = takenPiece;
		this.moved = movedPiece.isMoved();
	}

	/**
	 * Empty Move.
	 */
	public Move() {
		movedPiece = null;
		source = null;
		destination = null;
		takenPiece = null;
		moved = false;
	}

	public final Field getSource() {
		return source;
	}

	public final Field getDestination() {
		return destination;
	}

	public final Piece getTakenPiece() {
		return takenPiece;
	}

	public final boolean isMoved() {
		return moved;
	}

	/**
	 * Defines the piece that replaces the moved piece.
	 */
	public final void setPawnPromotion(final Piece promotionPiece) {
		this.promotionPiece = promotionPiece;
	}

	public final Piece getPromotionPiece() {
		return promotionPiece;
	}

	public final Piece getMovedPiece() {
		return movedPiece;
	}
}
