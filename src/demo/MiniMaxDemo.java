package demo;

import ai.search.Search;
import game.move.Action;
import game.player.Player;
import game.board.State;
import game.evaluation.EvaluationTest;
import ai.search.MiniMax;
import game.print.TicTacToePrinting;
import game.board.BigBoard;
import game.evaluation.BigBoardEvaluationTest;
import ai.heuristic.EasyAI;
import ai.heuristic.MediumAI;


import static game.player.PlayerEnum.MAX;
import static game.player.PlayerEnum.MIN;

public class MiniMaxDemo {
    public static int xWon = 0;
    public static int oWon = 0;
    public static int draw = 0;

    public static void main(String[] args) {
        System.out.println("This is a demonstration of minimax search on ultimate tic-tac-toe");

        Player mediumAiMax = new Player(MAX, new MediumAI(), 3);
        Player mediumAiMin = new Player(MIN, new MediumAI(), 5);

        Player easyAiMax = new Player(MAX, new EasyAI(), 1);
        Player easyAiMin = new Player(MIN, new EasyAI(), 1);

        BigBoard initialBoard = new BigBoard(mediumAiMax, mediumAiMin);

        for (int i = 1; i <= 10; i++)
            gamePlay(initialBoard, new MiniMax(initialBoard.getPlayer()), false);

        System.out.println("X won " + xWon);
        System.out.println("O won " + oWon);
        System.out.println("Draw " + draw);
        xWon = 0;
        oWon = 0;
        draw = 0;

        // TODO: - make available actions to be stored in a List instead of Set
        // TODO: - create absolutely random ai
        // TODO: + make easy ai to actually play randomly unless possible to win
        // TODO: - organise the code in a better structured way
        // TODO: - create Player class to store depth limit, state score and player type
        // TODO: - keep track of number of moves for each game
        // TODO: -
    }


    public static void gamePlay(State state, Search search, boolean print) {
        EvaluationTest evaluationTest = new BigBoardEvaluationTest();
        TicTacToePrinting ticTacToePrinting = new TicTacToePrinting();

        if (print) {
            System.out.println("Last move was in: " + state.getLastMove().getRow() + " " + state.getLastMove().getColumn());
            System.out.println("State now: ");
            ticTacToePrinting.print(state);
        }

        while(!evaluationTest.isTerminal(state)) {
            if (state.getPlayer().getPlayerEnum() == MAX) { // MAX to play
                Action nextAction = search
                        .findStrategy(state, evaluationTest, MAX).get(state);
                state = state.getActionResult(nextAction);

                if (print) {
                    int row = state.getLastMove().getRow() + 1;
                    int col = state.getLastMove().getColumn() + 1;
                    System.out.println("X in (" + row  + ", " + col + ")");
                }
            } else { // MIN to play
                Action nextAction = search
                        .findStrategy(state, evaluationTest, MIN).get(state);
                state = state.getActionResult(nextAction);

                if (print) {
                    int row = state.getLastMove().getRow() + 1;
                    int col = state.getLastMove().getColumn() + 1;
                    System.out.println("O in (" + row  + ", " + col + ")");
                }
            }
            if (print) {
                ticTacToePrinting.print(state);

            }
        }

        BigBoard board = (BigBoard) state;
        if (board.isSilhouetteWon()) {
            if (board.getPlayer().getPlayerEnum() == MAX) {
                oWon++;
            } else {
                xWon++;
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
        int totalStates = search.getNoOfStatesGenerated();
        System.out.println("Total number of states generated: " + totalStates);
    }

    // Mid-game state. 1 to do the next move in upper middle board.
    // Last move was in the bottom mid-board's upper mid-cell [6, 4].
    static int[][] midGame = {
            {1, 0, 2, 0, 0, 0, 2, 0, 2},
            {0, 0, 1, 1, 0, 0, 0, 1, 0},
            {2, 0, 1, 0, 2, 2, 1, 1, 0},
            {0, 1, 0, 2, 0, 1, 2, 1, 0},
            {2, 0, 0, 2, 0, 1, 0, 0, 0},
            {2, 1, 0, 0, 0, 0, 0, 0, 2},
            {0, 2, 2, 1, 2, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 2, 0, 2, 0},
            {1, 0, 0, 0, 0, 0, 0, 2, 1}
    };

    // One Move Before winning state
    // last move -> 0 2
    // winning move -> 0,7
    static int[][] oneMoveBeforeWinningState = {
            {1, 0, 2, 0, 1, 0, 2, 0, 2},
            {0, 2, 1, 1, 0, 0, 0, 1, 0},
            {2, 0, 1, 2, 2, 2, 1, 1, 0},
            {0, 1, 0, 2, 0, 1, 2, 1, 0},
            {2, 0, 0, 2, 0, 1, 0, 0, 0},
            {2, 1, 0, 0, 0, 1, 0, 0, 2},
            {1, 2, 2, 1, 2, 0, 0, 2, 1},
            {1, 0, 0, 0, 1, 2, 0, 2, 0},
            {1, 0, 0, 0, 0, 0, 0, 2, 1}
    };
    //    BigBoard midGameBoard = new BigBoard(mediumAiMax, easyAiMin, midGame, new Move(6, 4));


}
