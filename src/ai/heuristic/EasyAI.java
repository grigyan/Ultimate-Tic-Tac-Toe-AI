package ai.heuristic;

import game.board.BigBoard;

import static game.player.PlayerEnum.MIN;

/*
Variables:
1. currentPlayer is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
2. opponent is the opponent player for currentPlayer:)

Easy AI Logic:
1. Return number of small boards won minus number of small boards lost for the currentPlayer
    1.1 If we are evaluating for MIN (currentPlayer = 2) multiply result by (-1)

 */


public class EasyAI implements StateEvaluationAi {

    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {
        int[][] silhouette = board.getSilhouette();
        int player = board.getPlayer().getPlayerEnum() == MIN ? 1 : 2;
        int opponent = 3 - player; // if 2 then 1, if 1 then 2

        int boardsWon = 0;
        int boardLost = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (silhouette[i][j] == player) {
                    boardsWon++;
                } else if (silhouette[i][j] == opponent) {
                    boardLost++;
                }
            }
        }

        if (opponent == 1) {
            return boardsWon - boardLost;
        }

        return (boardsWon - boardLost) * -1;
    }

}
