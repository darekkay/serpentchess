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
package de.ovgu.serpentchess.presenter.events.game;

import java.io.File;

/**
 * Event to load a saved game.
 * 
 * @author Jens Meinicke
 * 
 */
public class LoadGameEvent {

	private final File file;

	public LoadGameEvent(final File file) {
		this.file = file;
	}

	public final File getFile() {
		return file;
	}

}
