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

import de.ovgu.serpentchess.util.LoggerFactory;
import de.ovgu.serpentchess.util.SerializationUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Loads the game history.
 * 
 * @author Jens Meinicke
 */
public class HistoryReader {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();

	public History read(final File file) throws IOException {
		Object loadObject = SerializationUtils.loadFromFile(file);
		if (loadObject instanceof History) {
			EVENT_LOGGER.info("History loaded from: " + file.getAbsolutePath());
			return (History) loadObject;
		} else {
			throw new IOException("load obect not instance of History");
		}
	}

}