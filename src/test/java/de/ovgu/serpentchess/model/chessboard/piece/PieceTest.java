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
package de.ovgu.serpentchess.model.chessboard.piece;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public abstract class PieceTest {

	private Piece piece;
	private PieceColor color;

	public PieceTest(PieceColor color) {
		piece = createPiece(color);
		this.color = color;
	}

	@Parameters
	public static Collection<PieceColor[]> getColors() {
		Collection<PieceColor[]> params = new ArrayList<PieceColor[]>();
		params.add(new PieceColor[] { PieceColor.GRAY });
		params.add(new PieceColor[] { PieceColor.WHITE });
		params.add(new PieceColor[] { PieceColor.BLACK });
		return params;
	}

	protected abstract Piece createPiece(PieceColor color);

	protected abstract boolean expectedMultipleMovementAllowed();

	protected abstract int expectedDirectionCount();

	@Test
	public void testColor() {
		assertEquals(color, piece.getColor());
	}

	@Test
	public void testNotMoved() {
		assertFalse(piece.isMoved());
	}

	@Test
	public void testMultiplemovements() {
		assertEquals(expectedMultipleMovementAllowed(), piece.canMoveMultipleSteps());
	}

	@Test
	public void testDirections() {
		assertEquals(expectedDirectionCount(), piece.getDirections().size());
	}

}
