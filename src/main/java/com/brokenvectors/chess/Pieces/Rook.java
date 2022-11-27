package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;

import java.util.List;
import java.util.Vector;

public class Rook extends Piece {
    public boolean hasMoved = false; // for castling
    public Rook(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.ROOK;
    }

    @Override
    public Vector<Move> getMoves() {
        // TODO: this code is still repetitive but i doubt i can do anything about it
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();

        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y, origin.x + i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y, origin.x - i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y + i, origin.x);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y - i, origin.x);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        return moves;
    }

    @Override
    public void onMove(Move move) {
        this.hasMoved = true;
    }
}
