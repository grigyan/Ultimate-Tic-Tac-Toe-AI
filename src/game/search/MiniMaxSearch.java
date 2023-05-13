package game.search;


import game.move.Action;
import game.board.BigBoard;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.heuristic.StateEvaluationAi;

import java.util.*;

public class MiniMaxSearch implements Search {
    private int noOfStates = 0;
    private static final int DEPTH_LIMIT = 7;
    private Random random = new Random();

    @Override
    public Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, StateEvaluationAi stateEvaluationAi) {
        HashMap<State, Action> strategy = new HashMap<>();
        maxValue(initialState, terminalTest, stateEvaluationAi, strategy, 0);
        return strategy;
    }

    private int maxValue(State currentState, EvaluationTest terminalTest, StateEvaluationAi stateEvaluationAi, HashMap<State, Action> strategy, int depth) {
        if (terminalTest.isTerminal(currentState)) {
            return terminalTest.getStateEvaluation(currentState);
        }

        if (depth == DEPTH_LIMIT) {
            return stateEvaluationAi.evaluateBoardAfterMove((BigBoard) currentState);
        }

        int bestMoveValue = Integer.MIN_VALUE;
        List<Action> bestActions = new ArrayList<>();

        for (Action action : currentState.getApplicableActions()) {
            State successor = currentState.getActionResult(action);
            noOfStates += 1;

            int newValue = minValue(successor, terminalTest, stateEvaluationAi, strategy, depth + 1);     //call to min()
            if (newValue >= bestMoveValue) {
                bestActions.add(action);
                bestMoveValue = newValue;
            }
        }
        Action bestAction = bestActions.get(random.nextInt(bestActions.size())); // choose random best action from available best actions

        strategy.put(currentState, bestAction);
        return bestMoveValue;
    }

    private int minValue(State currentState, EvaluationTest terminalTest, StateEvaluationAi stateEvaluationAi, HashMap<State, Action> strategy, int depth) {
        if (terminalTest.isTerminal(currentState)) {
            return terminalTest.getStateEvaluation(currentState);
        }

        if (depth == DEPTH_LIMIT) {
            return stateEvaluationAi.evaluateBoardAfterMove((BigBoard) currentState) * (-1);
        }

        int bestMoveValue = Integer.MAX_VALUE;
        List<Action> bestActions = new ArrayList<>();

        for (Action action : currentState.getApplicableActions()) {
            State successor = currentState.getActionResult(action);
            noOfStates += 1;

            int newValue = maxValue(successor, terminalTest, stateEvaluationAi, strategy, depth + 1);     // call to max()
            if (newValue <= bestMoveValue) {
                bestMoveValue = newValue;
                bestActions.add(action);
            }
        }

        Action bestAction = bestActions.get(random.nextInt(bestActions.size())); // choose random best action from available best actions

        strategy.put(currentState, bestAction);
        return bestMoveValue;
    }





    @Override
    public int getNoOfStatesGenerated() {
        return noOfStates;
    }
}
