package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Knight extends Piece {
    public Knight(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.KNIGHT;
    }

    @Override
    public Vector<Move> getMoves() {
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();

        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x + 2)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x - 2)));
        moves.add(new Move(origin, new Coordinate(origin.y + 2, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 2, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x + 2)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x - 2)));
        moves.add(new Move(origin, new Coordinate(origin.y - 2, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 2, origin.x + 1)));

        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove() {

    }
}
