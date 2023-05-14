package ai.search;

import game.move.Action;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.player.PlayerEnum;

import java.util.Map;

public interface Search {
    Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, PlayerEnum playerEnum);

    int getNoOfStatesGenerated();
}
