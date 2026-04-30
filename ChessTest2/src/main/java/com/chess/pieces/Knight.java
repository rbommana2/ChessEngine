package com.chess.pieces;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'n';
    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if(Math.abs(row - toRow) == 1 && Math.abs(col - toCol) == 2) {
            return true;
        } else if(Math.abs(row - toRow) == 2 && Math.abs(col - toCol) == 1) {
            return true;
        }
        return false;
    }
}