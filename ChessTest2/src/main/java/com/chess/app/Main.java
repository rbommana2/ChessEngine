package com.chess.app;

import com.chess.board.boardLogic;

import java.util.*;
import com.chess.pieces.Color;
import com.chess.engine.*;

public class Main {
    public static void main(String[] args) {
        boardLogic board = new boardLogic();
        minimaxEngine engine = new minimaxEngine();

        


        board.initializeStart();

        

        //System.out.println(board.checkmate());
        while(!board.checkmate()) {
            System.out.println(board.move);
            Color side = (board.move % 2 == 0) ? Color.BLACK : Color.WHITE;
            //System.out.println(board.generateLegalMoves(side));
            //System.out.println(board.move);

            board.applyMove(engine.findBestMove(side, board, 4));
            board.printBoard();
            //board.inputToMove();
        }

        board.printBoard();
        System.out.println("checkmate");



    }
}