package game.heuristic;

import game.board.BigBoard;

public interface StateEvaluationAi {
    int evaluateBoardAfterMove(BigBoard board);
}
