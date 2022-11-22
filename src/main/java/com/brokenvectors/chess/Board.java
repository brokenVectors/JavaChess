package com.brokenvectors.chess;
import com.brokenvectors.chess.Pieces.*;

import java.util.HashMap;
import java.util.Vector;

public class Board {
    Piece[][] pieces;
    HashMap<Boolean, King> kings;
    private boolean isWhiteToPlay;
    public Board() {
        this.isWhiteToPlay = true;
        this.pieces = new Piece[8][8];
        this.kings = new HashMap<Boolean, King>();

        // Board starting position
        // TODO: add support for FEN
        this.addPiece(new Rook(this, true), new Coordinate(0,0));
        this.addPiece(new Knight(this, true), new Coordinate(0,1));
        this.addPiece(new Bishop(this, true), new Coordinate(0,2));
        this.addPiece(new Queen(this, true), new Coordinate(0,3));
        this.addPiece(new King(this, true), new Coordinate(0,4));
        this.addPiece(new Bishop(this, true), new Coordinate(0,5));
        this.addPiece(new Knight(this, true), new Coordinate(0,6));
        this.addPiece(new Rook(this, true), new Coordinate(0,7));

        this.addPiece(new Pawn(this, true), new Coordinate(1,0));
        this.addPiece(new Pawn(this, true), new Coordinate(1,1));
        this.addPiece(new Pawn(this, true), new Coordinate(1,2));
        this.addPiece(new Pawn(this, true), new Coordinate(1,3));
        this.addPiece(new Pawn(this, true), new Coordinate(1,4));
        this.addPiece(new Pawn(this, true), new Coordinate(1,5));
        this.addPiece(new Pawn(this, true), new Coordinate(1,6));
        this.addPiece(new Pawn(this, true), new Coordinate(1,7));

        this.addPiece(new Rook(this, false), new Coordinate(7,0));
        this.addPiece(new Knight(this, false), new Coordinate(7,1));
        this.addPiece(new Bishop(this, false), new Coordinate(7,2));
        this.addPiece(new Queen(this, false), new Coordinate(7,3));
        this.addPiece(new King(this, false), new Coordinate(7,4));
        this.addPiece(new Bishop(this, false), new Coordinate(7,5));
        this.addPiece(new Knight(this, false), new Coordinate(7,6));
        this.addPiece(new Rook(this, false), new Coordinate(7,7));

        this.addPiece(new Pawn(this, false), new Coordinate(6,0));
        this.addPiece(new Pawn(this, false), new Coordinate(6,1));
        this.addPiece(new Pawn(this, false), new Coordinate(6,2));
        this.addPiece(new Pawn(this, false), new Coordinate(6,3));
        this.addPiece(new Pawn(this, false), new Coordinate(6,4));
        this.addPiece(new Pawn(this, false), new Coordinate(6,5));
        this.addPiece(new Pawn(this, false), new Coordinate(6,6));
        this.addPiece(new Pawn(this, false), new Coordinate(6,7));
    }
    public King getKing(boolean color) {
        // color is true if white, false if black
        return this.kings.get(color);
    }
    public void addPiece(Piece piece, Coordinate coordinate) {
        if(piece instanceof King) {
            // If piece is king, add it to kings hashmap for easy king search
            kings.put(piece.getColor(), (King) piece);
        }
        this.pieces[coordinate.y][coordinate.x] = piece;
        piece.setPosition(coordinate);
    }

    public String toString() {
        // String representation of board
        String str = "";
        // because 2d arrays are always top to bottom, render order must be reversed on y-axis
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                Piece piece = pieces[j][i];
                if(piece == null) str += ".  ";
                else str += piece.toString() + " ";
            }
            str += "\n";
        }
        return str;
    }
    public Piece getPiece(Coordinate coordinate) {
        return this.pieces[coordinate.y][coordinate.x];
    }
    public boolean validateMove(Move move) {
        // Create list of legal moves, check if move is part of that list
        // Check if hypothetical board with the move played doesn't put the king in check
        // If it doesn't validate move, void it
        // The pros use bit-boards, but I'm not smart enough for that
        return true;
    }
    public void makeMove(Move move) {
        // TODO: Move validation
        Piece piece = this.pieces[move.origin.y][move.origin.x];
        if(piece != null && validateMove(move)) {
            boolean turnIsRespected = isWhiteToPlay == piece.getColor();
            if(!turnIsRespected) return;
            // Remove piece from origin square, move it to target square
            this.pieces[move.origin.y][move.origin.x] = null;
            piece.setPosition(move.target);
            piece.onMove();
            this.pieces[move.target.y][move.target.x] = piece;
            isWhiteToPlay = !isWhiteToPlay; // flip turn
        }

    }
    public boolean getTurn() {
        return isWhiteToPlay;
    }
}
