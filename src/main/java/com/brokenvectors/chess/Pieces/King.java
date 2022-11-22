package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import java.util.Vector;

public class King extends Piece {
    public King(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.KING;
    }

    @Override
    public Vector<Move> getMoves() {
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();
        // x: +1 y: 0
        // x: +1 y: +1
        // x: +1 y: -1
        // x: 0 y: +1
        // x: 0 y: -1
        // x: -1 y: 0
        // x: -1 y: -1
        // x: -1 y: +1
        moves.add(new Move(origin, new Coordinate(origin.y + 0, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x + 0)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x + 0)));
        moves.add(new Move(origin, new Coordinate(origin.y + 0, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x - 1)));

        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove() {

    }
}
