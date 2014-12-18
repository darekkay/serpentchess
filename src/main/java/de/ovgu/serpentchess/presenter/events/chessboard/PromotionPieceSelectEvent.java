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
package de.ovgu.serpentchess.presenter.events.chessboard;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.presenter.Move;

/**
 * Event fired after a piece was selected for promotion.
 */
public class PromotionPieceSelectEvent {

	private final Piece promotionPiece;
	private final Move move;

	public PromotionPieceSelectEvent(final Move move, final Piece promotionPiece) {
		this.move = move;
		this.promotionPiece = promotionPiece;
	}

	public Field getField() {
		return move.getDestination();
	}

	public Piece getPromotionPiece() {
		return promotionPiece;
	}

	public Move getMove() {
		return move;
	}

}
