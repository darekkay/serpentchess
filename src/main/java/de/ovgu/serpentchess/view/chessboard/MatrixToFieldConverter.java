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

import de.ovgu.serpentchess.model.chessboard.Field;

/**
 * Calculates fields for the matrix points if no valid field Field(0,0) is
 * returned.
 */
class MatrixToFieldConverter {

	public Field calculateField(int i, int j) {
		// TODO @Anna better solution?
		if (j == 0 || i == 0) {
			return new Field(0, 0);
		}

		int x = j;
		int y = 0;
        switch (j) {
            case 1:
                y = calculateYUpperSide(6, 20, i);
                break;
            case 2:
                y = calculateYUpperSide(5, 21, i);
                break;
            case 3:
                y = calculateYUpperSide(4, 22, i);
                break;
            case 4:
                y = calculateYUpperSide(3, 23, i);
                break;
            case 5:
                y = calculateYUpperSide(2, 24, i);
                break;
            case 6:
                y = calculateYUpperSide(1, 25, i);
                break;
            case 7:
                y = calculateYLowerSide(2, 24, i);
                break;
            case 8:
                y = calculateYLowerSide(3, 23, i);
                break;
            case 9:
                y = calculateYLowerSide(4, 22, i);
                break;
            case 10:
                y = calculateYLowerSide(5, 21, i);
                break;
            case 11:
                y = calculateYLowerSide(6, 20, i);
                break;
            case 12:
                y = calculateYLowerSide(7, 19, i);
                break;
            case 13:
                y = calculateYLowerSide(8, 18, i);
                break;
        }

		if (y == 0) {
			return new Field(0, 0);
		} else {
			return new Field(x, y);
		}

	}

	private int calculateYUpperSide(int min, int max, int value) {
		int y = 0;

		if (value < min) {
			y = 0;
		} else if (value > max) {
			y = 0;
		} else {
			int counter = 0;
			for (int k = min; k <= max; k = k + 2) {
				counter++;
				if (k == value) {
					y = counter;
				}
			}
		}

		return y;
	}

	private int calculateYLowerSide(int min, int max, int value) {
		int y = 0;

		if (value < min) {
			y = 0;
		} else if (value > max) {
			y = 0;
		} else {
			int counter = min;
			for (int k = min; k <= max; k += 2) {
				if (k == value) {
					y = counter;
				}
				counter++;
			}
		}
		return y;
	}

}
