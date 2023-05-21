package demo;

import ai.heuristic.MonteCarloAI;
import ai.heuristic.RandomAI;
import ai.search.AlphaBeta;
import ai.search.Search;
import game.move.Action;
import game.move.Move;
import game.player.Player;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.print.TicTacToePrinting;
import game.board.BigBoard;
import game.evaluation.BigBoardEvaluationTest;
import ai.heuristic.EasyAI;
import ai.heuristic.MediumAI;


import java.util.Scanner;

import static game.player.PlayerEnum.MAX;
import static game.player.PlayerEnum.MIN;

public class MiniMaxDemo {
    public static int xWon = 0;
    public static int oWon = 0;
    public static int draw = 0;

    public static int[][] midGame = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 2, 0, 2, 0},
    };
    //public static BigBoard midGameBoard = new BigBoard(mediumAiMax, easyAiMin, MiniMaxDemo.midGame, new Move(5, 6));


    public static void main(String[] args) {
        System.out.println("This is a demonstration of minimax search on ultimate tic-tac-toe");

        // MAX players
        Player randomMax = new Player(MAX, new RandomAI());      // for random players depth is set to 1 automatically
        Player easyAiMax = new Player(MAX, new EasyAI(), 2);
        Player mediumAiMax = new Player(MAX, new MediumAI(), 1);
        Player monteCarloMax = new Player(MAX, new MonteCarloAI(), 1);

        // MIN players
        Player randomMin = new Player(MIN, new RandomAI());      // for random players depth is set to 1 automatically
        Player easyAiMin = new Player(MIN, new EasyAI(), 1);
        Player mediumAiMin = new Player(MIN, new MediumAI(), 5);
        Player monteCarloMin = new Player(MIN, new MonteCarloAI(), 3);

        // Boards
        BigBoard initialBoard = new BigBoard(mediumAiMax, easyAiMin);

        for (int i = 1; i <= 100; i++) {
            gamePlay(initialBoard, new AlphaBeta(), false);
            System.out.println(i);
        }
        System.out.println("----------------");
        System.out.println("X won " + xWon);
        System.out.println("O won " + oWon);
        System.out.println("Draw " + draw);
        System.out.println("----------------");


    }

    public static void gamePlay(State state, Search search, boolean print) {
        EvaluationTest evaluationTest = new BigBoardEvaluationTest();
        TicTacToePrinting ticTacToePrinting = new TicTacToePrinting();

        while (!evaluationTest.isTerminal(state)) {
            Action nextAction = search
                    .findStrategy(state, evaluationTest, state.getPlayer()).get(state);
            state = state.getActionResult(nextAction);

            if (print) {
                int row = state.getLastMove().getRow() + 1;
                int col = state.getLastMove().getColumn() + 1;
                String mark = state.getPlayer().getPlayerEnum() == MAX ? "O" : "X";
                System.out.println(mark + " in (" + row + ", " + col + ")");
                ticTacToePrinting.print(state);
            }
        }

        BigBoard board = (BigBoard) state;
        if (board.isSilhouetteWon()) {
            if (board.getPlayer().getPlayerEnum() == MIN) {
                xWon++;
            } else {
                oWon++;
            }
            if (print) {
                System.out.println(board.getPlayer().getPlayerEnum() == MAX ? "O won the game" : "X won the game");
            }
        } else {
            draw++;
            if (print) {
                System.out.println("Game ended with a draw");
            }
        }
        if (print) {
            int totalStates = search.getNoOfStatesGenerated();
            System.out.println("Total number of states generated: " + totalStates);
        }
    }

    // last move index 5, 6

}
