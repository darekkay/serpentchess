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

import java.util.ArrayList;

/**
 * 
 * Circular list implementation. Contains a pointer to a current object.
 * 
 */
public class CircularArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	private int pointer = 0;

	public E get(int index) {
		if (index == -1) {
			index = size() - 1;
		} else if (index == size()) {
			index = 0;
		} else if (index > size()) {
			index = index % size();
		}

		return super.get(index);
	}

	public E getNext() {
		pointer++;
		return get(pointer);
	}

	public E getPrevious() {
		pointer--;
		return get(pointer);
	}

	public E getCurrent() {
		return get(pointer);
	}
}