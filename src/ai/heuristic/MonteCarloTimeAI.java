package ai.heuristic;

import game.board.BigBoard;
import game.evaluation.BigBoardEvaluationTest;
import game.move.Action;

import java.util.List;
import java.util.Random;

public class MonteCarloTimeAI implements StateEvaluationAi {
    int numberOfGames = 100;
    BigBoardEvaluationTest evaluationTest = new BigBoardEvaluationTest();
    Random random = new Random();

    @Override
    public int evaluateBoardAfterLastMove(BigBoard board) {
        double xWon = 0;
        double oWon = 0;
        for (int i = 1; i <= numberOfGames; i++) {
            BigBoard copy = board;
            while(!evaluationTest.isTerminal(copy)) {
                List<Action> applicableActions = copy.getApplicableActions();
                Action nextRandomAction = applicableActions.get(random.nextInt(applicableActions.size()));
                copy = (BigBoard) copy.getActionResult(nextRandomAction);
            }

            int result = evaluationTest.getStateEvaluation(copy);
            if (result == 0) {
                xWon += 0.5;
                oWon += 0.5;
            } else if (result > 0) {
                xWon += 1;
            } else {
                oWon += 1;
            }
        }

        return (int) xWon;
//        return board.getPlayer().getPlayerEnum() == PlayerEnum.MAX ? (int )oWon : (int) xWon;
//        return board.getPlayer().getPlayerEnum() == MAX ? (int) xWon - (int) oWon : (int) oWon - (int) xWon;
    }
}
