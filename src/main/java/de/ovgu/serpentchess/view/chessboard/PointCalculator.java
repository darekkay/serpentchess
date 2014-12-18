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
package de.ovgu.serpentchess.view.chessboard;

import java.awt.Point;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.ovgu.serpentchess.model.chessboard.Field;

/**
 * Calculates all centers of hexagons to match clicks on the chessboard to the
 * fields.
 */
class PointCalculator {

	public BiMap<Point, Field> calculatePointMap(int viewWidth, int viewHeight) {
		// create a Field out of bounds
		int invalidFieldIdentifier = -1;

		BiMap<Point, Field> pointMap = HashBiMap.create();

		// offset to adjust height of points because of triangles on the upper
		// and lower border
		int offset = (int) ((double) viewHeight / 70);
		double fieldWidth = (double) viewWidth / 26;
		double fieldHeight = ((double) viewHeight - 2 * offset) / 13;

		MatrixToFieldConverter conv = new MatrixToFieldConverter();

		for (int j = 0; j < 15; j++) {
			int mod = j % 2;
			int startIndex = (mod == 0) ? 1 : 0;

			for (int i = startIndex; i < 27; i = i + 2) {
				int x = (int) Math.round(i * fieldWidth);
				int y = (int) Math.round((j - 0.5) * fieldHeight);
				Field f = conv.calculateField(i, j);
				if (f.getX() != 0) {
					pointMap.put(new Point(x, y + offset), f);
				} else {
					pointMap.put(new Point(x, y + offset), new Field(0, invalidFieldIdentifier--));
				}
			}
		}
		return pointMap;
	}
}
