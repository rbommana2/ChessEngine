package com.chess.app;

import com.chess.board.boardLogic;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        boardLogic board = new boardLogic();
        board.initializeStart();

        System.out.println(board.checkmate());
        while(!board.checkmate()) {
            board.printBoard();
            board.inputToMove();
        }

        board.printBoard();
        System.out.println("checkmate");



    }
}