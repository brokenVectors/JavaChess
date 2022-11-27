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
        Piece rightSidePiece = null;
        Piece leftSidePiece = null;
        if(this.canOccupy(rightSide)) rightSidePiece = this.getBoard().getPiece(rightSide);
        if(this.canOccupy(leftSide)) leftSidePiece = this.getBoard().getPiece(leftSide);
        boolean canEnPassantRight = !this.isBlockedAt(rightDiagonal) && rightSidePiece instanceof Pawn && ((Pawn) rightSidePiece).justMovedTwoSquares && ((Pawn) rightSidePiece).onNthRankFromStart(2);
        boolean canEnPassantLeft = !this.isBlockedAt(leftDiagonal) && leftSidePiece instanceof Pawn && ((Pawn) leftSidePiece).justMovedTwoSquares && ((Pawn) leftSidePiece).onNthRankFromStart(2);
        if(canEnPassantRight) {
            System.out.println("ELIGIBLE FOR EN PASSANT RIGHT");
            Piece finalRightSidePiece = rightSidePiece;
            moves.add(new Move(origin, rightDiagonal) {
                @Override
                public void special(Board board) {
                    // Removes pawn previously adjacent to attacking pawn. [ EN PASSANT ]
                    System.out.println("EN PASSANT");
                    board.removePiece(finalRightSidePiece);
                    System.out.println(finalRightSidePiece);
                }
            });
        }
        if(canEnPassantLeft) {
            System.out.println("ELIGIBLE FOR EN PASSANT LEFT");
            Piece finalLeftSidePiece = leftSidePiece;
            Move specialMove = new Move(origin, leftDiagonal) {
                @Override
                public void special(Board board) {
                    // Removes pawn previously adjacent to attacking pawn. [ EN PASSANT ]
                    System.out.println("EN PASSANT");
                    board.removePiece(finalLeftSidePiece);
                    System.out.println(finalLeftSidePiece);
                }
            };
            moves.add(specialMove);
            //specialMove.special(this.getBoard());
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
