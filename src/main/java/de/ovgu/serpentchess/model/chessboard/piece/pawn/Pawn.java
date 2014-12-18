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
package de.ovgu.serpentchess.model.chessboard.piece.pawn;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;

import java.util.Set;

/**
 * The Pawn piece.
 */
public abstract class Pawn extends Piece {

	public Pawn(PieceColor color) {
		super(color, false);
	}

	/**
	 * Pawns have different directions for taking pieces.
	 */
	public abstract Set<Direction> getTakingDirections();

	@Override
	public String getAbbreviation() {
		return "P";
	}
}
