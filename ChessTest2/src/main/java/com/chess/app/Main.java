package com.chess.app;

import com.chess.board.boardLogic;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        boardLogic board = new boardLogic();
        board.initializeStart();

        //boolean test = (board.getMove().equals("end"));

        // do {
        //     board.printBoard();
        //     board.getMove();
        //     board.inputToMove();
            
        // } while(!board.getMove().equals("end"));
        //System.out.println(test);


        board.printBoard();

        while(!board.getMove().equals("end")) {

            board.inputToMove();
            board.printBoard();
            
        }



    }
}