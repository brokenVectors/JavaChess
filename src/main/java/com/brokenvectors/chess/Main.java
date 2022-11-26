package com.brokenvectors.chess;
import com.brokenvectors.chess.Renderer.BoardRenderer;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        board.makeMove("e2-e4");
        board.makeMove("e7-e5");
        board.makeMove("g1-f3");
        board.makeMove("b8-c6");
        board.makeMove("f1-b5");
        board.makeMove("a7-a6");
        System.out.println(board);
        System.out.println(board.getPiece(new Coordinate(4,1)).getMoves());
        //BoardRenderer.run(board);
    }
}
