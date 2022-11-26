package com.brokenvectors.chess;
import com.brokenvectors.chess.Renderer.BoardRenderer;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        //board.makeMove("e2-e4");
        //board.makeMove("e7-e5");
        //board.makeMove("g1-f3");
        //board.makeMove("b8-c6");
        System.out.println(board);
        BoardRenderer.run(board);
    }
}
