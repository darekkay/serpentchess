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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldTest {

	@Test
	public void testGetXY() {
		Field field = new Field(4, 6);
		assertEquals(4, field.getX());
		assertEquals(6, field.getY());
	}

	@Test
	public void testEquality() {
		Field field1 = new Field(4, 6);
		Field field2 = new Field(4, 6);

		assertEquals(field1, field2);
	}

	@Test
	public void testAbbreviations() {
		Field middleField = new Field(4, 6);
		assertEquals("D6", middleField.getAbbreviation());

		Field firstField = new Field(1, 1);
		assertEquals("A1", firstField.getAbbreviation());

		Field lastField = new Field(13, 13);
		assertEquals("M13", lastField.getAbbreviation());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBounds() {
		new Field(27, 1);
	}

}
