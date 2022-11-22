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
        // direction inverses movement if pawn is black
        // TODO: add en-passant, promotion
        int direction = 1;
        if(this.getColor() == false) {
            // if pawn is black:
            direction = -1;
        }

        if(!this.hasMoved)
            moves.add(new Move(origin, new Coordinate(origin.y + 2 * direction, origin.x)));
        Coordinate rightDiagonal = new Coordinate(this.getPosition().x + 1, this.getPosition().y + 1 * direction);
        Coordinate leftDiagonal = new Coordinate(this.getPosition().x - 1, this.getPosition().y + 1 * direction);

        if(this.getBoard().getPiece(rightDiagonal) != null && this.canOccupy(rightDiagonal))
            moves.add(new Move(origin, rightDiagonal));
        if(this.getBoard().getPiece(leftDiagonal) != null && this.canOccupy(leftDiagonal))
            moves.add(new Move(origin, leftDiagonal));
        moves.add(new Move(origin, new Coordinate(origin.y + 1 * direction, origin.x)));
        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove() {
        // this is unnecessary, can just check if pawn is on its starting rank
        this.hasMoved = true; // can no longer move two squares
    }
}
