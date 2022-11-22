package com.brokenvectors.chess;

import com.brokenvectors.chess.Pieces.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);
        board.makeMove("e2-e4");
        System.out.println(board);
        board.makeMove("d7-d5");
        System.out.println(board);
        board.undo();
        System.out.println(board);
    }
}
