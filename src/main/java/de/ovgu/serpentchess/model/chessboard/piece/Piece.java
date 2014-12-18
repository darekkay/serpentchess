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
package de.ovgu.serpentchess.model.chessboard.piece;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.common.HasAbbreviation;
import de.ovgu.serpentchess.model.common.Movable;

import java.io.Serializable;

/**
 * A chess piece.
 */
public abstract class Piece implements HasAbbreviation, Movable, Serializable {

	private final PieceColor color;
	private final boolean canMoveMultipleSteps;
	private boolean moved = false;

	protected Piece(PieceColor color, boolean canMoveMultipleSteps) {
		this.color = color;
		this.canMoveMultipleSteps = canMoveMultipleSteps;
	}

	public PieceColor getColor() {
		return color;
	}

	@Override
	public boolean canMoveMultipleSteps() {
		return canMoveMultipleSteps;
	}

	@Override
	public boolean isMoved() {
		return moved;
	}

	@Override
	public void setMoved() {
		this.moved = true;
	}

	@Override
	public void resetMoved() {
		moved = false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Piece piece = (Piece) o;

		if (color != piece.color)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return color.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + color + ")";
	}
}
