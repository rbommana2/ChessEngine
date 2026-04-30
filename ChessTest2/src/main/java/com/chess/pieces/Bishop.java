package com.chess.pieces;
import java.util.stream.IntStream;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    protected char symbol() {
        return 'b';
    }

    private boolean blockedDiagonal(int row, int col, int toRow, int toCol, Piece[][] board) {
        //method to check the squares on the diagonal not including the current position and future position
        
        // int[] betweenRow = new int[Math.abs(row-toRow) - 1];
        // int[] betweenCol = new int[Math.abs(row-toRow) - 1];
        
        int i = (row < toRow) ? row+1 : row-1;
        int j = (col < toCol) ? col+1 : col-1;
        int num = 0;

        // System.out.println(Math.abs(row-toRow) - 1);

        while(num < Math.abs(row-toRow)-1) {

            // betweenRow[num] = i;
            // betweenCol[num] = j;

            // System.out.println("i: " + i);
            // System.out.println("j: " + j);
            // System.out.println(board[i][j] != null);

            if(board[i][j] != null) {
                return false;
            }

            i = (row < toRow) ? i+1 : i-1;
            j = (col < toCol) ? j+1 : j-1;
            
            num ++;

        }
        return true;
        

    }

    public boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board) {
        if(
            Math.abs(row - toRow) == Math.abs(col - toCol) &&
            blockedDiagonal(row, col, toRow, toCol, board)
        
        ) {
            //System.out.println(blockedDiagonal(row, col, toRow, toCol, board));
            return true;
        }
        return false;
    }
}