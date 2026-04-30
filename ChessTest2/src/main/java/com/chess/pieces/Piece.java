package com.chess.pieces;



public abstract class Piece {

    protected final Color color;
    protected abstract char symbol();

    public char getSymbol() {
        return color == Color.WHITE ? Character.toUpperCase(symbol()) : Character.toLowerCase(symbol());
    }
    public abstract boolean isValidMove(int row, int col, int toRow, int toCol, Piece[][] board);

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }



    
}