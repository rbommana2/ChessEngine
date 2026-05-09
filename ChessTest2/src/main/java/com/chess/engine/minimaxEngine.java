package com.chess.engine;

import com.chess.pieces.Bishop;
import com.chess.pieces.Color;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;
import com.chess.board.*;
import com.chess.engine.Evaluator;

import java.util.*;


public class minimaxEngine {
    
    private final Evaluator evaluator = new Evaluator();
    private static final int CHECKMATE_SCORE = 1000000;

    public Move findBestMove(Color side, boardLogic board, int depth) {
        int moveNum = board.move;
        
        List<Move> possibleMoves = board.generateLegalMoves(side);
        //side = (moveNum%2 == 0) ? Color.BLACK : Color.WHITE;
        boolean maximizing = (side == Color.WHITE);

        Move bestMove = null;
        int highScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(Move move : possibleMoves) {
            boardLogic.MoveState state = board.applyMove(move);

            int moveScore = minimax(opposite(side), board, depth-1, Integer.MIN_VALUE, Integer.MAX_VALUE);

            board.undoMove(move, state);

            if(moveScore > highScore && maximizing) {
                highScore = moveScore;
                bestMove = move;
            }

            if(moveScore < highScore && !maximizing) {
                highScore = moveScore;
                bestMove = move;
            }

        
        }
        System.out.println(bestMove);
        
        return bestMove;

    }

    private int minimax(Color side, boardLogic board, int depth, int alpha, int beta) {
        List<Move> possibleMoves = board.generateLegalMoves(side);
        if(depth == 0) {
            return evaluator.evaluate(board);
        }

        if(possibleMoves.isEmpty()) {
            if(board.inCheck(side)) {
                return (side == Color.WHITE) ? -CHECKMATE_SCORE : CHECKMATE_SCORE;
            }
            return 0;
        }
        Color trueSide = (board.move%2 == 0) ? Color.BLACK : Color.WHITE;
        boolean maximizing = (side == trueSide);

        if(maximizing) {
            int highScore = Integer.MIN_VALUE;

            for(Move move : possibleMoves) {
                boardLogic.MoveState state = board.applyMove(move);
                int score = minimax(opposite(side), board, depth-1, alpha, beta);
                board.undoMove(move, state);

                highScore = Math.max(score, highScore);
                alpha = Math.max(highScore, alpha);

                if(alpha >= beta) {
                    //System.out.println(highScore);
                    break;
                }
            }
            return highScore;
        } else {
            int highScore = Integer.MAX_VALUE;

            for(Move move: possibleMoves) {
                boardLogic.MoveState state = board.applyMove(move);
                int score = minimax(opposite(side), board, depth-1, alpha, beta);
                board.undoMove(move, state);

                highScore = Math.min(score, highScore);
                beta = Math.min(highScore, beta);

                if(alpha >= beta) {
                    //System.out.println(highScore);
                    break;
                }
            }

            return highScore;
        }



    }

    private Color opposite(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }








    









}
