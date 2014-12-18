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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.Field;

public class MatrixToFieldConverterTest {
	@Test
	public void testCalculate() {
		MatrixToFieldConverter conv = new MatrixToFieldConverter();

		// invalid field: no hexagon
		assertEquals(new Field(0, 0), conv.calculateField(2, 1));
		assertEquals(new Field(0, 0), conv.calculateField(2, 13));
		assertEquals(new Field(0, 0), conv.calculateField(25, 2));
		assertEquals(new Field(0, 0), conv.calculateField(25, 12));

		// valid field: hexagon
		assertEquals(new Field(1, 1), conv.calculateField(6, 1));
		assertEquals(new Field(1, 8), conv.calculateField(20, 1));
		assertEquals(new Field(6, 1), conv.calculateField(1, 6));
		assertEquals(new Field(6, 13), conv.calculateField(25, 6));
		assertEquals(new Field(13, 8), conv.calculateField(8, 13));
		assertEquals(new Field(13, 13), conv.calculateField(18, 13));
		assertEquals(new Field(7, 7), conv.calculateField(12, 7));
	}
}
