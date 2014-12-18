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

/**
 * Definition of a direction as a vector. This class is immutable.
 */
public class Direction {

	private final int x;
	private final int y;

	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Direction multiply(int factor) {
		return new Direction(x * factor, y * factor);
	}

	public Direction add(Direction direction) {
		return new Direction(x + direction.x, y + direction.y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Direction direction = (Direction) o;

		if (x != direction.x)
			return false;
		if (y != direction.y)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		return 31 * result + y;
	}
}
