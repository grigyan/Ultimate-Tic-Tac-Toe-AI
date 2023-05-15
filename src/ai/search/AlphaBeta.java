package ai.search;


import priorityQueue.MaxHeapPriorityQueue;
import priorityQueue.MinHeapPriorityQueue;
import game.move.Action;
import game.board.BigBoard;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.player.Player;
import game.player.PlayerEnum;

import java.util.*;

import static game.player.PlayerEnum.MAX;

public class AlphaBeta implements Search {
    private int noOfStates = 0;
    private Random random = new Random();
    private final Player player;

    public AlphaBeta(Player player) {
        this.player = player;
    }

    @Override
    public int getNoOfStatesGenerated() {
        return noOfStates;
    }

    @Override
    public Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, PlayerEnum playerEnum) {
        HashMap<State, Action> strategy = new HashMap<>();
        if (playerEnum == MAX) {
            maxValue(initialState, terminalTest, strategy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            minValue(initialState, terminalTest, strategy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        return strategy;
    }

    private int maxValue(State currentState, EvaluationTest terminalTest,
                         HashMap<State, Action> strategy, int depth, int alpha, int beta) {
        if (terminalTest.isTerminal(currentState)) {
            return terminalTest.getStateEvaluation(currentState);
        }

        if (depth == player.getDepthLimit()) {
            return player.getStateEvaluationAi().evaluateBoardAfterLastMove((BigBoard) currentState);
        }

        int bestMoveValue = Integer.MIN_VALUE;
        MaxHeapPriorityQueue<Integer, Action> maxActions = new MaxHeapPriorityQueue<>();

        for (Action action : currentState.getApplicableActions()) {
            State successor = currentState.getActionResult(action);
            noOfStates += 1;

            int newValue = minValue(successor, terminalTest, strategy, depth + 1, alpha, beta);     //call to min()
            if (newValue >= bestMoveValue) {
                maxActions.insert(newValue, action);
                bestMoveValue = newValue;
            }
            if (bestMoveValue >= beta) {
                return bestMoveValue;
            }
            alpha = Math.max(alpha, bestMoveValue);
        }

        priorityQueue.Entry<Integer, Action> bestMove = maxActions.removeMin();

        strategy.put(currentState, bestMove.getValue());
        return bestMove.getKey();
    }

    private int minValue(State currentState, EvaluationTest terminalTest,
                         HashMap<State, Action> strategy, int depth, int alpha, int beta) {
        if (terminalTest.isTerminal(currentState)) {
            return terminalTest.getStateEvaluation(currentState);
        }

        if (depth == player.getDepthLimit()) {
            return player.getStateEvaluationAi().evaluateBoardAfterLastMove((BigBoard) currentState) * (-1);
        }

        int bestMoveValue = Integer.MAX_VALUE;
        MinHeapPriorityQueue<Integer, Action> minActions = new MinHeapPriorityQueue<>();

        for (Action action : currentState.getApplicableActions()) {
            State successor = currentState.getActionResult(action);
            noOfStates += 1;

            int newValue = maxValue(successor, terminalTest, strategy, depth + 1, alpha, beta);     // call to max()
            if (newValue <= bestMoveValue) {
                minActions.insert(newValue, action);
                bestMoveValue = newValue;
            }
            if (bestMoveValue <= alpha) {
                return  bestMoveValue;
            }
            beta = Math.min(beta, bestMoveValue);
        }

        priorityQueue.Entry<Integer, Action> bestMove = minActions.removeMin();

        strategy.put(currentState, bestMove.getValue());
        return bestMove.getKey();
    }

    private Action getBestActionFromMap(Map<Action, Integer> actionToEvaluationMap, PlayerEnum player) {
        List<Action> actions = new ArrayList<>();
        if (player == MAX) {
            int max = Integer.MIN_VALUE;
            for (Map.Entry<Action, Integer> entry : actionToEvaluationMap.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                }
            }

            for (Map.Entry<Action, Integer> entry : actionToEvaluationMap.entrySet()) {
                if (entry.getValue() == max) {
                    actions.add(entry.getKey());
                }
            }
        } else {
            int min = Integer.MAX_VALUE;
            for (Map.Entry<Action, Integer> entry : actionToEvaluationMap.entrySet()) {
                if (entry.getValue() < min) {
                    min = entry.getValue();
                }
            }

            for (Map.Entry<Action, Integer> entry : actionToEvaluationMap.entrySet()) {
                if (entry.getValue() == min) {
                    actions.add(entry.getKey());
                }
            }
        }

        return actions.get(random.nextInt(actions.size()));
    }



}
