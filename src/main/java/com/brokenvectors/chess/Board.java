package com.brokenvectors.chess;
import com.brokenvectors.chess.Pieces.*;

import java.util.HashMap;
import java.util.Vector;

public class Board {
    private Piece[][] pieces;
    private final HashMap<Boolean, King> kings;
    private BoardHistory history;
    private boolean isWhiteToPlay;
    public Board() {
        this.isWhiteToPlay = true;
        this.pieces = new Piece[8][8];
        this.history = new BoardHistory(this);
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
        this.history.save();
    }
    public void clear() {
        this.pieces = new Piece[8][8];
        this.kings.clear();
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
        StringBuilder str = new StringBuilder();
        // because 2d arrays are always top to bottom, render order must be reversed on y-axis
        for(int j = 7; j >= 0; j--) {
            for(int i = 0; i < 8; i++) {
                Piece piece = pieces[j][i];
                if(piece == null) str.append(".  ");
                else str.append(piece.toString()).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
    public Piece getPiece(Coordinate coordinate) {
        return this.pieces[coordinate.y][coordinate.x];
    }
    public Vector<Move> getPseudoLegalMoves(boolean color) {
        Vector<Move> pseudoLegalMoves = new Vector<Move>();
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {
                Piece piece = this.pieces[j][i];
                if(piece != null && piece.getColor() == color) {
                    pseudoLegalMoves.addAll(piece.getMoves());
                }
            }
        }
        return pseudoLegalMoves;
    }
    public Vector<Move> getLegalMoves(boolean color) {
        Vector<Move> pseudoLegalMoves = this.getPseudoLegalMoves(color);
        Vector<Move> legalMoves = new Vector<Move>();
        Board imaginary = new Board();
        //this.history.load(imaginary); // not loading correctly

        for(Move move: pseudoLegalMoves) {
            this.history.load(imaginary);
            imaginary.movePiece(move);
            imaginary.setTurn(this.isWhiteToPlay); // don't know why, but BoardHistory won't set turn correctly
            boolean legal = !imaginary.inCheck(imaginary.getTurn());
            if(legal){
                legalMoves.add(move);
            }
            imaginary.history.undo();
        }
        return legalMoves;
    }
    public boolean inCheck(boolean color) {
        // Check if the enemy player has any pieces that can attack the king.
        // Since it doesn't matter if the move is actually legal, just check pseudo legal moves.
        // Example: a pinned piece can still check the enemy king.
        Vector<Move> pseudoLegalMoves = this.getPseudoLegalMoves(!color);
        for(Move move: pseudoLegalMoves) {
            if(move.target.equals(this.kings.get(color).getPosition())) return true;
        }
        return false;
    }
    public boolean validateMove(Move move, boolean color) {
        // Create list of legal moves, check if move is part of that list
        // Check if hypothetical board with the move played doesn't put the king in check(check if enemy's legal moves has king's square as target)
        // If it doesn't validate move, void it
        // TODO: see getLegalMoves()
        for(Move m: this.getLegalMoves(this.getTurn())) {
            boolean moveFound = (move.origin.x == m.origin.x) && (move.origin.y == m.origin.y) && (move.target.x == m.target.x) && (move.target.y == m.target.y);
            if(moveFound) {
                return true;
            }
        }
        return false;
    }
    public boolean makeMove(String moveString) {
        return this.makeMove(new Move(moveString));
    }
    private void movePiece(Move move) {
        // moves a piece without any validation
        // Remove piece from origin square, move it to target square
        Piece piece = this.getPiece(move.origin);
        piece.setPosition(move.target);
        this.pieces[move.origin.y][move.origin.x] = null;
        this.pieces[move.target.y][move.target.x] = piece;
        this.history.save();
    }
    public void detectGameOver() {
        // Detects stalemate or checkmate.
        if(this.getLegalMoves(true).size() == 0 && this.isWhiteToPlay) {
            if(this.inCheck(true)) {
                System.out.println("White is in checkmate!");
            } else {
                System.out.println("Stalemate!");
            }
        }

        if(this.getLegalMoves(false).size() == 0 && !this.isWhiteToPlay) {
            if(this.inCheck(false)) {
                System.out.println("Black is in checkmate!");
            } else {
                System.out.println("Stalemate!");
            }
        }
    }
    public void undo() {
        this.history.undo();
    }
    public boolean makeMove(Move move) {
        // Returns true if move could be made, otherwise returns false
        // TODO: Move validation
        Piece piece = this.pieces[move.origin.y][move.origin.x];
        if(piece != null && validateMove(move, this.getTurn())) {
            boolean turnIsRespected = isWhiteToPlay == piece.getColor();
            if(!turnIsRespected) return false; // redundant?
            // Remove piece from origin square, move it to target square
            this.movePiece(move);
            piece.onMove();
            isWhiteToPlay = !isWhiteToPlay; // flip turn
            this.detectGameOver();
            return true;
        }
        else {
            System.out.println("Couldn't make move: Piece either doesn't exist or move is illegal, or turn wasn't respected");
            return false;
        }
    }
    public boolean getTurn() {
        return isWhiteToPlay;
    }
    public void setTurn(boolean isWhiteToPlay) {
        this.isWhiteToPlay = isWhiteToPlay;
    }
}
