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
package de.ovgu.serpentchess.util;

import org.apache.log4j.Logger;

/**
 * Factory for Logger objects.
 */
public final class LoggerFactory {
	private static final String EVENT_LOGGER_NAME = "EventLogger";
	private static final String ERROR_LOGGER_NAME = "ErrorLogger";

	private LoggerFactory() {

	}

	public static Logger getEventLogger() {
		return Logger.getLogger(EVENT_LOGGER_NAME);
	}

	public static Logger getErrorLogger() {
		return Logger.getLogger(ERROR_LOGGER_NAME);
	}
}
