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
package de.ovgu.serpentchess.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.direction.DiagonalDirections;
import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.direction.StraightDirections;

public class DirectionTest {

	@Test
	public void testMultiply() {
		Direction direction = new Direction(2, 3);
		Direction multipliedDirection = direction.multiply(2);

		assertEquals(new Direction(2, 3), direction); 
		// direction is immutable, hence it should not change
		assertEquals(new Direction(4, 6), multipliedDirection);
	}

	@Test
	public void testStraightCount() {
		int count = StraightDirections.get().size();
		assertEquals(6, count);
	}

	@Test
	public void testDiagonalCount() {
		int count = DiagonalDirections.get().size();
		assertEquals(6, count);
	}

	@Test
	public void testNoDuplicates() {
		for (Direction d : DiagonalDirections.get()) {
			assertFalse(StraightDirections.get().contains(d));
		}
	}

}
