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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import de.ovgu.serpentchess.presenter.Move;

/**
 * Test for game {@link History}.
 * 
 * @author Jens Meinicke
 * 
 */
public class HistoryTest {

	@Test
	public void addMoveTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		assertEquals("add error", 2, history.getMoves().size());
	}

	@Test
	public void undoTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.undo();
		history.undo();
		assertTrue("undo error", history.getMoves().isEmpty());
	}

	@Test
	public void invalidUndoTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.undo();
		history.undo();
		history.undo();
		assertTrue("invalid undo error", history.getMoves().isEmpty());
	}

	@Test
	public void overwriteTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.undo();
		history.addMove(new Move(new Field(3, 1), new WhitePawn(), new Field(3, 2)));
		history.addMove(new Move(new Field(6, 4), new WhitePawn(), new Field(6, 5)));
		assertEquals("overwrite error", 3, history.getMoves().size());
	}

	@Test
	public void redoTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.undo();
		history.undo();
		history.redo();
		history.redo();
		assertEquals("redo error", 2, history.getMoves().size());
	}

	@Test
	public void invalidRedoTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.undo();
		history.undo();
		history.redo();
		history.redo();
		history.redo();
		assertEquals("invalid redo error", 2, history.getMoves().size());
	}

	@Test
	public void resetTest() {
		History history = new History();
		history.addMove(new Move(new Field(1, 1), new WhitePawn(), new Field(2, 2)));
		history.addMove(new Move(new Field(4, 8), new WhitePawn(), new Field(3, 8)));
		history.reset();
		assertTrue(history.getMoves().isEmpty());
		history.addMove(new Move(new Field(3, 5), new WhitePawn(), new Field(3, 6)));
		assertTrue(history.getMoves().size() == 1);
	}
}
