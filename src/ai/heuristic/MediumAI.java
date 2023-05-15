package ai.heuristic;

import game.player.PlayerEnum;
import game.move.Action;
import game.move.Move;
import game.board.BigBoard;

public class MediumAI implements StateEvaluationAi {
    public int evaluateBoardAfterLastMove(BigBoard board) {
        int currentPlayer = board.getPlayer().getPlayerEnum() == PlayerEnum.MAX ? 2 : 1;
        int opponent = currentPlayer == 1 ? 2 : 1;
        int score = 0;

        Action action = board.getLastMove();

        Move move = (Move) action;
        int moveRow = move.getRow();
        int moveColumn = move.getColumn();

        // check if it is a winning move +100
        BigBoard successor = (BigBoard) board.getActionResult(action);
        if (board.getSilhouette()[moveRow / 3][moveColumn / 3] != successor.getSilhouette()[moveRow / 3][moveColumn / 3]) {
            score += 100;
        }
        //

        // blocks opponent's two in a row or two in a column +40
        if (moveColumn % 3 == 0) {  // move was done at the leftmost cell of the row
            if (board.getCell(moveRow, moveColumn + 1) == opponent &&
                    board.getCell(moveRow, moveColumn + 2) == opponent) {
                score += 40;
            }
        } else if (moveColumn % 3 == 1) { // move was done at the mid-cell of the row
            if (board.getCell(moveRow, moveColumn - 1) == opponent &&
                    board.getCell(moveRow, moveColumn + 1) == opponent) {
                score += 40;
            }
        } else { // move was done at the rightmost cell of the row
            if (board.getCell(moveRow, moveColumn - 2) == opponent &&
                    board.getCell(moveRow, moveColumn - 1) == opponent) {
                score += 40;
            }
        }

        if (moveRow % 3 == 0) { // move was done at the top cell of the column
            if (board.getCell(moveRow + 1, moveColumn) == opponent &&
                    board.getCell(moveRow + 2, moveColumn) == opponent) {
                score += 40;
            }
        } else if (moveRow % 3 == 1) { // move was done at the mid-cell of the column
            if (board.getCell(moveRow - 1, moveColumn) == opponent &&
                    board.getCell(moveRow + 1, moveColumn) == opponent) {
                score += 40;
            }
        } else { // move was done at the bottom-most cell of the column
            if (board.getCell(moveRow - 1, moveColumn) == opponent &&
                    board.getCell(moveRow - 2, moveColumn) == opponent) {
                score += 40;
            }
        }
        //

        // center: +40
        if (moveRow % 3 == 1 && moveColumn % 3 == 1) {
            score += 40;
        }
        // mid-cell: +30
        if ((moveRow % 3 == 1 && moveColumn % 3 == 0) ||
                (moveRow % 3 == 1 && moveColumn % 3 == 2) ||
                (moveRow % 3 == 0 && moveColumn % 3 == 1) ||
                (moveRow % 3 == 2 && moveColumn % 3 == 1)) {
            score += 30;
        }


        return score;
    }
}
