package com.brokenvectors.chess.Pieces;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import java.util.Vector;

public class King extends Piece {
    public boolean hasMoved = false;
    public King(Board board, boolean isWhite) {
        super(board, isWhite);
        this.pieceType = PieceType.KING;
    }
    public int getCastlingX(boolean isKingside) {
        return isKingside ? 6 : 2;
    }
    public boolean isRookEligibleForCastle(boolean isKingside) {
        int y = this.getColor() ? 0 : 7; // get castling rank
        int x = isKingside ? 7 : 0; // get rook column
        Piece piece = this.getBoard().getPiece(new Coordinate(y, x));
        return (piece instanceof Rook) && !((Rook) piece).hasMoved;
    }
    public Rook getCastlingRook(boolean isKingside) {
        if(this.isRookEligibleForCastle(isKingside)) {
            return (Rook) this.getBoard().getPiece(new Coordinate(this.getColor() ? 0 : 7, isKingside ? 7 : 0));
        }
        return null;
    }
    @Override
    public Vector<Move> getMoves() {
        return this.getMoves(false);
    }

    public Vector<Move> getMoves(boolean excludeCastling) {
        // TODO: Castling
        Vector<Move> moves = new Vector<Move>();
        Coordinate origin = this.getPosition();

        moves.add(new Move(origin, new Coordinate(origin.y, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x + 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x)));
        moves.add(new Move(origin, new Coordinate(origin.y, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y - 1, origin.x - 1)));
        moves.add(new Move(origin, new Coordinate(origin.y + 1, origin.x - 1)));

        // CASTLING CONDITIONS
        // QUEENSIDE / KINGSIDE
        // King must not have moved yet. [ CHECK ]
        // Rooks must not have moved yet. [ CHECK ]
        // Squares from origin to target must not be attacked by any enemy piece. [ CHECK ]
        // There must be no occupied squares between origin and target.
        // PROBLEM: Gets stuck in a recursion loop because this calls Board.isSquareAttacked() which calls Board.getPseudoLegalMoves() which calls this.getMoves()
        // SOLUTION: Add parameter to Board.isSquareAttacked() that excludes castling.
        // TODO: Clean up this awful mess.
        if(!this.hasMoved && !excludeCastling) {
            if(this.isRookEligibleForCastle(true)) {
                int castlingX = getCastlingX(true);
                boolean pathOccupied = this.isBlockedAt(new Coordinate(this.getPosition().y, castlingX - 1)) || this.isBlockedAt(new Coordinate(this.getPosition().y, castlingX));
                boolean pathAttacked = this.getBoard().isSquareAttacked(this.getColor(), new Coordinate(this.getPosition().y, castlingX - 1), true) || this.getBoard().isSquareAttacked(this.getColor(), new Coordinate(this.getPosition().y, castlingX), true);
                Coordinate castlingSquare = new Coordinate(this.getPosition().y, castlingX);
                if(!pathOccupied && !pathAttacked) {
                    Move specialMove = new Move(origin, castlingSquare) {
                        @Override
                        public void special(Board board) {
                            // Move rook to square adjacent to king.
                            Rook castlingRook = getCastlingRook(true);
                            getBoard().removePiece(castlingRook);
                            getBoard().addPiece(castlingRook, new Coordinate(getPosition().y, castlingX - 1));
                        }
                    };
                    moves.add(specialMove);
                }
            }
            if(this.isRookEligibleForCastle(false)) {
                int castlingX = getCastlingX(false);
                boolean pathOccupied = this.isBlockedAt(new Coordinate(this.getPosition().y, castlingX + 1)) || this.isBlockedAt(new Coordinate(this.getPosition().y, castlingX));
                boolean pathAttacked = this.getBoard().isSquareAttacked(this.getColor(), new Coordinate(this.getPosition().y, castlingX - 1), true) || this.getBoard().isSquareAttacked(this.getColor(), new Coordinate(this.getPosition().y, castlingX), true);
                Coordinate castlingSquare = new Coordinate(this.getPosition().y, castlingX);
                if(!pathOccupied && !pathAttacked) {
                    Move specialMove = new Move(origin, castlingSquare) {
                        @Override
                        public void special(Board board) {
                            // Move rook to square adjacent to king.
                            Rook castlingRook = getCastlingRook(false);
                            getBoard().removePiece(castlingRook);
                            getBoard().addPiece(castlingRook, new Coordinate(getPosition().y, castlingX + 1));
                        }
                    };
                    moves.add(specialMove);
                }
            }
        }
        moves.removeIf(move -> (!this.canOccupy(move.target)));
        return moves;
    }

    @Override
    public void onMove(Move move) {
        this.hasMoved = true;
    }
}
