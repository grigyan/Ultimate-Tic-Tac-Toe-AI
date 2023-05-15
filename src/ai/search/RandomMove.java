package ai.search;

import game.board.State;
import game.evaluation.EvaluationTest;
import game.move.Action;
import game.player.PlayerEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMove implements Search {
    private final Random random = new Random();

    @Override
    public Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, PlayerEnum playerEnum) {
        Map<State, Action> strategy = new HashMap<>();
        List<Action> allActions = initialState.getApplicableActions();
        Action randomAction = allActions.get(random.nextInt(allActions.size()));
        strategy.put(initialState, randomAction);

        return strategy;
    }

    @Override
    public int getNoOfStatesGenerated() {
        return 0;
    }
}
