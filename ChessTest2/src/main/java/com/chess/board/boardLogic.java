package com.chess.board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.chess.pieces.Bishop;
import com.chess.pieces.Color;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;


public class boardLogic {


    

    public final Piece[][] board = new Piece[8][8];
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private List<Character> capturedPiecesChar = new ArrayList<>();
    public int move = 1;

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

    public HashSet<Piece> hasMoved = new HashSet<>();
    

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
        //System.out.println(simulateCheck(row, col, toRow, toCol));
        Move moveObj = Move.normal(row, col, toRow, toCol, piece, board[toRow][toCol]);

        if (piece instanceof King && isCastleMove(row, col, toRow, toCol)) {
            doCastle(row, col, toRow, toCol);
            updateCastleFlags(moveObj, piece); // marks king moved
            move++;
            return;
        }




        boolean enPassant = isEnPassant(row, col, toRow, toCol);
        if(
            piece != null && 
            (piece.isValidMove(row, col, toRow, toCol, board) || enPassant) && 
            capturePiece(row, col, toRow, toCol) && 
            moveOrder(row, col) &&
            !simulateCheck(row, col, toRow, toCol)
        ) {
            
            if((piece instanceof King)) {
                hasMoved.add(piece);
            }

            board[toRow][toCol] = piece;
            board[row][col] = null;

            pawnPromotion(toRow, toCol);

            move++;
            lastMove = new LastMove(row, col, toRow, toCol, piece);
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

        if(
            (currentColor != futureColor && futureColor != null)
        
        ) {

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
        return true;
    }

    private void pawnPromotion(int row, int col) {
        Piece piece = board[row][col];

        if (!(piece instanceof Pawn)) {
            return;
        }

        if (piece.getColor() == Color.WHITE && row == 0) {
            board[row][col] = new Queen(Color.WHITE);
        } else if (piece.getColor() == Color.BLACK && row == 7) {
            board[row][col] = new Queen(Color.BLACK);
        }
}


    private static class LastMove {
        int fromRow, fromCol, toRow, toCol;
        Piece piece;
        LastMove(int fromRow, int fromCol, int toRow, int toCol, Piece piece) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
            this.piece = piece;
        }
    }
    private LastMove lastMove = null;

    private boolean isEnPassant(int row, int col, int toRow, int toCol) {

        if(board[toRow][toCol] == null) return false;

        Piece currentPiece = board[row][col];

        int dir = (currentPiece.getColor() == Color.WHITE) ? -1 : 1;

        if(
            (!(currentPiece instanceof Pawn) || lastMove == null) ||
            (!(toRow == row + dir && Math.abs(toCol - col) == 1)) ||
            (board[toRow][toCol] != null)  ||
            (!(lastMove.piece instanceof Pawn)) || 
            (lastMove.piece.getColor() == currentPiece.getColor()) || 
            (Math.abs(lastMove.toRow - lastMove.fromRow) != 2)
        ) return false;


        capturedPieces.add(lastMove.piece);
        capturedPiecesChar.add(lastMove.piece.getSymbol());
        System.out.println(lastMove.piece.getSymbol() + " has been captured!");
        System.out.println(capturedPiecesChar);

        return lastMove.toRow == row && lastMove.toCol == toCol;
    }

    private boolean whiteKingMoved = false, blackKingMoved = false;
    private boolean whiteLeftRookMoved = false, whiteRightRookMoved = false;
    private boolean blackLeftRookMoved = false, blackRightRookMoved = false;

    private boolean isCastleMove(int row, int col, int toRow, int toCol) {
        Piece p = board[row][col];
        if (!(p instanceof King)) return false;
        if (row != toRow) return false;
        if (Math.abs(toCol - col) != 2) return false; // king moves 2 squares

        Color color = p.getColor();
        int homeRow = (color == Color.WHITE) ? 7 : 0;
        if (row != homeRow || col != 4) return false; // king must start on e-file

        // king/rook must not have moved
        if (color == Color.WHITE && whiteKingMoved) return false;
        if (color == Color.BLACK && blackKingMoved) return false;

        boolean kingSide = (toCol == 6);
        int rookCol = kingSide ? 7 : 0;
        Piece rook = board[homeRow][rookCol];
        if (!(rook instanceof Rook) || rook.getColor() != color) return false;

        if (color == Color.WHITE) {
            if (kingSide && whiteRightRookMoved) return false;
            if (!kingSide && whiteLeftRookMoved) return false;
        } else {
            if (kingSide && blackRightRookMoved) return false;
            if (!kingSide && blackLeftRookMoved) return false;
        }

        int step = kingSide ? 1 : -1;
        for (int c = col + step; c != rookCol; c += step) {
            if (board[homeRow][c] != null) return false;
        }

        if (inCheck(color)) return false;
        if (simulateCheck(row, col, row, col + step)) return false;
        if (simulateCheck(row, col, toRow, toCol)) return false;

        return true;
    }

