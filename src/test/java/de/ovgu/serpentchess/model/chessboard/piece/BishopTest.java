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

public class BishopTest extends PieceTest {

	public BishopTest(PieceColor color) {
		super(color);
	}

	@Override
	protected Piece createPiece(PieceColor color) {
		return new Bishop(color);
	}

	@Override
	protected boolean expectedMultipleMovementAllowed() {
		return true;
	}

	@Override
	protected int expectedDirectionCount() {
		return 6;
	}

}
