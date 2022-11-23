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
        state.isWhiteToPlay = this.board.getTurn();
        states.add(state);
    }
    public void load(Board other) {
        BoardState last = states.lastElement();
        other.clear();
        for(PieceData pieceData: last.pieces) {
            Piece piece;
            switch(pieceData.pieceType) {
                case KING:
                    piece = new King(other, pieceData.isWhite);
                    break;
                case QUEEN:
                    piece = new Queen(other, pieceData.isWhite);
                    break;
                case ROOK:
                    piece = new Rook(other, pieceData.isWhite);
                    break;
                case KNIGHT:
                    piece = new Knight(other, pieceData.isWhite);
                    break;
                case BISHOP:
                    piece = new Bishop(other, pieceData.isWhite);
                    break;
                case PAWN:
                    piece = new Pawn(other, pieceData.isWhite);
                    break;
                default:
                    // this will never happen but the compiler obliges me to add a default case
                    System.out.println("what");
                    piece = new Pawn(other, pieceData.isWhite);
                    break;
            }
            piece.setPosition(new Coordinate(pieceData.y, pieceData.x));
            other.addPiece(piece, piece.getPosition());
            other.setTurn(last.isWhiteToPlay);
        }
    }

    public void undo() {
        if(this.states.size() < 2)
            return;
        this.states.pop();
        this.load(this.board);
        // Deserialize BoardState.
    }
}
