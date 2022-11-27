package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import java.util.Vector;

public class Pawn extends Piece {
    public boolean justMovedTwoSquares = false;
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
        // TODO: add en-passant, promotion
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
        // En passant: pawn must be on same row and adjacent to an enemy *pawn* which has just advanced two squares [ CHECK ]
        // Attacking pawn must already be at the en passant square before enemy pawn advances [ CHECK ]
        // If attacker doesn't immediately play en passant, it is no longer possible [ CHECK ]
        // If a piece is behind the enemy pawn, en passant cannot occur [ CHECK ]
        // PROBLEM: With en passant, the taken piece square doesn't correspond to the attacker's target square
        // SOLUTION: To make room for other special moves(castling, promotion), add a special function to Move
        Coordinate rightSide = new Coordinate(this.getPosition().y, this.getPosition().x + 1);
        Coordinate leftSide = new Coordinate(this.getPosition().y, this.getPosition().x - 1);
        Piece rightSidePiece = this.getBoard().getPiece(rightSide);
        Piece leftSidePiece = this.getBoard().getPiece(leftSide);
        boolean canEnPassantRight = !this.isBlockedAt(rightDiagonal) && rightSidePiece instanceof Pawn && ((Pawn) rightSidePiece).justMovedTwoSquares && ((Pawn) rightSidePiece).onNthRankFromStart(2);
        boolean canEnPassantLeft = !this.isBlockedAt(leftDiagonal) && leftSidePiece instanceof Pawn && ((Pawn) leftSidePiece).justMovedTwoSquares && ((Pawn) leftSidePiece).onNthRankFromStart(2);
        // A pawn can only have one en passant opportunity at once.
        if(canEnPassantLeft || canEnPassantRight) {
            final Piece enPassantPiece = canEnPassantRight ? rightSidePiece : leftSidePiece;
            final Coordinate enPassantSquare = canEnPassantRight ? rightDiagonal : leftDiagonal;
            Move specialMove = new Move(origin, enPassantSquare) {
                @Override
                public void special(Board board) {
                    // Removes pawn previously adjacent to attacking pawn. [ EN PASSANT ]
                    board.removePiece(enPassantPiece);
                    System.out.println(enPassantPiece);
                }
            };
            moves.add(specialMove);
        }


        return moves;
    }

    public boolean onNthRankFromStart(int n) {
        if(this.getColor()) return this.getPosition().y == 1 + n; // if pawn is white...
        else return this.getPosition().y == 6 - n; // if pawn is black...
    }
    @Override
    public void onMove(Move move) {

    }
    @Override
    public void onBoardChange(Move move) {
        if(move.origin.equals(this.getPosition()) && Math.abs(move.target.y - move.origin.y) == 2) {
            this.justMovedTwoSquares = true;
            System.out.println("Pawn moved two squares!");
        }
        else {
            this.justMovedTwoSquares = false;
        }
    }
}
