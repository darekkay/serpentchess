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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

/**
 * Swing utility methods.
 */
public class SwingUtil {
	private static final Logger ERROR_LOGGER = LoggerFactory.getErrorLogger();

	public static void centerWindow(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(screenSize.width / 2 - window.getWidth() / 2, screenSize.height / 2 - window.getHeight() / 2);
	}

	public static void setWindowIcon(Window window, String iconPath) {
		window.setIconImage(Toolkit.getDefaultToolkit().createImage(window.getClass().getResource(iconPath)));
	}

	public static void addComponentToGridBagLayout(Container cont, GridBagLayout gbl, int x, int y, int width, int height, double weightx,
			double weighty, Component component) {
		GridBagConstraints gbc = new GridBagConstraints();

		if (y == 0)
			gbc.insets = new Insets(8, 6, 4, 6);
		else
			gbc.insets = new Insets(4, 6, 4, 6);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(component, gbc);
		cont.add(component);
	}

	public static void addMenuItem(Window window, JMenu menu, String title, String icon, int mnemonic, KeyStroke key,
			ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(title, mnemonic);
		if (key != null)
			menuItem.setAccelerator(key);
		if (icon != null)
			menuItem.setIcon(new ImageIcon(window.getClass().getResource(icon)));
		if (listener != null)
			menuItem.addActionListener(listener);
		menu.add(menuItem);
	}

	public static void setLookAndFeelWindows() {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				ERROR_LOGGER.warn("Setting Windows look and feel failed: " + e.getMessage());
			}
		}
	}

}
