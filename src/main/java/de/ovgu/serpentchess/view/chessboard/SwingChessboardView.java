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
package de.ovgu.serpentchess.view.chessboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.google.common.collect.BiMap;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

import de.ovgu.serpentchess.model.chessboard.Chessboard;
import de.ovgu.serpentchess.model.chessboard.Field;
import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.presenter.chessboard.IChessboardPresenter;
import de.ovgu.serpentchess.presenter.events.chessboard.FieldClickEvent;
import de.ovgu.serpentchess.util.LoggerFactory;

/**
 * Swing implementation for the ChessboardView.
 */
public class SwingChessboardView extends JPanel implements ChessboardView {

	private static final Logger EVENT_LOGGER = LoggerFactory.getEventLogger();
	private static final Logger ERROR_LOGGER = LoggerFactory.getErrorLogger();

	private static final Color COLOR_BACKGROUND = new Color(255, 255, 255);
	private static final Color COLOR_BORDER = new Color(0, 0, 0);
	private static final double CHESSBOARD_WIDTH_HEIGHT_RATIO = 1.125;

	private final EventBus eventBus;
	private final IChessboardPresenter presenter;
	private Chessboard chessboard;
	private BiMap<Point, Field> pointMap;

	private PointToFieldConverter converter;

	@Inject
	public SwingChessboardView(EventBus eventBus, IChessboardPresenter chessboardPresenter) {
		this.presenter = chessboardPresenter;
		this.eventBus = eventBus;
		initView();
	}

	private void initView() {
		setPreferredSize(new Dimension(400, 400));
		setSize(new Dimension(400, 400));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLocation(new Point(0, 0));

		scaleImages();
		calculatePoints();
		addListerner();
	}

	private void calculatePoints() {
		pointMap = new PointCalculator().calculatePointMap(getChessboardWidth(), getChessboardHeight());
		converter = new PointToFieldConverter(pointMap);
	}

	private void addListerner() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					handleMouseClicked(e.getPoint());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				scaleImages();
				calculatePoints();
				repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	private void scaleImages() {
		ChessboardImages.INSTANCE.scaleImages(getChessboardWidth(), getChessboardHeight());
	}

	/**
	 * Calculate the selected {@link Field}.
	 */
	protected void handleMouseClicked(Point point) {
		Field field = converter.calculateClickedFiled(point);
		if (field != null && field.isValid()) {
			EVENT_LOGGER.debug("Field " + field.getAbbreviation() + " clicked");
			eventBus.post(new FieldClickEvent(field));
		}
	}

	public void print(Chessboard chessboard) {
		this.chessboard = chessboard;
		paintAll(getGraphics());
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		graphics.clearRect(0, 0, getWidth(), getHeight());
		graphics.setColor(COLOR_BACKGROUND);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		Image chessboardImage = ChessboardImages.INSTANCE.getScaledChessboardImage();
		graphics.drawImage(chessboardImage, 0, 0, null);
		if (chessboard != null) {

			paintReachableFields(graphics);

			paintPieces(graphics);
		}
		drawBorder(graphics);
	}

	private void paintReachableFields(final Graphics graphics) {
		Field selectedField = presenter.getSelectedField();
		Piece selectedPeace = chessboard.getPiece(selectedField);
		if (selectedPeace == null) {
			return;
		}
		PieceColor pieceColor = chessboard.getPiece(selectedField).getColor();
		if (selectedField != null) {
			final Image selectedImage = ChessboardImages.INSTANCE.getScaledSelectedFieldImage();
			final Point selectedPoint = getDrawPoint(selectedField);
			graphics.drawImage(selectedImage, selectedPoint.x - selectedImage.getWidth(null) / 2,
					selectedPoint.y - selectedImage.getHeight(null) / 2, null);
			Image reachableFieldImage = ChessboardImages.INSTANCE.getScaledReachableFieldImage();
			Image beatPieceImage = ChessboardImages.INSTANCE.getScaledBeatPieceImage();
			if (reachableFieldImage == null) {
				ERROR_LOGGER.error("Scaled image is nof found for selected field.");
				return;
			}
			if (beatPieceImage == null) {
				ERROR_LOGGER.error("Scaled image is nof found for beat piece.");
				return;
			}
			for (Field reachableField : presenter.getReachableFields(selectedField)) {
				final Point point = getDrawPoint(reachableField);
				final Piece piece = chessboard.getPiece(reachableField);
				final Image image;
				if (piece == null || chessboard.getPiece(reachableField).getColor() == pieceColor) {
					image = reachableFieldImage;
				} else {
					image = beatPieceImage;
				}
				graphics.drawImage(image, point.x - image.getWidth(null) / 2, point.y - image.getHeight(null) / 2, null);
			}
		}
	}

	private void paintPieces(final Graphics graphics) {
		for (Field field : pointMap.values()) {
			Piece piece = chessboard.getPiece(field);
			if (piece != null) {
				Point point = getDrawPoint(field);
				Image pieceImage = ChessboardImages.INSTANCE.getScaledPieceImage(piece);
				if (pieceImage != null) {
					graphics.drawImage(pieceImage, point.x - pieceImage.getWidth(null) / 2, point.y - pieceImage.getHeight(null) / 2, null);
				} else {
					ERROR_LOGGER.error("Scaled image is null for piece: " + piece);
				}
			}
		}
	}

	private void drawBorder(Graphics graphics) {
		graphics.setColor(COLOR_BORDER);
		graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	private Point getDrawPoint(Field field) {
		return pointMap.inverse().get(field);
	}

	private int getChessboardWidth() {
		int sideSize = Math.min((int) (getWidth() / CHESSBOARD_WIDTH_HEIGHT_RATIO), getHeight());
		return (int) (sideSize * CHESSBOARD_WIDTH_HEIGHT_RATIO);
	}

	private int getChessboardHeight() {
		return Math.min((int) (getWidth() / CHESSBOARD_WIDTH_HEIGHT_RATIO), getHeight());
	}

	/** Prints a circle on the fields position. */
	@SuppressWarnings("unused")
	private void debugField(Graphics graphics, Field field) {
		Point fieldPoint = getDrawPoint(field);
		if (fieldPoint == null) {
			ERROR_LOGGER.error(field + " not found");
			return;
		}
		graphics.drawOval(fieldPoint.x - 10, fieldPoint.y - 10, 20, 20);
		graphics.drawString(field.getAbbreviation(), fieldPoint.x - 10, fieldPoint.y - 10);
	}

	/**
	 * Draws all field names (A1, A2, ..) on the board. Used only for debugging
	 * purpose.
	 */
	@SuppressWarnings("unused")
	private void drawFieldAbbreviations(Graphics graphics, boolean printNumbers) {
		graphics.setColor(Color.BLACK);
		for (Field field : pointMap.values()) {
			Point fieldPoint = getDrawPoint(field);
			if (printNumbers) {
				graphics.drawString("(" + field.getX() + "," + field.getY() + ")", fieldPoint.x - 7, fieldPoint.y + 3);
			} else {
				graphics.drawString(field.getAbbreviation(), fieldPoint.x - 7, fieldPoint.y + 3);
			}
		}
	}

	/**
	 * Draw the field borders.
	 */
	@SuppressWarnings("unused")
	private void debugFieldBorders(Graphics graphics) {
		graphics.setColor(new Color(255, 0, 0));
		Field field = converter.calculateClickedFiled(new Point(0, 0));
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				Field actualField = converter.calculateClickedFiled(new Point(j, i));
				if (!actualField.equals(field)) {
					graphics.drawLine(j + 1, i, j - 1, i);
					field = actualField;
				}
			}
		}
	}
}
