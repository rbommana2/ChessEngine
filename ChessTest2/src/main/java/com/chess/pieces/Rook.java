package com.chess.pieces;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'r';
    }

    private boolean blockedFile(int row, int col, int toRow, int toCol, Piece[][] board) {
        int length = (row == toRow) ? Math.abs(col-toCol)-1 : Math.abs(row-toRow)-1;

        int i = (row == toRow) ? row : ((row-toRow < 0) ? row+1 : row-1);
        int j = (col == toCol) ? col : ((col-toCol < 0) ? col+1 : col-1);
        System.out.println("working");


        for(int num = 0; num < length; num++) {
            System.out.println("i: " + i);
            System.out.println("j: " + j);


            if(board[i][j] != null) {
                return false;
            }
            i = (row == toRow) ? row : ((row-toRow < 0) ? i+1 : i-1);
            j = (col == toCol) ? col : ((col-toCol < 0) ? j+1 : j-1);

        }

        return true;
    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if ((row == toRow || col == toCol)
            && blockedFile(row, col, toRow, toCol, board)
        ) {
            return true;
        } return false;
    }
}