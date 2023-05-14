package ai.search;

import game.player.PlayerEnum;
import game.move.Action;
import game.board.State;
import game.evaluation.EvaluationTest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CutOffSearch implements Search {
    private int noOfStates = 0;
    private static final int DEPTH_LIMIT = 2;

    @Override
    public Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, PlayerEnum playerEnum) {
        LinkedHashMap<State, Action> strategy = new LinkedHashMap<>();


        return strategy;
    }

    public int minimaxAtDepth(State state, int depth, EvaluationTest terminalTest) {
        PlayerEnum maximizingPlayer = state.getPlayer().getPlayerEnum();
        if (terminalTest.isTerminal(state)) {
            return terminalTest.getStateEvaluation(state);
        }

        if (depth == 0) {
            // return static evaluation of the position
        }

        if (state.getPlayer().getPlayerEnum() == PlayerEnum.MAX) {
            int maxEval = Integer.MIN_VALUE;
            List<Action> applicableActions = state.getApplicableActions();

            for (Action action : applicableActions) {
                State stateAfterAction = state.getActionResult(action);
                int currentEval = minimaxAtDepth(stateAfterAction, depth - 1, terminalTest);
                maxEval = Math.max(maxEval, currentEval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            List<Action> applicableActions = state.getApplicableActions();

            for (Action action : applicableActions) {
                State stateAfterAction = state.getActionResult(action);
                int currentEval = minimaxAtDepth(stateAfterAction, depth - 1, terminalTest);
                minEval = Math.min(minEval, currentEval);
            }
            return minEval;
        }
    }

    // when making first call pass alpha to be Integer.MIN and beta to be Integer.MAX
    public int alphaBetaAtDepth(State state, int depth, EvaluationTest terminalTest, int alpha, int beta) {
        PlayerEnum maximizingPlayer = state.getPlayer().getPlayerEnum();
        if (terminalTest.isTerminal(state)) {
            return terminalTest.getStateEvaluation(state);
        }

        if (depth == 0) {
            // return static evaluation of the position
        }

        if (state.getPlayer().getPlayerEnum() == PlayerEnum.MAX) {
            int maxEval = Integer.MIN_VALUE;
            List<Action> applicableActions = state.getApplicableActions();

            for (Action action : applicableActions) {
                State stateAfterAction = state.getActionResult(action);
                int currentEval = alphaBetaAtDepth(stateAfterAction, depth - 1, terminalTest, alpha, beta);
                maxEval = Math.max(maxEval, currentEval);
                alpha = Math.max(alpha, currentEval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            List<Action> applicableActions = state.getApplicableActions();

            for (Action action : applicableActions) {
                State stateAfterAction = state.getActionResult(action);
                int currentEval = alphaBetaAtDepth(stateAfterAction, depth - 1, terminalTest, alpha, beta);
                minEval = Math.min(minEval, currentEval);
                beta = Math.min(beta, currentEval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    @Override
    public int getNoOfStatesGenerated() {
        return noOfStates;
    }

}
