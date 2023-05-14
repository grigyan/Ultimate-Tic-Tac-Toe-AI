package ai.heuristic;

import game.board.BigBoard;

public interface StateEvaluationAi {
    int evaluateBoardAfterMove(BigBoard board);
}
