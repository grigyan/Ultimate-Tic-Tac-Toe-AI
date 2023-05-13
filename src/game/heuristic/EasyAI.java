package game.heuristic;

import game.board.BigBoard;
import game.heuristic.StateEvaluationAi;

public class EasyAI implements StateEvaluationAi {
    @Override
    public int evaluateBoardAfterMove(BigBoard board) {
        return 0;
    }
}
