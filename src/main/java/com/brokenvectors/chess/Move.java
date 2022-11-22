package com.brokenvectors.chess;

public class Move {
    public Coordinate origin;
    public Coordinate target;
    public Move(Coordinate origin, Coordinate target) {
        this.origin = origin;
        this.target = target;
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
        return toAlphabet(origin.x) + Integer.toString(origin.y + 1) + "-" + toAlphabet(target.x) + Integer.toString(target.y + 1);
    }
}
