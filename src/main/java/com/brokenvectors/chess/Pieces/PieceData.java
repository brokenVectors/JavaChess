package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.PieceType;

public class PieceData {
    public boolean isWhite;
    public PieceType pieceType;
    public int x;
    public int y;

    public PieceData(boolean isWhite, PieceType pieceType, int y, int x) {
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
    }
}
