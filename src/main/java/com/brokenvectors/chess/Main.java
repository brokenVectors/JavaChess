package com.brokenvectors.chess;

import com.brokenvectors.chess.Pieces.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        board.makeMove("e2-e4");
        System.out.println(board);
        board.makeMove("e7-e5");
        System.out.println(board);
        board.makeMove("d1-h5");
        System.out.println(board);
        board.makeMove("f7-f6");
        System.out.println(board);
        board.makeMove("h5-h7");
        System.out.println(board);
    }
}
