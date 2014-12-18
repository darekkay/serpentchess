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
package de.ovgu.serpentchess.model.chessboard;

import de.ovgu.serpentchess.model.common.HasAbbreviation;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A field on the chessboard, represented as (Letter,Number).
 */
public class Field implements HasAbbreviation, Serializable {

	private final int x;
	private final int y;

	public Field(final int x, final int y) {
		checkArgument(x >= 0 && x <= 26, "The x coordinate cannot be expressed as a letter.");

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String getAbbreviation() {
		return getLetter(x) + y;
	}

	private String getLetter(int ord) {
		// ASCII 65 = A
		return String.valueOf((char) (ord + 64));
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ')';
	}

	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Field field = (Field) o;

		if (x != field.x)
			return false;
		if (y != field.y)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		return 31 * result + y;
	}

	public boolean isValid() {
		return x >= 0 && y >= 0;
	}

}
