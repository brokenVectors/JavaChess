package com.brokenvectors.chess;

import com.brokenvectors.chess.Pieces.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());
        board.makeMove(new Move(new Coordinate(1,4), new Coordinate(3,4)));
        System.out.println(board.toString());
        System.out.println(board.getPiece(new Coordinate(0,5)).getMoves().toString());
    }
}
