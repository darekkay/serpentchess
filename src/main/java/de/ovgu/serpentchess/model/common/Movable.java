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
package de.ovgu.serpentchess.model.common;

import de.ovgu.serpentchess.model.chessboard.direction.Direction;

import java.util.Set;

/**
 * Any object that can be moved on the board.
 */
public interface Movable {

	/**
	 * Returns all directions an object can ever move to.
	 */
	Set<Direction> getDirections();

	/**
	 * Returns whether an object can move multiple steps.
	 */
	boolean canMoveMultipleSteps();

	/**
	 * Returns whether the object was moved at least once.
	 */
	boolean isMoved();

	/**
	 * Marks the object as not moved.
	 */
	void resetMoved();

	/**
	 * Marks the object as moved.
	 */
	void setMoved();
}
