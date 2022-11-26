package com.brokenvectors.chess.Renderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardMouseListener implements MouseListener {
    private final BoardRenderer renderer;
    public BoardMouseListener(BoardRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouse_x = e.getX();
        int mouse_y = e.getY();
        int board_x = mouse_x / (this.renderer.width/8);
        int board_y = 7 - ((mouse_y + this.renderer.yOffset) / (this.renderer.height/8) - 1);
        if(this.renderer.selecting) {
            this.renderer.makeMove(this.renderer.selected_y, this.renderer.selected_x, board_y, board_x);
            this.renderer.selecting = false;
        }
        else {
            this.renderer.select(board_x, board_y);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
