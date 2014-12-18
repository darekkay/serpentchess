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
package de.ovgu.serpentchess.model.chessboard.direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * All possible diagonal directions.
 */
public final class DiagonalDirections {

	private static final Set<Direction> DIRECTIONS = new HashSet<Direction>() {
		{
			add(new Direction(-1, 1));
			add(new Direction(-2, -1));
			add(new Direction(-1, -2));
			add(new Direction(1, -1));
			add(new Direction(2, 1));
			add(new Direction(1, 2));
		}
	};

	private static final Map<Direction, List<Direction>> BLOCKING_DIRECTIONS = new HashMap<Direction, List<Direction>>() {
		{
			put(new Direction(-1, 1), Arrays.asList(new Direction(0, -1), new Direction(1, 0)));
			put(new Direction(-2, -1), Arrays.asList(new Direction(1, 0), new Direction(1, 1)));
			put(new Direction(-1, -2), Arrays.asList(new Direction(1, 1), new Direction(0, 1)));
			put(new Direction(1, -1), Arrays.asList(new Direction(0, 1), new Direction(-1, 0)));
			put(new Direction(2, 1), Arrays.asList(new Direction(-1, 0), new Direction(-1, -1)));
			put(new Direction(1, 2), Arrays.asList(new Direction(-1, -1), new Direction(0, -1)));
		}
	};

	private DiagonalDirections() {

	}

	public static Set<Direction> get() {
		return Collections.unmodifiableSet(DIRECTIONS);
	}

	public static List<Direction> getBlockingDirections(final Direction direction) {
		return BLOCKING_DIRECTIONS.get(direction);
	}
}
