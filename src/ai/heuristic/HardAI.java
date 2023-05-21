package ai.heuristic;

import game.board.BigBoard;

/*

Hard AI Logic:
1. If opponent is sent to already won (or full) board -1
2. If opponent is sent to the small board where you have less than or equal number of marks -1
3. Center square of small board +2
4. Corner square of small board +1
5. Two in a row | col | diagonally that can be extended to win later +4
 */

public class HardAI implements StateEvaluationAi {

    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {

        return 0;
    }
}
