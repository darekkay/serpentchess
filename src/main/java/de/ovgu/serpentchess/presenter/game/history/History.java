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
package de.ovgu.serpentchess.presenter.game.history;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.CheckForNull;

import de.ovgu.serpentchess.presenter.Move;

/**
 * Datastructure for the game history.
 * 
 * @author Jens Meinicke
 * 
 */
@SuppressWarnings("serial")
public class History implements Serializable {

	/**
	 * Points to the last move.
	 */
	private int pointer = 0;
	private final LinkedList<Move> moves = new LinkedList<Move>();

	public final void addMove(final Move move) {
		if (pointer < moves.size()) {
			removeOldMoves(moves);
		}
		moves.add(move);
		pointer++;
	}

	private void removeOldMoves(final LinkedList<Move> moves) {
		while (pointer < moves.size()) {
			moves.removeLast();
		}
	}

	@SuppressWarnings("unchecked")
	public final List<Move> getMoves() {
		final LinkedList<Move> moves = (LinkedList<Move>) this.moves.clone();
		removeOldMoves(moves);
		return Collections.unmodifiableList(moves);
	}

	@CheckForNull
	public final Move undo() {
		if (pointer > 0) {
			pointer--;
		} else {
			return null;
		}
		return moves.get(pointer);
	}

	@CheckForNull
	public final Move redo() {
		if (pointer < moves.size()) {
			pointer++;
		} else {
			return null;
		}
		return moves.get(pointer - 1);
	}

	public void reset() {
		pointer = 0;
		moves.clear();
	}
}
