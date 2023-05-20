package ai.heuristic;

import game.board.BigBoard;

public interface StateEvaluationAi {
    default int evaluateBoardAfterLastMove(BigBoard board) {
        return 0;
    };
}
