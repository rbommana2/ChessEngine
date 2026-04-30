package com.chess.board;
import com.chess.pieces.*;
import java.util.*;

public class boardLogic {

    public final Piece[][] board = new Piece[8][8];
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private List<Character> capturedPiecesChar = new ArrayList<>();

    // public char[][] charBoard() {
    //     char[][] displayBoard = new char[8][8];
    //     //System.out.println("a b c d e f g h");
    //     for(int i = 0; i < 8; i++) {

            
    //         for(int j = 0; j < 8; j++) {
    //             displayBoard[i][j] = board[i][j] == null ? '.' : board[i][j].getSymbol();  
    //         }
    //         //System.out.println(displayBoard[i][0]);
            
    //     }

    //     return displayBoard;
        
    // }

    public void printBoard() {
        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            int rank = 8 - row;
            System.out.print(rank + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                char symbol = (piece == null) ? '.' : piece.getSymbol();
                //System.out.println(piece == null);
                System.out.print(symbol + " ");
            }
            System.out.println(rank);
        }
        System.out.println("  a b c d e f g h");
        //System.out.println(board[0][0] == null);
    }

    public void initializeStart() {

        //black pieces
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);
        
        board[1][0] = new Pawn(Color.BLACK);
        board[1][1] = new Pawn(Color.BLACK);
        board[1][2] = new Pawn(Color.BLACK);
        board[1][3] = new Pawn(Color.BLACK);
        board[1][4] = new Pawn(Color.BLACK);
        board[1][5] = new Pawn(Color.BLACK);
        board[1][6] = new Pawn(Color.BLACK);
        board[1][7] = new Pawn(Color.BLACK);

        //white pieces
        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);

        board[6][0] = new Pawn(Color.WHITE);
        board[6][1] = new Pawn(Color.WHITE);
        board[6][2] = new Pawn(Color.WHITE);
        board[6][3] = new Pawn(Color.WHITE);
        board[6][4] = new Pawn(Color.WHITE);
        board[6][5] = new Pawn(Color.WHITE);
        board[6][6] = new Pawn(Color.WHITE);
        board[6][7] = new Pawn(Color.WHITE);


    }

    public void initlializeMove() {

    }

    public String getMove() {
        //getting the input move string
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move");
        String input = scanner.nextLine();
        return input;
    }
    

    public void inputToMove() {
        //converting the input string into a readable move

        //each move should contain the color, piece, position
        
        char[] currentMove = getMove().toCharArray();
        

        int row = currentMove[0] - '0';
        int col = currentMove[1] - '0';

        int toRow = currentMove[2] - '0';
        int toCol = currentMove[3] - '0';

        Piece piece = board[row][col];

        if(piece != null && piece.isValidMove(row, col, toRow, toCol, board) && capturePiece(row, col, toRow, toCol)) {
            
            board[toRow][toCol] = piece;
            board[row][col] = null;
        } else System.out.println("invalid");



    }


    private boolean capturePiece(int row, int col, int toRow, int toCol) {
        Piece currentPiece = board[row][col];
        Color currentColor = currentPiece.getColor();

        Piece futurePiece = (board[toRow][toCol] == null) ? null : board[toRow][toCol];
        Color futureColor = (futurePiece == null) ? null : futurePiece.getColor();

        if(currentColor != futureColor && futureColor != null) {

            capturedPieces.add(futurePiece);
            capturedPiecesChar.add(futurePiece.getSymbol());
            System.out.println(futurePiece.getSymbol() + " has been captured!");
            System.out.println(capturedPiecesChar);

            return true;
        } else if(futureColor == null) return true;
        
        return false;
        



    }






}