    private void doCastle(int row, int col, int toRow, int toCol) {
        Piece king = board[row][col];
        boolean kingSide = (toCol == 6);

        int rookFromCol = kingSide ? 7 : 0;
        int rookToCol   = kingSide ? 5 : 3;

        board[toRow][toCol] = king;
        board[row][col] = null;

        board[row][rookToCol] = board[row][rookFromCol];
        board[row][rookFromCol] = null;
    }

    private void updateCastleFlags(Move m, Piece moved) {
        if (moved instanceof King) {
            if (moved.getColor() == Color.WHITE) whiteKingMoved = true;

            else blackKingMoved = true;
            
        } else if (moved instanceof Rook) {
            if (m.fromRow == 7 && m.fromCol == 0) whiteLeftRookMoved = true;
            if (m.fromRow == 7 && m.fromCol == 7) whiteRightRookMoved = true;
            if (m.fromRow == 0 && m.fromCol == 0) blackLeftRookMoved = true;
            if (m.fromRow == 0 && m.fromCol == 7) blackRightRookMoved = true;
        }
    }

    private void promotePawn(int row, int col) {
        Piece piece = board[row][col];

        if (!(piece instanceof Pawn)) {
            return;
        }

        if (piece.getColor() == Color.WHITE && row == 0) {
            board[row][col] = new Queen(Color.WHITE);
        } else if (piece.getColor() == Color.BLACK && row == 7) {
            board[row][col] = new Queen(Color.BLACK);
        }
    }

    public static class MoveState {
        public final Piece capturedOnTarget;
        public final Piece enPassantCaptured;
        public final LastMove prevLastMove;
        public final int prevMoveNumber;

        public final boolean whiteKingMoved, blackKingMoved;
        public final boolean whiteLeftRookMoved, whiteRightRookMoved;
        public final boolean blackLeftRookMoved, blackRightRookMoved;

        public MoveState(
            Piece capturedOnTarget,
            Piece enPassantCaptured,
            LastMove prevLastMove,
            int prevMoveNumber,
            boolean whiteKingMoved, boolean blackKingMoved,
            boolean whiteLeftRookMoved, boolean whiteRightRookMoved,
            boolean blackLeftRookMoved, boolean blackRightRookMoved
        ) {
            this.capturedOnTarget = capturedOnTarget;
            this.enPassantCaptured = enPassantCaptured;
            this.prevLastMove = prevLastMove;
            this.prevMoveNumber = prevMoveNumber;
            this.whiteKingMoved = whiteKingMoved;
            this.blackKingMoved = blackKingMoved;
            this.whiteLeftRookMoved = whiteLeftRookMoved;
            this.whiteRightRookMoved = whiteRightRookMoved;
            this.blackLeftRookMoved = blackLeftRookMoved;
            this.blackRightRookMoved = blackRightRookMoved;
        }
    }

