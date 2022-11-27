package com.brokenvectors.chess.Renderer;

import com.brokenvectors.chess.Board;
import com.brokenvectors.chess.Coordinate;
import com.brokenvectors.chess.Move;
import com.brokenvectors.chess.PieceType;
import com.brokenvectors.chess.Pieces.Piece;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoardRenderer extends JFrame {
    private static BoardRenderer instance;
    private final HashMap<Boolean, HashMap<PieceType, Image>> sprites; // Hashmap of white and black(true/false) and a hashmap for each of piece type and image
    private final Board board;
    public final int width;
    public final int height;
    public final int yOffset;
    public int selected_x;
    public int selected_y;
    public boolean selecting = false;
    public BoardRenderer(Board board) {
        super("Chess");
        getContentPane().setBackground(Color.WHITE);
        setSize(400,425);
        width = 400;
        height = 400;
        yOffset = 25;
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
        this.addMouseListener((MouseListener) new BoardMouseListener(this));
    }
    private void loadPieceSprite(PieceType pieceType, boolean isWhite, String fileName) {
        try {
            sprites.get(isWhite).put(pieceType, ImageIO.read(new File("/Users/miles/Documents/media/chess_sprites/" + fileName)).getScaledInstance(50,50, Image.SCALE_DEFAULT));
        }
        catch(IOException ignored) {

        }
    }
    public void drawBoard(Graphics g, int w, int h, int yOffset) {
        Graphics2D graphics2d = (Graphics2D) g;
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {
                Piece piece = this.board.getPiece(new Coordinate(j,i));
                // checkerboard
                if( (i + j) % 2 == 0) {
                    graphics2d.setColor(new Color(100,100,100));
                }
                else {
                    graphics2d.setColor(Color.WHITE);
                }
                boolean isSelectedSquare = j == this.selected_y && i == this.selected_x;
                if(isSelectedSquare && selecting && piece != null)
                    graphics2d.setColor(Color.GREEN);
                int x = w/8*i;
                int y = h/8*(7-j) + yOffset;
                graphics2d.fillRect(x,y,w/8,h/8);
                // piece sprite
                if(piece != null)
                    graphics2d.drawImage(this.sprites.get(piece.getColor()).get(piece.getPieceType()), x,y, null);
            }
        }
    }
    public void makeMove(int y1, int x1, int y2, int x2) {
        this.board.makeMove(new Move(new Coordinate(y1, x1), new Coordinate(y2, x2)));
        //this.board.movePiece(new Move(new Coordinate(y1, x1), new Coordinate(y2, x2)));
        this.repaint();
    }
    public void select(int x, int y) {
        this.selected_x = x;
        this.selected_y = y;
        this.selecting = true;
        //System.out.println(this.selected_x);
        //System.out.println(this.selected_y);
        Piece selectedPiece = this.board.getPiece(new Coordinate(this.selected_y,this.selected_x));
        if(selectedPiece == null) {
            selecting = false; // If user selected nothing, set selecting to false
        }
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        this.drawBoard(g, this.width, this.height, this.yOffset);
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
