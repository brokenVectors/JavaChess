package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;

import java.util.List;
import java.util.Vector;

public class Bishop extends Piece {
    public Bishop(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.BISHOP;
    }

    @Override
    public Vector<Move> getMoves() {
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y + i, origin.x + i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            else break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y - i, origin.x - i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            else break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y - i, origin.x + i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            else break;
        }
        for(int i = 1; i < 8; i++) {
            Coordinate target = new Coordinate(origin.y + i, origin.x - i);
            if(this.canOccupy(target)) moves.add(new Move(origin, target));
            else break;
        }
        return moves;
    }

    @Override
    public void onMove() {

    }
}
