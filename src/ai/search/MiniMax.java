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

public class MiniMax implements Search {
    private int noOfStates = 0;


    @Override
    public int getNoOfStatesGenerated() {
        return noOfStates;
    }

    @Override
    public Map<State, Action> findStrategy(State initialState, EvaluationTest terminalTest, Player player) {
        HashMap<State, Action> strategy = new HashMap<>();
        if (player.getPlayerEnum() == MAX) {
            maxValue(initialState, terminalTest, strategy, 0);
        } else {
            minValue(initialState, terminalTest, strategy, 0);
        }

        return strategy;
    }

    private int maxValue(State currentState, EvaluationTest terminalTest,
                         HashMap<State, Action> strategy, Player player, int depth) {
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

            int newValue = minValue(successor, terminalTest, strategy, player, depth + 1);     //call to min()
            if (newValue >= bestMoveValue) {
                maxActions.insert(newValue, action);
                bestMoveValue = newValue;
            }
        }

        priorityQueue.Entry<Integer, Action> bestMove = maxActions.removeMin();

        strategy.put(currentState, bestMove.getValue());
        return bestMove.getKey();
    }

    private int minValue(State currentState, EvaluationTest terminalTest,
                         HashMap<State, Action> strategy, Player player, int depth) {
        if (terminalTest.isTerminal(currentState)) {
            return terminalTest.getStateEvaluation(currentState);
        }

        if (depth == player.getDepthLimit()) {
            return player.getStateEvaluationAi().evaluateBoardAfterLastMove((BigBoard) currentState);
        }

        int bestMoveValue = Integer.MAX_VALUE;
        MinHeapPriorityQueue<Integer, Action> minActions = new MinHeapPriorityQueue<>();


        for (Action action : currentState.getApplicableActions()) {
            State successor = currentState.getActionResult(action);
            noOfStates += 1;

            int newValue = maxValue(successor, terminalTest, strategy, player, depth + 1);     // call to max()
            if (newValue <= bestMoveValue) {
                minActions.insert(newValue, action);
                bestMoveValue = newValue;
            }
        }
        priorityQueue.Entry<Integer, Action> bestMove = minActions.removeMin();

        strategy.put(currentState, bestMove.getValue());
        return bestMove.getKey();
    }

}
