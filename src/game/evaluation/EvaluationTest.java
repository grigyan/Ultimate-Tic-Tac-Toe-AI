package game.evaluation;

import game.board.State;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class EvaluationTest {
    protected final Map<State, Integer> stateEvaluationMap;

    public EvaluationTest() {
        stateEvaluationMap = new LinkedHashMap<>();
    }

    public int getStateEvaluation(State state) {
        return stateEvaluationMap.get(state);
    }
    public abstract boolean isTerminal(State state);

}
