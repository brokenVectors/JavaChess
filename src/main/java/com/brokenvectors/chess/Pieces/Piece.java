package com.brokenvectors.chess.Pieces;
import com.brokenvectors.chess.*;
import java.util.Vector;

public abstract class Piece {
     private Coordinate position;
     private final Board board;
     private final boolean isWhite;
     public PieceType pieceType;
     public abstract Vector<Move> getMoves(); // Returns a list of pseudo-legal moves: https://www.chessprogramming.org/Pseudo-Legal_Move
     public void onMove(Move move) {

     }

     public Piece(Board board, boolean isWhite) {
          this.board = board;
          this.isWhite = isWhite;
     }

     public Coordinate getPosition() {
          return position;
     }

     public void setPosition(Coordinate position) {
          this.position = position;
     }

     public Board getBoard() {
          return board;
     }

     public boolean getColor() {
          return isWhite;
     }
     public void onBoardChange(Move move) {

     }

     public PieceType getPieceType() {
          return pieceType;
     }
     public boolean canOccupy(Coordinate target) {
          // Returns if piece occupying given square is legal.
          boolean inBounds = (target.x >= 0) && (target.x < 8) && (target.y >= 0) && (target.y < 8);
          if(!inBounds) return false;
          Piece pieceAtTarget = this.getBoard().getPiece(target);
          boolean noPiece = (pieceAtTarget == null);
          if(noPiece) return true;
          return (pieceAtTarget.getColor() != this.getColor()); // cannot occupy if is friendly piece
     }
     public boolean isBlockedAt(Coordinate target) {
          // Returns if a given square should terminate a given ray.
          // Simply put, if a piece is on a given square.
          boolean inBounds = (target.x >= 0) && (target.x < 8) && (target.y >= 0) && (target.y < 8);
          if(!inBounds) return true;
          Piece pieceAtTarget = this.getBoard().getPiece(target);
          return (pieceAtTarget != null);
     }

     public String toString() {
          // Returns string representation of piece
          String str = "";
          if(isWhite) str += "W";
          else str += "B";
          switch (pieceType) {
               case KING:
                    str += "K";
                    break;
               case QUEEN:
                    str += "Q";
                    break;
               case PAWN:
                    str += "P";
                    break;
               case ROOK:
                    str += "R";
                    break;
               case KNIGHT:
                    str += "N";
                    break;
               case BISHOP:
                    str += "B";
                    break;
          }
          return str;
     }

}
