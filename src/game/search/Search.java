package game.search;

import game.move.Action;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.heuristic.StateEvaluationAi;

import java.util.Map;

public interface Search {
    Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, StateEvaluationAi stateEvaluationAi);
    int getNoOfStatesGenerated();
}
