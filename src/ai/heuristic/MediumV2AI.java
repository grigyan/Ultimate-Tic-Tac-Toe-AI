package ai.heuristic;

import game.board.BigBoard;

import static ai.heuristic.util.MoveQualityCheckers.*;
import static game.board.BoardConstants.EMPTY;
import static game.player.PlayerEnum.MIN;


/*
Variables:
1. topPlayer is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
2. opponent is the opponent player for currentPlayer:)
3. score is the evaluation for the given position. It multiplied by -1 if currentPlayer is MIN.
4. smallBoardWonAndLostDifference is the difference between number of small boards that were won and were lost


Medium AI Logic:
1. Number of small board won, number of small boards blocked
2.
 */
public class MediumV2AI implements StateEvaluationAi {
    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {
        int topPlayer = board.getPlayer().getPlayerEnum() == MIN ? 1 : 2;
        int opponent = topPlayer == 1 ? 2 : 1;
        int score = 0;
        int smallBoardWonAndLostDifference = new EasyAI().evaluateBoardAfterLastMove(board);

        if (isOpponent2BlockedAfterLastMove(board)) {
            score += 5;
        }
        if (is2InARowColDiagonalAfterLastMove(board)) {
            score += 5;
        }
        if (isInCenterSquareOfSmallBoard(board)) {
            score += 2;
        }

        return topPlayer == 1 ? score : (-1) * score;
    }

}
