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

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.util.SerializationUtils;

/**
 * Saves the game history.
 * 
 * @author Jens Meinicke
 */
public class HistoryWriter {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();

	private final History history;

	public HistoryWriter(final History history) {
		this.history = history;
	}

	public final void write(final File file) throws IOException {
		SerializationUtils.saveToFile(file, history);
		EVENT_LOGGER.info("History saved to: " + file.getAbsolutePath());
	}
}
