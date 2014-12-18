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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * Util class for saving/loading serializable objects.
 */
public class SerializationUtils {

	private static final Logger ERROR_LOGGER = LoggerFactory.getErrorLogger();

	public static void saveToFile(final File file, final Object obj) throws IOException {
		final FileOutputStream fileOS = new FileOutputStream(file);
		try (DataOutputStream dataOS = new DataOutputStream(fileOS)) {
			dataOS.write(serialize(obj));
			dataOS.flush();
		}
	}

	public static Object loadFromFile(final File file) throws IOException {
		final FileInputStream fileIS = new FileInputStream(file);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (DataInputStream dataIS = new DataInputStream(fileIS); DataOutputStream dataOS = new DataOutputStream(outputStream)) {
			final byte[] buffer = new byte[0xFFFF];
			int len = dataIS.read(buffer);
			while (len != -1) {
				dataOS.write(buffer, 0, len);
				len = dataIS.read(buffer);
			}
		} catch (IOException e) {
			ERROR_LOGGER.error("Error while loading file: ", e);
		}
		return deserialize(outputStream.toByteArray());
	}

	private static byte[] serialize(final Object obj) throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(obj);
		return bos.toByteArray();
	}

	private static Object deserialize(final byte[] bytes) throws IOException {
		try {
			final ObjectInputStream objectIS = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return objectIS.readObject();
		} catch (ClassNotFoundException e) {
			ERROR_LOGGER.error("Error while deserializing object: ", e);
		}
		return null;
	}

}
