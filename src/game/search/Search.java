package game.search;

import game.move.Action;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.player.Player;

import java.util.Map;

public interface Search {
    Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, Player player, Player opponent);

    int getNoOfStatesGenerated();
}
