package ai.heuristic;

import game.player.PlayerEnum;
import game.move.Move;
import game.board.BigBoard;

import static ai.heuristic.util.MoveQualityCheckers.*;
import static game.board.BoardConstants.EMPTY;

/*
Variables:
1. topPlayer is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
2. opponent is the opponent player for currentPlayer:)
3. score is the evaluation for the given position. It multiplied by -1 if currentPlayer is MIN.

Medium AI Logic:
1. Small board win +3
2. Center board win +10
3. Corner board win +4
4. Two small board wins that can be extended to win +5
5. Two cell wins that can be extended to win +2
6. Redirecting to already won board -3
7. Blocks opponents two in a row in small board +5

 */


public class MediumAI implements StateEvaluationAi {
    public static final int SMALL_BOARD_WIN = 3;
    public static final int CENTER_BOARD_WIN = 10;
    public static final int CORNER_BOARD_WIN = 4;
    public static final int CREATE_TWO_BOARD_WINNING_SEQUENCE = 5;
    public static final int CREATE_TWO_CELL_WINNING_SEQUENCE = 2;
    public static final int REDIRECTING_TO_PLAY_ANYWHERE = -3;
    public static final int BLOCK_OPPONENTS_WINNING_SEQUENCE = 5;   // in small board

    public int evaluateBoardAfterLastMove(BigBoard board) {
        int topPlayer = board.getPlayer().getPlayerEnum() == PlayerEnum.MIN ? 1 : 2;
        int opponent = topPlayer == 1 ? 2 : 1;
        int score = 0;

        Move lastMove = board.getLastMove();
        int lastMoveRow = lastMove.getRow();
        int lastMoveCol = lastMove.getColumn();
        int smallBoardI = lastMoveRow / 3;
        int smallBoardJ = lastMoveCol / 3;

        // 1. winning the small board +5
        if (board.getSilhouette()[smallBoardI][smallBoardJ] == topPlayer) {
            score += SMALL_BOARD_WIN;
            // 2. winning the center board +10
            if (smallBoardI == 1 && smallBoardJ == 1) {
                score += CENTER_BOARD_WIN;
            }

            // 3. winning the corner board +3
            if ((smallBoardI == 0 || smallBoardI == 2) && (smallBoardJ == 0 || smallBoardJ == 2)) {
                score += CORNER_BOARD_WIN;
            }
        }

        // 4. creating two in a row/column/diagonal in silhouette, that can later become three in a row/column/diagonal +4
        if (playerOpponentMarkAppearances(board.getSilhouette(), smallBoardI, smallBoardJ, 2, 0)) {
            score += CREATE_TWO_BOARD_WINNING_SEQUENCE;
        }


        // 5. creating two in a row/column/diagonal in a small board, that can later become three +3
        if (is2InARowColDiagonalAfterLastMove(board)) {
            score += CREATE_TWO_CELL_WINNING_SEQUENCE;
        }

        // 6. redirecting to a full or already won board -3;
        if (board.getApplicableActions().size() > 9) {
            score += REDIRECTING_TO_PLAY_ANYWHERE;
        }

        // 7. blocking opponents two in a row/column/diagonal
        if (isOpponent2BlockedAfterLastMove(board)) {

            score += BLOCK_OPPONENTS_WINNING_SEQUENCE;
        }

        return topPlayer == 1 ? score : (-1) * score;
    }


}
