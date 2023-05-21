package ai.heuristic;

import ai.heuristic.util.MoveQualityCheckers;
import game.board.BigBoard;
import game.move.Move;
import game.player.PlayerEnum;

import static ai.heuristic.util.MoveQualityCheckers.*;

/*
Hard AI Logic:

 */

public class HardAI implements StateEvaluationAi {

    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {
        return 0;
    }
}
