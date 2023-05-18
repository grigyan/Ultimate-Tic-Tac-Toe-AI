package ai.heuristic;

import game.board.BigBoard;

import static game.player.PlayerEnum.MIN;

/*
Variables:
1. topPlayer -> is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
2. opponent -> is the opponent player for topPlayer:)
3. score -> evaluation score for the given position

Easy AI Logic:

1. Return number of small boards won by topPlayer minus number of small boards lost by topPlayer
    1.1 If we are evaluating for MIN (topPlayer == 2) multiply result by (-1)

 */


public class EasyAI implements StateEvaluationAi {

    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {
        int[][] silhouette = board.getSilhouette();
        int topPlayer = board.getPlayer().getPlayerEnum() == MIN ? 1 : 2;
        int opponent = 3 - topPlayer; // if 2 then 1, if 1 then 2

        int boardsWon = 0;
        int boardLost = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (silhouette[i][j] == topPlayer) {
                    boardsWon++;
                } else if (silhouette[i][j] == opponent) {
                    boardLost++;
                }
            }
        }

        int score = boardsWon - boardLost;

        return topPlayer == 1 ? score : (-1) * score;
    }

}
