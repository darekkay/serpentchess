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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

import de.ovgu.serpentchess.model.chessboard.piece.Piece;

/**
 * A data structure to map pieces to fields.
 */
public class Chessboard {

	private final Map<Field, Piece> fieldMap = new HashMap<>();

	public void createField(int x, int y) {
		fieldMap.put(new Field(x, y), null);
	}

	@CheckForNull
	public Field getField(int x, int y) {
		for (Field field : fieldMap.keySet()) {
			if (field.equals(x, y)) {
				return field;
			}
		}
		return null;
	}

	@CheckForNull
	public Piece getPiece(final Field field) {
		return fieldMap.get(field);
	}

	public void putPiece(Field field, Piece piece) {
		checkNotNull(field, "Field may not be null.");
		checkNotNull(piece, "Piece may not be null.");
		checkFieldExists(field);

		fieldMap.put(field, piece);
	}

	public void removePiece(Field field) {
		checkFieldExists(field);
		fieldMap.put(field, null);
	}

	public void clearPieces() {
		for (Map.Entry<Field, Piece> entry : fieldMap.entrySet()) {
			fieldMap.put(entry.getKey(), null);
		}
	}

	public int getFieldCount() {
		return fieldMap.keySet().size();
	}

	public int getPieceCount() {
		int pieceCount = 0;

		for (Map.Entry<Field, Piece> entry : fieldMap.entrySet()) {
			if (entry.getValue() != null) {
				pieceCount++;
			}
		}

		return pieceCount;
	}

	private void checkFieldExists(final Field field) {
		if (!fieldMap.containsKey(field)) {
			throw new IllegalStateException("The field (" + field.getX() + "," + field.getY() + ") doesn't exist.");
		}
	}

	public boolean containsPiece(Piece piece) {
		checkNotNull(piece);
		for (Piece pieceOnTheBoard : fieldMap.values())
			if (piece.equals(pieceOnTheBoard))
				return true;

		return false;
	}

	/**
	 * Returns all fields, that contain a piece.
	 */
	public List<Field> getAllTakenFields() {
		List<Field> fields = new ArrayList<>();
		for (Map.Entry<Field, Piece> entry : fieldMap.entrySet()) {
			if (entry.getValue() != null)
				fields.add(entry.getKey());
		}
		return fields;
	}

	/**
	 * Returns the first field where the given piece is found on, or null
	 * otherwise.
	 */
	public Field getField(Piece piece) {
		checkNotNull(piece);
		for (Map.Entry<Field, Piece> entry : fieldMap.entrySet()) {
			if (piece.equals(entry.getValue()))
				return entry.getKey();
		}
		return null;
	}
}
