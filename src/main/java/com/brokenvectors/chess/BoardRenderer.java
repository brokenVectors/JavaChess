package com.brokenvectors.chess;

import com.brokenvectors.chess.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoardRenderer extends JFrame {
    private static BoardRenderer instance;
    private final HashMap<Boolean, HashMap<PieceType, Image>> sprites; // Hashmap of white and black(true/false) and a hashmap for each of piece type and image
    private final Board board;
    public BoardRenderer(Board board) {
        super("Chess");
        getContentPane().setBackground(Color.WHITE);
        setSize(400,425);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.board = board;
        this.sprites = new HashMap<Boolean, HashMap<PieceType, Image>>();
        sprites.put(true, new HashMap<PieceType, Image>()); // white sprites
        sprites.put(false, new HashMap<PieceType, Image>()); // black sprites

        this.loadPieceSprite(PieceType.KING, true, "white_king.png");
        this.loadPieceSprite(PieceType.QUEEN, true, "white_queen.png");
        this.loadPieceSprite(PieceType.ROOK, true, "white_rook.png");
        this.loadPieceSprite(PieceType.KNIGHT, true, "white_knight.png");
        this.loadPieceSprite(PieceType.BISHOP, true, "white_bishop.png");
        this.loadPieceSprite(PieceType.PAWN, true, "white_pawn.png");

        this.loadPieceSprite(PieceType.KING, false, "black_king.png");
        this.loadPieceSprite(PieceType.QUEEN, false, "black_queen.png");
        this.loadPieceSprite(PieceType.ROOK, false, "black_rook.png");
        this.loadPieceSprite(PieceType.KNIGHT, false, "black_knight.png");
        this.loadPieceSprite(PieceType.BISHOP, false, "black_bishop.png");
        this.loadPieceSprite(PieceType.PAWN, false, "black_pawn.png");
    }
    private void loadPieceSprite(PieceType pieceType, boolean isWhite, String fileName) {
        try {
            sprites.get(isWhite).put(pieceType, ImageIO.read(new File("/Users/miles/Documents/media/chess_sprites/" + fileName)).getScaledInstance(50,50, Image.SCALE_DEFAULT));
        }
        catch(IOException ignored) {

        }
    }
    public void drawBoard(Graphics g, int w, int h, int y_offset) {
        Graphics2D graphics2d = (Graphics2D) g;
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {
                if( (i + j) % 2 == 0) {
                    graphics2d.setColor(new Color(100,100,100));
                }
                else {
                    graphics2d.setColor(Color.WHITE);
                }
                graphics2d.fillRect(w/8*i,h/8*j + y_offset,w/8,h/8);
                Piece piece = this.board.getPiece(new Coordinate(j,i));
                if(piece != null)
                    g.drawImage(this.sprites.get(piece.getColor()).get(piece.getPieceType()), w/8*i, h/8*(7-j) + y_offset, null);
            }
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g, 400, 400, 25);
    }
    public static void run(Board board) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BoardRenderer(board).setVisible(true);
            }
        });
    }
}
