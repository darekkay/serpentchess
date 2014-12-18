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
import java.util.Map.Entry;

import com.google.common.collect.BiMap;

import de.ovgu.serpentchess.model.chessboard.Field;

/**
 * Calculates the corresponding fild of a point.
 */
class PointToFieldConverter {

	private final BiMap<Point, Field> map;

	public PointToFieldConverter(BiMap<Point, Field> map) {
		this.map = map;
	}

	public Field calculateClickedFiled(Point clickedPoint) {
		double currentDistance = -1;
		Field currentField = null;
		for (Entry<Point, Field> entry : map.entrySet()) {
			double distance = calculateDistance(clickedPoint, entry.getKey());
			if (currentDistance < 0) {
				currentDistance = distance;
				currentField = entry.getValue();
			} else if (distance < currentDistance) {
				currentDistance = distance;
				currentField = entry.getValue();
			}
		}
		return currentField;
	}

	private double calculateDistance(Point clickedPoint, Point centerPoint) {
		return Point.distance(clickedPoint.getX(), clickedPoint.getY(), centerPoint.getX(), centerPoint.getY());
	}

}
