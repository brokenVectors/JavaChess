package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;

import java.util.List;
import java.util.Vector;

public class Queen extends Piece {
    public Queen(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.QUEEN;
    }

    @Override
    public Vector<Move> getMoves() {
        // Queen is just Rook + Bishop moves(Vertical/Horizontal + Diagonals)
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();

        // Rook moves(Vertical/Horizontal)
        for(int i = origin.x; i >= 0; i--) {
            Coordinate target = new Coordinate(origin.y, i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = origin.x + 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y, i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }

        for(int i = origin.y; i >= 0; i--) {
            Coordinate target = new Coordinate(i, origin.x);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = origin.y + 1; i < 8; i++) {
            Coordinate target = new Coordinate(i, origin.x);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }

        // Bishop moves(Diagonals)
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y + i, origin.x + i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y - i, origin.x - i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y - i, origin.x + i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y + i, origin.x - i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            if(this.isBlockedAt(target)) break;
        }

        return moves;
    }

    @Override
    public void onMove() {

    }
}
