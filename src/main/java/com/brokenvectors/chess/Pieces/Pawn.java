package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import java.util.List;
import java.util.Vector;

public class Pawn extends Piece {
    private boolean hasMoved;
    public Pawn(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.PAWN;
        this.hasMoved = false;
    }

    @Override
    public Vector<Move> getMoves() {
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();
        // multiplier inverses movement if pawn is black
        // TODO: add diagonal taking, en-passant
        int mult = 1;
        if(this.getColor() == false) {
            // if pawn is black:
            mult = -1;
        }

        if(!this.hasMoved) {
            moves.add(new Move(origin, new Coordinate(origin.y + 2 * mult, origin.x)));
        }
        moves.add(new Move(origin, new Coordinate(origin.y + 1 * mult, origin.x)));
        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove() {
        // this is unnecessary, can just check if pawn is on its starting rank
        this.hasMoved = true; // can no longer move two squares
    }
}