    public MoveState applyMove(Move m) {
        Piece moving = board[m.fromRow][m.fromCol];
        Piece capturedOnTarget = board[m.toRow][m.toCol];
        Piece enPassantCaptured = null;

        MoveState state = new MoveState(
            capturedOnTarget,
            null,
            lastMove,
            move,
            whiteKingMoved, blackKingMoved,
            whiteLeftRookMoved, whiteRightRookMoved,
            blackLeftRookMoved, blackRightRookMoved
        );

        if (m.isEnPassant) {
            int capturedPawnRow = m.fromRow; // pawn being captured is beside mover
            enPassantCaptured = board[capturedPawnRow][m.toCol];
            board[capturedPawnRow][m.toCol] = null;
            state = new MoveState(
                capturedOnTarget, enPassantCaptured, lastMove, move,
                whiteKingMoved, blackKingMoved,
                whiteLeftRookMoved, whiteRightRookMoved,
                blackLeftRookMoved, blackRightRookMoved
            );
        }

        board[m.toRow][m.toCol] = moving;
        board[m.fromRow][m.fromCol] = null;

        if (m.isCastle && moving instanceof King) {
            boolean kingSide = (m.toCol == 6);
            int rookFromCol = kingSide ? 7 : 0;
            int rookToCol = kingSide ? 5 : 3;
            board[m.fromRow][rookToCol] = board[m.fromRow][rookFromCol];
            board[m.fromRow][rookFromCol] = null;
        }

        if (m.isPromotion && m.promotionType != null && moving instanceof Pawn) {
            System.out.println("promo");
            Color c = moving.getColor();
            //promotePawn(m.toRow, m.toCol);
            if (m.promotionType == Queen.class) board[m.toRow][m.toCol] = new Queen(c);
            else if (m.promotionType == Rook.class) board[m.toRow][m.toCol] = new Rook(c);
            else if (m.promotionType == Bishop.class) board[m.toRow][m.toCol] = new Bishop(c);
            else if (m.promotionType == Knight.class) board[m.toRow][m.toCol] = new Knight(c);
        }

        updateCastleFlags(m, moving);
        lastMove = new LastMove(m.fromRow, m.fromCol, m.toRow, m.toCol, moving);
        move++;

        return state;

    }

    public void undoMove(Move m, MoveState s) {
        Piece movedNow = board[m.toRow][m.toCol];

        if (m.isPromotion) {
            Color c = (movedNow != null) ? movedNow.getColor() : Color.WHITE;
            movedNow = new Pawn(c);
        }

        board[m.fromRow][m.fromCol] = movedNow;
        board[m.toRow][m.toCol] = s.capturedOnTarget;

        if (m.isCastle && movedNow instanceof King) {
            boolean kingSide = (m.toCol == 6);
            int rookFromCol = kingSide ? 7 : 0;
            int rookToCol = kingSide ? 5 : 3;
            board[m.fromRow][rookFromCol] = board[m.fromRow][rookToCol];
            board[m.fromRow][rookToCol] = null;
        }

        if (m.isEnPassant) {
            int capturedPawnRow = m.fromRow;
            board[capturedPawnRow][m.toCol] = s.enPassantCaptured;
            board[m.toRow][m.toCol] = null; //
        }

        lastMove = s.prevLastMove;
        move = s.prevMoveNumber;
        whiteKingMoved = s.whiteKingMoved;
        blackKingMoved = s.blackKingMoved;
        whiteLeftRookMoved = s.whiteLeftRookMoved;
        whiteRightRookMoved = s.whiteRightRookMoved;
        blackLeftRookMoved = s.blackLeftRookMoved;
        blackRightRookMoved = s.blackRightRookMoved;
    }


    public List<Move> generateLegalMoves(Color side) {
        //System.out.println(board[7][7].isValidMove(7, 7, 7, 6, board));
        //System.out.println(capturePiece(7, 7, 7, 6));

      
        //side = (move%2 == 0) ? Color.BLACK : Color.WHITE;

        List<Move> possibleMoves = new ArrayList<>();
        

        for(int row = 0; row < 8; row ++) {
            for(int col = 0; col < 8; col++) {


                Piece piece = board[row][col];
                if(piece == null || piece.getColor() != side) continue;


                    for(int toRow = 0; toRow < 8; toRow++) {
                        for(int toCol = 0; toCol < 8; toCol++) {
                            
                            if(!piece.isValidMove(row, col, toRow, toCol, board) || simulateCheck(row, col, toRow, toCol)) {
                                continue;
                            }
                           // System.out.println(piece.isValidMove(row, col, toRow, toCol, board));

                            Piece futurePiece = board[toRow][toCol];

                            Move move = Move.normal(
                                row,
                                col,
                                toRow,
                                toCol,
                                piece,
                                futurePiece
                            );
                            if(piece.isValidMove(row, col, toRow, toCol, board)) possibleMoves.add(move);
                            //System.out.println(move + " " + piece.isValidMove(row, col, toRow, toCol, board));
                                


                        }
                    }
                

            }
        }
        //System.out.println(possibleMoves);
        return possibleMoves;
    }





}
