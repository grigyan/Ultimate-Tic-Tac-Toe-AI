package game.evaluation;

import game.player.PlayerEnum;
import game.board.State;
import game.board.BigBoard;

import static game.player.PlayerEnum.MAX;

public class BigBoardEvaluationTest extends EvaluationTest {

    @Override
    public boolean isTerminal (State state) {
        BigBoard board = (BigBoard) state;

        // Case 1: someone won
        if (board.isSilhouetteWon()) {
            PlayerEnum player = board.getPlayer();
            stateEvaluationMap.put(state, player == MAX ? -1000 : 1000);
            return true;
        } else if (board.isSilhouetteFull()) {
            stateEvaluationMap.put(state, 0);
            return true;
        }
        // Case 2: board is full
        else if (board.isGridFull()) {
            stateEvaluationMap.put(state, 0);
            return true;
        }
        // Case 3: board is not full, and nobody won, however there are no more valid moves left
        else if (board.getApplicableActions().size() == 0) {
            stateEvaluationMap.put(state, 0);
            return true;
        }

        return false;
    }

    @Override
    public void cutOffEvaluate (State state) {
        // in utilities put state with some value
    }

}
