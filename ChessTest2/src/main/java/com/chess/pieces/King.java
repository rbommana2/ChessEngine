package com.chess.pieces;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'k';
    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if(Math.abs(row - toRow) == 1 || Math.abs(col - toCol) == 1) {
            return true;
        } 
        return false;
    }
}
