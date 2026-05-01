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

    private boolean capturePiece(int row, int col, int toRow, int toCol, Piece[][] board) {
        Piece currentPawn = board[row][col];
        Piece futureSq = board[toRow][toCol];

        if(futureSq != null && futureSq.getColor() != currentPawn.getColor() && Math.abs(row - toRow) == Math.abs(col - toCol) && Math.abs(row-toRow) == 1) {
            return true;
        }
        return false;
    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {


        if(firstMove(row, col, board) && Math.abs(row - toRow) == 2 && col == toCol) {
            return true;
        }

        if(Math.abs(row - toRow) == 1 && col == toCol) {
            return true;
        }

        if(capturePiece(row, col, toRow, toCol, board)) return true;

        return false;
    }
}