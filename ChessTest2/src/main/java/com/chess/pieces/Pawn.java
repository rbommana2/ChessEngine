package com.chess.pieces;

import java.lang.Math;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'p';
    }

    private boolean firstMove(int row, int col, Piece[][] board) {
        if(board[row][col].getColor() == Color.WHITE && row == 6) {
            return true;
        }

        if(board[row][col].getColor() == Color.BLACK && row == 1) {
            return true;
        }

        return false;
    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {

        if(board[row][col].getColor() == Color.WHITE){
            if(row < toRow) return false;
        }

        if(board[row][col].getColor() == Color.BLACK){
            if(toRow < row) return false;
        }


        if(firstMove(row, col, board) && Math.abs(row - toRow) == 2 && col == toCol) {
            return true;
        }

        if(Math.abs(row - toRow) == 1 && col == toCol) {
            return true;
        }

        return false;
    }
}