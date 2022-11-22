package com.brokenvectors.chess;
import com.brokenvectors.chess.Pieces.*;
import java.util.Stack;

public class BoardHistory {
    private final Board board;
    private final Stack<BoardState> states;
    public BoardHistory(Board board) {
        this.board = board;
        this.states = new Stack<BoardState>();
    }
    public void save() {
        BoardState state = new BoardState();
        // Serialize board into BoardState.
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {
                Piece piece = this.board.getPiece(new Coordinate(j,i));
                if(piece != null) {
                    PieceData pieceData = new PieceData(piece.getColor(), piece.getPieceType(), piece.getPosition().y, piece.getPosition().x);
                    state.pieces.add(pieceData);
                }

            }
        }
        states.add(state);
    }

    public void undo() {
        if(states.size() < 2)
            return;
        states.pop();
        BoardState last = states.lastElement();
        this.board.clear();
        for(PieceData pieceData: last.pieces) {
            Piece piece;
            switch(pieceData.pieceType) {
                case KING:
                    piece = new King(this.board, pieceData.isWhite);
                    break;
                case QUEEN:
                    piece = new Queen(this.board, pieceData.isWhite);
                    break;
                case ROOK:
                    piece = new Rook(this.board, pieceData.isWhite);
                    break;
                case KNIGHT:
                    piece = new Knight(this.board, pieceData.isWhite);
                    break;
                case BISHOP:
                    piece = new Bishop(this.board, pieceData.isWhite);
                    break;
                case PAWN:
                    piece = new Pawn(this.board, pieceData.isWhite);
                    break;
                default:
                    // this will never happen but the compiler obliges me to add a default case
                    System.out.println("what");
                    piece = new Pawn(this.board, pieceData.isWhite);
                    break;
            }
            piece.setPosition(new Coordinate(pieceData.y, pieceData.x));
            this.board.addPiece(piece, piece.getPosition());
        }
        // Deserialize BoardState.
    }
}
