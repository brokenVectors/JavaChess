package com.brokenvectors.chess;
import com.brokenvectors.chess.Pieces.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        board.makeMove("g2-g4");
        System.out.println(board);
        board.makeMove("e7-e5");
        System.out.println(board);
        board.makeMove("f2-f3");
        System.out.println(board);
        board.makeMove("d8-h4");
        System.out.println(board);
        board.makeMove("e2-e4");
        System.out.println(board);
        BoardRenderer.run(board);
    }
}
