package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import java.util.Vector;

public class Pawn extends Piece {
    public Pawn(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.PAWN;
    }

    private boolean onStartingSquare() {
        if(this.getColor()) {
            // if pawn is white:
            return this.getPosition().y == 1;
        }
        else {
            return this.getPosition().y == 6;
        }
    }
    @Override
    public Vector<Move> getMoves() {
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();
        // direction inverses movement if pawn is black
        // TODO: add en-passant, promotion, fix diagonal taking because it doesn't work perfectly
        int direction = 1;
        if(!this.getColor()) {
            // if pawn is black:
            direction = -1;
        }
        Coordinate rightDiagonal = new Coordinate(this.getPosition().y + direction, this.getPosition().x + 1);
        Coordinate leftDiagonal = new Coordinate(this.getPosition().y + direction, this.getPosition().x - 1);
        Coordinate twoSquaresInFront = new Coordinate(origin.y + 2 * direction, origin.x);
        Coordinate oneSquareInFront = new Coordinate(origin.y + direction, origin.x);
        if(this.canOccupy(rightDiagonal) && this.getBoard().getPiece(rightDiagonal) != null)
            moves.add(new Move(origin, rightDiagonal));
        if(this.canOccupy(leftDiagonal) && this.getBoard().getPiece(leftDiagonal) != null)
            moves.add(new Move(origin, leftDiagonal));
        if(this.getBoard().getPiece(oneSquareInFront) == null) {
            moves.add(new Move(origin, oneSquareInFront));
            if(this.onStartingSquare() && this.getBoard().getPiece(twoSquaresInFront) == null)
                moves.add(new Move(origin, twoSquaresInFront));
        }
        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove() {

    }
}
