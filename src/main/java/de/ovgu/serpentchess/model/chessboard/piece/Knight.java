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

import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.PieceColor;

import java.util.HashSet;
import java.util.Set;

/**
 * The Knight piece.
 */
public class Knight extends Piece {

	@SuppressWarnings("serial")
	private static final Set<Direction> DIRECTIONS = new HashSet<Direction>() {
		{
			add(new Direction(1, -2));
			add(new Direction(2, -1));
			add(new Direction(3, 1));
			add(new Direction(3, 2));
			add(new Direction(2, 3));
			add(new Direction(1, 3));
			add(new Direction(-1, 2));
			add(new Direction(-2, 1));
			add(new Direction(-3, -1));
			add(new Direction(-3, -2));
			add(new Direction(-2, -3));
			add(new Direction(-1, -3));
		}
	};

	public Knight(PieceColor color) {
		super(color, false);
	}

	@Override
	public String getAbbreviation() {
		return "N";
	}

	public Set<Direction> getDirections() {
		return DIRECTIONS;
	}
}
