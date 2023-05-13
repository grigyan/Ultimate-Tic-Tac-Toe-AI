package game.demo;

import game.move.Action;
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

public class MiniMaxDemo {
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

    static int[][] terminal = {
    {1,2,2,1,1,1,2,1,2},
    {0,2,1,1,1,2,2,1,1},
    {2,0,1,0,2,2,1,1,2},
    {0,1,0,2,2,1,2,1,2},
    {2,1,0,2,1,1,0,2,1},
    {2,1,0,2,2,0,2,0,2},
    {0,2,2,1,2,1,1,1,1},
    {1,2,1,2,1,2,0,2,2},
    {1,1,1,2,1,2,1,2,1}};


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

    public static int xWon = 0;
    public static int oWon = 0;
    public static int draw = 0;

    public static void gamePlay(State state) {
        EvaluationTest evaluationTest = new BigBoardEvaluationTest();
        Search minimax = new MiniMaxSearch();
        TicTacToePrinting ticTacToePrinting = new TicTacToePrinting();
        MediumAi mediumAi = new MediumAi();
        EasyAI easyAi = new EasyAI();


        while(!evaluationTest.isTerminal(state)) {
            if (state.getPlayer() == MAX) { // MAX to play
                Action nextAction = minimax.findStrategy(state, evaluationTest, mediumAi).get(state);
                state = state.getActionResult(nextAction);
                //System.out.println("X in (" + state.getLastMove().getRow() + ", " + state.getLastMove().getColumn() + ")");
            } else { // MIN to play
                Action nextAction = minimax.findStrategy(state, evaluationTest, easyAi).get(state);
                state = state.getActionResult(nextAction);
                //System.out.println("O in (" + state.getLastMove().getRow() + ", " + state.getLastMove().getColumn() + ")");
            }
            //ticTacToePrinting.print(state);
        }

        BigBoard board = (BigBoard) state;
        if (board.isSilhouetteWon()) {
            if (board.getPlayer() == MAX) {
                oWon++;
            } else {
                xWon++;
            }

            //System.out.println(board.getPlayer() == MAX ? "O won the game" : "X won the game");
        } else {
            draw++;
            //System.out.println("Game ended with a draw");
        }

    }


    public static void main(String[] args) {
        System.out.println("This is a demonstration of minimax search on ultimate tic-tac-toe");
        System.out.println();
        BigBoard initialState = new BigBoard();
        BigBoard midGameBoard = new BigBoard(MAX, midGame, new Move(6, 4));
        //System.out.println("Last move was: O in 6, 4");
        for (int i = 0; i < 1; i++) {
            gamePlay(midGameBoard);
        }

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
}


//BigBoard oneMoveBeforeWinning = new BigBoard(MAX, oneMoveBeforeWinningState, new Move(0,2));
/*
O played: 8, 5
then
last move: 6, 7
O's turn to play
\/
  |-0---1---2-||-3---4---5-||-6---7---8-|
0 | X   O   O || X   X   X || O   X   O |
1 |     O   X || X   X   O || O   X   X |
2 | O       X ||     O   O || X   X   O |
  |-----------||-----------||-----------|
3 |     X     || O   O   X || O   X   O |
4 | O   X     || O   X   X ||     O   X |
5 | O   X     || O   O     || O       O |
  |-----------||-----------||-----------|
6 |     O   O || X   O   X || X   X   X |
7 | X   O   X || O   X   O ||     O   O |
8 | X   X   X || O   X   O || X   O   X |
  |-----------||-----------||-----------|
*
*
* */
