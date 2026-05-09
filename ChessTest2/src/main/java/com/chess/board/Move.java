package com.chess.board;

import com.chess.pieces.*;
import com.chess.board.boardLogic;

public class Move {
    public final int fromRow;
    public final int fromCol;
    public final int toRow;
    public final int toCol;

    public final boolean isCastle;
    public final boolean isEnPassant;
    public final boolean isPromotion;

    public final Piece movedPiece;
    public final Piece capturedPiece;
    public final Class<? extends Piece> promotionType;

    public Move(
        int fromRow, int fromCol, int toRow, int toCol,
        Piece movedPiece, Piece capturedPiece,
        boolean isCastle, boolean isEnPassant, boolean isPromotion,
        Class<? extends Piece> promotionType
    ) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.isCastle = isCastle;
        this.isEnPassant = isEnPassant;
        this.isPromotion = isPromotion;
        this.promotionType = promotionType;
    }

    // Simple normal move helper
    public static Move normal(int fr, int fc, int tr, int tc, Piece moved, Piece captured) {
        return new Move(fr, fc, tr, tc, moved, captured, false, false, false, null);
    }

    @Override
    public String toString() {
        return "(" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol + ")";
    }
}
