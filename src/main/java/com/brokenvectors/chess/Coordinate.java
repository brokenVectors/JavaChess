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
    public Coordinate(String str) {
        switch (str.charAt(0)) {
            case 'a':
                this.x = 0;
                break;
            case 'b':
                this.x = 1;
                break;
            case 'c':
                this.x = 2;
                break;
            case 'd':
                this.x = 3;
                break;
            case 'e':
                this.x = 4;
                break;
            case 'f':
                this.x = 5;
                break;
            case 'g':
                this.x = 6;
                break;
            case 'h':
                this.x = 7;
                break;
        }
        this.y = Character.getNumericValue(str.charAt(1)) - 1; // because arrays have zero-based index
    }
    public static String toAlphabet(int i) {
        switch(i){
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
            default:
                return "?";
        }
    }
    public String toString() {
        return toAlphabet(this.x) + Integer.toString(this.y + 1);
    }
    public static boolean isInBounds(int y, int x) {
        return ( (y > 0) && (y < 8) ) && ( (x > 0) && (x < 8) );
    }
    public boolean equals(Coordinate other) {
        return (this.x == other.x) && (this.y == other.y);
    }
}
