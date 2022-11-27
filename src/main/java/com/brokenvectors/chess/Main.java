package com.brokenvectors.chess;
import com.brokenvectors.chess.Renderer.BoardRenderer;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        BoardRenderer.run(board);
    }
}
