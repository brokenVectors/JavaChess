package com.brokenvectors.chess;
import javax.swing.*;
import java.awt.*;

public class BoardRenderer extends JFrame {
    private static BoardRenderer instance;
    public BoardRenderer() {
        super("Chess");
        getContentPane().setBackground(Color.WHITE);
        setSize(400,425);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public void drawBoard(Graphics g, int w, int h, int y_offset) {
        Graphics2D graphics2d = (Graphics2D) g;
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {
                if( (i + j) % 2 == 0) {
                    graphics2d.setColor(Color.BLACK);
                }
                else {
                    graphics2d.setColor(Color.WHITE);
                }
                graphics2d.fillRect(w/8*i,h/8*j + y_offset,w/8,h/8);
            }
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g, 400, 400, 25);
    }
    public static void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BoardRenderer().setVisible(true);
            }
        });
    }
}
