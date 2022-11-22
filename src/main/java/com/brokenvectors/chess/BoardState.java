package com.brokenvectors.chess;

import com.brokenvectors.chess.Pieces.*;

import java.util.Vector;

public class BoardState {
    // BoardState stores PieceData as a 1D array to take up less space.
    // Board uses a 2D array to easily access a piece with two coordinates.

    public Vector<PieceData> pieces;
    public BoardState() {
        pieces = new Vector<PieceData>();
    }
}
