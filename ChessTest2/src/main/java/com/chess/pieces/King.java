package com.chess.pieces;
import com.chess.board.boardLogic;

public class King extends Piece {
    
    public King(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'k';
    }


    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if((board[toRow][toCol] != null) && (board[toRow][toCol].getColor() == board[row][col].getColor())) return false;
        if(row == toRow && col == toCol) return false;

        if(Math.abs(row - toRow) > 1 || Math.abs(col - toCol) > 1) {
            return false;
        }
        return true;
    }
}
