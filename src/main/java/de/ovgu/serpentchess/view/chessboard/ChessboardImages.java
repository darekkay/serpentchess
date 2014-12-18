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

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.ovgu.serpentchess.model.chessboard.PieceColor;
import de.ovgu.serpentchess.model.chessboard.piece.Bishop;
import de.ovgu.serpentchess.model.chessboard.piece.King;
import de.ovgu.serpentchess.model.chessboard.piece.Knight;
import de.ovgu.serpentchess.model.chessboard.piece.Piece;
import de.ovgu.serpentchess.model.chessboard.piece.Queen;
import de.ovgu.serpentchess.model.chessboard.piece.Rook;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.BlackPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.GrayPawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.Pawn;
import de.ovgu.serpentchess.model.chessboard.piece.pawn.WhitePawn;
import de.ovgu.serpentchess.util.LoggerFactory;

/**
 * Manages all images that can be painted on the ChessboardView.
 */
public enum ChessboardImages {

	/**
	 * Singleton enum pattern to load the images only once.
	 */
	INSTANCE;
	private static final String PATH_CHESSBOARD = "/images/chessboard.png";
	private static final String PATH_REACHABLE_FIELD = "/images/reachablefield.png";
	private static final String PATH_BEAT_PIECE = "/images/beatpeace.png";
	private static final String PATH_SELECTED_FIELD = "/images/selectedfield.png";
	private static final String PIECES_PATH = "/images/piece/";
	private static final int PIECE_TO_CHESSBOARD_RATIO = 15;
	private Image chessboardImage;
	private Image scaledChessboardImage;
	private Image reachableFieldImage;
	private Image scaledReachableFieldImage;
	private Image beatPieceImage;
	private Image scaledBeatPieceImage;
	private Image selectedFieldImage;
	private Image scaledSelectedFieldImage;
	private Map<Piece, Image> pieceImages = new HashMap<>();
	private Map<Piece, Image> scaledPieceImages = new HashMap<>();

	private ChessboardImages() {
		loadImages();
	}

	private void loadImages() {
		try {
			loadChessboardImage();
			loadHighlightingImages();
			loadPieceImages();
		} catch (IOException e) {
			LoggerFactory.getErrorLogger().error("Loading images failed:", e);
		}

		LoggerFactory.getEventLogger().debug("Chessboard images loaded.");
	}

	private void loadHighlightingImages() throws IOException {
		loadReachableFieldImage();
		loadBeatPieceImage();
		loadSelectedFieldImage();
	}

	private void loadPieceImages() throws IOException {
		for (PieceColor color : PieceColor.values()) {
			loadPieceImage(new Bishop(color));
			loadPieceImage(new King(color));
			loadPieceImage(new Knight(color));
			loadPieceImage(new Queen(color));
			loadPieceImage(new Rook(color));
		}

		loadPieceImage(new WhitePawn());
		loadPieceImage(new BlackPawn());
		loadPieceImage(new GrayPawn());
	}

	private void loadReachableFieldImage() throws IOException {
		reachableFieldImage = ImageIO.read(this.getClass().getResource(PATH_REACHABLE_FIELD));
		scaledReachableFieldImage = reachableFieldImage;
	}

	private void loadBeatPieceImage() throws IOException {
		beatPieceImage = ImageIO.read(this.getClass().getResource(PATH_BEAT_PIECE));
		scaledBeatPieceImage = beatPieceImage;
	}

	private void loadSelectedFieldImage() throws IOException {
		selectedFieldImage = ImageIO.read(this.getClass().getResource(PATH_SELECTED_FIELD));
		scaledSelectedFieldImage = selectedFieldImage;
	}

	private void loadChessboardImage() throws IOException {
		chessboardImage = ImageIO.read(this.getClass().getResource(PATH_CHESSBOARD));
		scaledChessboardImage = chessboardImage;
	}

	private void loadPieceImage(final Piece piece) throws IOException {
		String imagePath = PIECES_PATH + getPiecePath(piece) + ".png";
		URL imageUrl = this.getClass().getResource(imagePath);
		if (imageUrl != null) {
			Image pieceImage = ImageIO.read(imageUrl);
			pieceImages.put(piece, pieceImage);
			scaledPieceImages.put(piece, pieceImage);
		} else {
			LoggerFactory.getErrorLogger().error("Piece image not found: " + imagePath);
		}
	}

	private String getPiecePath(final Piece piece) {
		String prefix;
		if (piece instanceof Pawn) {
			prefix = Pawn.class.getSimpleName();
		} else {
			prefix = piece.getClass().getSimpleName();
		}

		String fullPath = prefix + "-" + piece.getColor().name().substring(0, 1);
		return fullPath.toLowerCase();
	}

	/**
	 * Returns the chessboard image, which was scaled using scaleImages().
	 */
	public Image getScaledReachableFieldImage() {
		return scaledReachableFieldImage;
	}

	public Image getScaledBeatPieceImage() {
		return scaledBeatPieceImage;
	}

	public Image getScaledSelectedFieldImage() {
		return scaledSelectedFieldImage;
	}

	/**
	 * Returns the chessboard image, which was scaled using scaleImages().
	 */
	public Image getScaledChessboardImage() {
		return scaledChessboardImage;
	}

	/**
	 * Returns the king image, which was scaled using scaleImages().
	 */
	public Image getScaledPieceImage(final Piece piece) {
		return scaledPieceImages.get(piece);
	}

	/**
	 * Scales all images within this class to the given size.
	 */
	public void scaleImages(int chessboardWidth, int chessboardHeight) {
		scaledChessboardImage = chessboardImage.getScaledInstance(chessboardWidth, chessboardHeight, Image.SCALE_SMOOTH);
		int pieceSize = chessboardHeight / PIECE_TO_CHESSBOARD_RATIO;
		scaledReachableFieldImage = reachableFieldImage.getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
		scaledBeatPieceImage = beatPieceImage.getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
		scaledSelectedFieldImage = selectedFieldImage.getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
		for (Map.Entry<Piece, Image> pieceEntry : pieceImages.entrySet()) {
			Image scaledImage = pieceEntry.getValue().getScaledInstance(pieceSize, pieceSize, Image.SCALE_SMOOTH);
			scaledPieceImages.put(pieceEntry.getKey(), scaledImage);
		}
	}
}
