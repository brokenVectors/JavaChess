package com.brokenvectors.chess;

public class Coordinate {
    // Col 1 -> 8 = A -> H (left to right)
    // Row 1 -> 8 = 1 -> 8 (low to high, white toward black)
    public int y;
    public int x;
    public Coordinate(int y, int x) {
        this.y = y;
        this.x = x;
    }
    public static boolean isInBounds(int y, int x) {
        return ( (y > 0) && (y < 8) ) && ( (x > 0) && (x < 8) );
    }
}
