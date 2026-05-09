package com.chess.engine;

import com.chess.board.boardLogic;
import com.chess.pieces.Bishop;
import com.chess.pieces.Color;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

import java.util.*;
import com.chess.board.Move;

public class Evaluator {
    private int pieceValue(Piece piece) {
        if (piece instanceof Pawn) return 100;
        if (piece instanceof Knight) return 320;
        if (piece instanceof Bishop) return 330;
        if (piece instanceof Rook) return 500;
        if (piece instanceof Queen) return 900;
        if (piece instanceof King) return 20000;
        return 0;
    }


    public int evaluate(boardLogic game) {
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = game.board[row][col];
                if (piece == null) continue;

                int value = pieceValue(piece);
                score += (piece.getColor() == Color.WHITE) ? value : -value;
            }
        }

        return score;
    }

    private List<Move> moveOrder(Color side, boardLogic board) {
        return new ArrayList<>();
    }



}
