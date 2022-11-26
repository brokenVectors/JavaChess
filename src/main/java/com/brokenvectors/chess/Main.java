package com.brokenvectors.chess;
import com.brokenvectors.chess.Renderer.BoardRenderer;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        System.out.println(board);
        board.makeMove("e2-e4");
        board.makeMove("e7-e5");
        board.makeMove("f1-c4");
        board.makeMove("b8-c6");
        board.makeMove("d1-h5");
        board.makeMove("a7-a6");
        board.makeMove("h5-f7");
        System.out.println(board);
        System.out.println(board.getPiece(new Coordinate("f7")).getMoves());

        //BoardRenderer.run(board);
    }
}
