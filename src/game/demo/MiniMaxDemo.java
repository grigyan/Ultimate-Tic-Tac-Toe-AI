package game.demo;

import game.move.Action;
import game.player.Player;
import game.search.Search;
import game.board.State;
import game.evaluation.EvaluationTest;
import game.search.MiniMaxSearch;
import game.move.Move;
import game.print.TicTacToePrinting;
import game.board.BigBoard;
import game.evaluation.BigBoardEvaluationTest;
import game.heuristic.EasyAI;
import game.heuristic.MediumAi;


import static game.player.PlayerEnum.MAX;
import static game.player.PlayerEnum.MIN;

public class MiniMaxDemo {
    public static int xWon = 0;
    public static int oWon = 0;
    public static int draw = 0;

    public static void main(String[] args) {
        System.out.println("This is a demonstration of minimax search on ultimate tic-tac-toe");

        Player mediumAiMax = new Player(MAX, new MediumAi(), 3);
        Player easyAiMax = new Player(MAX, new EasyAI(), 1);
        Player easyAiMin = new Player(MIN, new EasyAI(), 1);

        BigBoard midChatGpt = new BigBoard(easyAiMax, easyAiMin, midGameFromChatGpt, new Move(8, 3));
        BigBoard midGameBoard = new BigBoard(mediumAiMax, easyAiMin, midGame, new Move(6, 4));


        BigBoard currentBoard = midChatGpt;
        System.out.println("Last move was in: " + currentBoard.getLastMove().getRow() + " " + currentBoard.getLastMove().getColumn());


        gamePlay(currentBoard);


        // TODO: - make available actions to be stored in a List instead of Set
        // TODO: - create absolutely random ai
        // TODO: + make easy ai to actually play randomly unless possible to win
        // TODO: - organise the code in a better structured way
        // TODO: - create Player class to store depth limit, state score and player type
        // TODO: - keep track of number of moves for each game
        // TODO: -

        System.out.println("X won " + xWon);
        System.out.println("O won " + oWon);
        System.out.println("Draw " + draw);
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

    // last move by O in 8, 3
    static int[][] midGameFromChatGpt = {
            {1,0,2,0,0,2,2,0,2},
            {0,1,1,1,1,0,0,1,0},
            {2,0,1,0,2,2,1,1,1},
            {0,1,0,2,1,1,2,1,1},
            {2,0,0,2,2,1,0,0,2},
            {2,1,0,0,0,2,0,0,2},
            {1,2,2,1,2,0,2,0,1},
            {1,2,0,0,1,2,0,2,1},
            {1,0,0,2,0,0,0,2,1}};

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

    public static void gamePlay(State state) {
        EvaluationTest evaluationTest = new BigBoardEvaluationTest();
        Search minimax = new MiniMaxSearch();
        TicTacToePrinting ticTacToePrinting = new TicTacToePrinting();


        System.out.println("State now: ");
        ticTacToePrinting.print(state);
        while(!evaluationTest.isTerminal(state)) {
            if (state.getPlayer().getPlayerEnum() == MAX) { // MAX to play
                Action nextAction = minimax.findStrategy(state, evaluationTest, state.getPlayer(), state.getOpponent()).get(state);
                state = state.getActionResult(nextAction);
                System.out.println("X in (" + state.getLastMove().getRow() + ", " + state.getLastMove().getColumn() + ")");
            } else { // MIN to play
                Action nextAction = minimax.findStrategy(state, evaluationTest, state.getPlayer(), state.getOpponent()).get(state);
                state = state.getActionResult(nextAction);
                System.out.println("O in (" + state.getLastMove().getRow() + ", " + state.getLastMove().getColumn() + ")");
            }
            ticTacToePrinting.print(state);
        }

        BigBoard board = (BigBoard) state;
        if (board.isSilhouetteWon()) {
            if (board.getPlayer().getPlayerEnum() == MAX) {
                oWon++;
            } else {
                xWon++;
            }
            System.out.println(board.getPlayer().getPlayerEnum() == MAX ? "O won the game" : "X won the game");
        } else {
            draw++;
            System.out.println("Game ended with a draw");
        }

    }

}
