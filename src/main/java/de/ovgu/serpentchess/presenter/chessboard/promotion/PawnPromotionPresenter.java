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
package de.ovgu.serpentchess.presenter.chessboard.promotion;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.direction.Direction;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.Pawn;
import de.ovgu.serpentchess.presenter.Move;
import de.ovgu.serpentchess.presenter.chessboard.ChessboardPresenter;
import de.ovgu.serpentchess.view.chessboard.promotion.PawnPromotionView;

/**
 * Presenter for the pawn promotion.
 */
public class PawnPromotionPresenter {

	private final PawnPromotionView view;
	protected ChessboardPresenter chessboardPresenter;

	@Inject
	public PawnPromotionPresenter(PawnPromotionView view, ChessboardPresenter chessboardPresenter, EventBus eventBus) {
		this.view = view;
		this.chessboardPresenter = chessboardPresenter;
		eventBus.register(this);
	}

	public boolean checkPromotionRequired(Move move) {
		Field field = move.getDestination();
		Piece piece = chessboardPresenter.getPiece(field);
		boolean result = false;
		if (piece instanceof Pawn) {
			for (PieceColor color : PieceColor.values()) {
				if (color != piece.getColor())
					result = result || isFieldOnBaseLine(field, color);
			}
		}
		return result;
	}

	public void askForPromotionPiece(Move move) {
		view.showView(move, chessboardPresenter.getPiece(move.getDestination()).getColor());
	}

	private boolean isFieldOnBaseLine(Field field, PieceColor color) {
		Field baseLineField;
		Direction direction;

		switch (color) {
		case WHITE: {
			baseLineField = new Field(1, 1);
			direction = new Direction(0, 1);
			break;
		}
		case GRAY: {
			baseLineField = new Field(6, 13);
			direction = new Direction(1, 0);
			break;
		}
		case BLACK: {
			baseLineField = new Field(13, 8);
			direction = new Direction(-1, -1);
			break;
		}
		default:
			throw new IllegalArgumentException("Unknown color: " + color);
		}

		while (baseLineField != null) {
			if (field.equals(baseLineField))
				return true;

			baseLineField = chessboardPresenter.getFieldOnDirection(baseLineField, direction);
		}

		return false;
	}

}
