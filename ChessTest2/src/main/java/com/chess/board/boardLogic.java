package com.chess.board;
import com.chess.pieces.*;
import java.util.*;

public class boardLogic {

    public final Piece[][] board = new Piece[8][8];
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private List<Character> capturedPiecesChar = new ArrayList<>();
    int move = 1;

    public HashMap<Character, Integer> notation = new HashMap<>();
    

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
            System.out.println(row);
        }
        System.out.println("  0 1 2 3 4 5 6 7");
        //System.out.println(board[0][0] == null);
    }

    public void initializeStart() {

        notation.put('a', 0);
        notation.put('b', 1);
        notation.put('c', 2);
        notation.put('d', 3);
        notation.put('e', 4);
        notation.put('f', 5);
        notation.put('g', 6);
        notation.put('h', 7);

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
        
        

        
        //int row = notation.containsKey(currentMove[0]) ? notation.get(currentMove[0]) : currentMove[0] - '0';
        int row = currentMove[0] - '0';
        int col = currentMove[1] - '0';

        int toRow = currentMove[2] - '0';
        int toCol = currentMove[3] - '0';

        Piece piece = board[row][col];

        //System.out.println((move % 2 == 0) ? (board[row][col].getColor() == Color.BLACK) : (board[row][col].getColor() == Color.WHITE));
        System.out.println(simulateCheck(row, col, toRow, toCol));
        if(
            piece != null && 
            piece.isValidMove(row, col, toRow, toCol, board) && 
            capturePiece(row, col, toRow, toCol) && 
            moveOrder(row, col) &&
            !simulateCheck(row, col, toRow, toCol)
        ) {
            
            board[toRow][toCol] = piece;
            board[row][col] = null;

            pawnPromotion(toRow, toCol);

            move++;
            //System.out.println(move);

        } 
        
        else System.out.println("invalid");



    }

    private boolean moveOrder(int row, int col) {
        return ((move % 2 == 0) ? (board[row][col].getColor() == Color.BLACK) : (board[row][col].getColor() == Color.WHITE));
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

    private int[] currentKing(Color kingColor) {
        int[] coords = new int[2];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == kingColor) {
                    coords[0] = row;
                    coords[1] = col;
                    break;
                }
                
            }
        }
        return coords;
    };

    public boolean inCheck(Color kingColor) {
        int[] kingCoords = currentKing(kingColor);
        int kingRow = kingCoords[0], kingCol = kingCoords[1];

        Color enemy = (kingColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == enemy) {
                    if (piece.isValidMove(row, col, kingRow, kingCol, board)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean simulateCheck(int row, int col, int toRow, int toCol) {
        Piece currentSq = board[row][col];
        Piece futureSq = board[toRow][toCol];

        Color currentColor = currentSq.getColor();

        board[toRow][toCol] = currentSq;
        board[row][col] = null;

        boolean inCheck = inCheck(currentColor);
        //System.out.println(inCheck);

        board[row][col] = currentSq;
        board[toRow][toCol] = futureSq;

        return inCheck;
    }

    public boolean checkmate() {
        Color currentColor = (move % 2 == 0) ? Color.BLACK : Color.WHITE;
        if(!inCheck(currentColor)) return false;

        for(int row = 0; row < 8; row ++) {
            for(int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if(piece == null || piece.getColor() != currentColor) {
                    continue;
                }

                for(int toRow = 0; toRow < 8; toRow++) {
                    for(int toCol = 0; toCol < 8; toCol++) {
                        if(!piece.isValidMove(row, col, toRow, toCol, board)) continue;
                        if(simulateCheck(row, col, toRow, toCol)) continue;
                        return false;
                    }
                }
                
            }
        }

        return true;
        
    }

    public boolean isStalemate() {
        Color currentColor = (move % 2 == 0) ? Color.BLACK : Color.WHITE;
        if (inCheck(currentColor)) return false;

        for(int row = 0; row < 8; row ++) {
            for(int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if(piece == null || piece.getColor() != currentColor) {
                    continue;
                }

                for(int toRow = 0; toRow < 8; toRow++) {
                    for(int toCol = 0; toCol < 8; toCol++) {
                        if(!piece.isValidMove(row, col, toRow, toCol, board)) continue;
                        if(simulateCheck(row, col, toRow, toCol)) continue;
                        return false;
                    }
                }
                
            }
        }
        return false;
    }

    private void pawnPromotion(int row, int col) {
        Piece currentPiece = board[row][col];
        Color currentColor = currentPiece.getColor();
        Scanner scan = new Scanner(System.in);

        if(currentPiece instanceof Pawn && (row == 0 || row == 7)) {
            System.out.println("Promotion piece: ");
            String input = scan.nextLine();

            board[row][col] = null;

            if(input.equals("knight")) {
                board[row][col] = new Knight(currentColor);
            } else if(input.equals("queen")) {
                board[row][col] = new Queen(currentColor);
            } else if(input.equals("rook")) {
                board[row][col] = new Rook(currentColor);
            } else {
                board[row][col] = new Bishop(currentColor);
            }

            System.out.println(input);

        }
    }









}
