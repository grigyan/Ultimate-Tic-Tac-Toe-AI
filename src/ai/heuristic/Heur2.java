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
Winning the game is the best case scenario

1. Small board win +10
2. Center board win +100
3. Corner board win +40
4. Two small board wins that can be extended to win +80
5. Two cell wins that can be extended to win +5
6. Redirecting to already won board -30
7. Blocks opponents two in a row in small board +50

 */

public class Heur2 implements StateEvaluationAi {
    public static final int SMALL_BOARD_WIN = 10;
    public static final int CENTER_BOARD_WIN = 100;
    public static final int CORNER_BOARD_WIN = 40;
    public static final int CREATE_TWO_BOARD_WINNING_SEQUENCE = 80;
    public static final int CREATE_TWO_CELL_WINNING_SEQUENCE = 5;
    public static final int REDIRECTING_TO_PLAY_ANYWHERE = -30;
    public static final int BLOCK_OPPONENTS_WINNING_SEQUENCE = 5;   // in small board

    public int evaluateBoardAfterLastMove(BigBoard board) {
        int topPlayer = board.getPlayer().getPlayerEnum() == PlayerEnum.MIN ? 1 : 2;
        int opponent = topPlayer == 1 ? 2 : 1;
        int score = 0;

        if (board.getGrid()[4][4] == EMPTY && board.getLastMove().getColumn() == 4 && board.getLastMove().getRow() == 4) {
            score += 1000;
        }

        if (board.isSilhouetteWon()) {
            return topPlayer == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        Move lastMove = board.getLastMove();
        int lastMoveRow = lastMove.getRow();
        int lastMoveCol = lastMove.getColumn();
        int smallBoardI = lastMoveRow / 3;
        int smallBoardJ = lastMoveCol / 3;

        // 1. small board win
        if (board.getSilhouette()[smallBoardI][smallBoardJ] == topPlayer) {
            score += SMALL_BOARD_WIN;
            // 2. center board win
            if (smallBoardI == 1 && smallBoardJ == 1) {
                score += CENTER_BOARD_WIN;
            }

            // 3. corner board win
            if ((smallBoardI == 0 || smallBoardI == 2) && (smallBoardJ == 0 || smallBoardJ == 2)) {
                score += CORNER_BOARD_WIN;
            }
        }

        // 4. creating two in a row/column/diagonal in silhouette, that can later become three in a row/column/diagonal
        if (playerOpponentMarkAppearances(board.getSilhouette(), smallBoardI, smallBoardJ, 2, 0)) {
            score += CREATE_TWO_BOARD_WINNING_SEQUENCE;
        }


        // 5. creating two in a row/column/diagonal in a small board, that can later become three
        if (is2InARowColDiagonalAfterLastMove(board)) {
            score += CREATE_TWO_CELL_WINNING_SEQUENCE;
        }

        // 6. redirecting to a full or already won board ;
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
