package com.chess.pieces;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'q';
    }


    private boolean blockedDiagonal(int row, int col, int toRow, int toCol, Piece[][] board) {
        //method to check the squares on the diagonal not including the current position and future position
        
        int i = (row < toRow) ? row+1 : row-1;
        int j = (col < toCol) ? col+1 : col-1;
        int num = 0;

        // System.out.println(Math.abs(row-toRow) - 1);

        while(num < Math.abs(row-toRow)-1) {

            if(board[i][j] != null) {
                return false;
            }

            i = (row < toRow) ? i+1 : i-1;
            j = (col < toCol) ? j+1 : j-1;
            
            num ++;

        }
        return true;
        

    }

    private boolean blockedFile(int row, int col, int toRow, int toCol, Piece[][] board) {
        int length = (row == toRow) ? Math.abs(col-toCol)-1 : Math.abs(row-toRow)-1;

        int i = (row == toRow) ? row : ((row-toRow < 0) ? row+1 : row-1);
        int j = (col == toCol) ? col : ((col-toCol < 0) ? col+1 : col-1);

        for(int num = 0; num < length; num++) {
            if(board[i][j] != null) {
                return false;
            }
            i = (row == toRow) ? row : ((row-toRow < 0) ? i+1 : i-1);
            j = (col == toCol) ? col : ((col-toCol < 0) ? j+1 : j-1);

        }

        return true;
    }




    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if(
            Math.abs(row - toRow) == Math.abs(col - toCol)
            && blockedDiagonal(row, col, toRow, toCol, board)
        ) {
            return true;
        } else if (
            (row == toRow || col == toCol)
            && blockedFile(row, col, toRow, toCol, board)
        ) {
            return true;
        }
        else return false;
    }
}