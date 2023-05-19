package ai.heuristic;

import game.player.PlayerEnum;
import game.move.Move;
import game.board.BigBoard;

/*
Variables:
1. currentPlayer is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
2. opponent is the opponent player for currentPlayer:)
3. score is the evaluation for the given position. It multiplied by -1 if currentPlayer is MIN.

Medium AI Logic:
1. Small board win +5
2. Center board win +10
3. Corner board win +3
4. Two board win that can be extended to win + 4


 */




public class MediumAI implements StateEvaluationAi {
    public int evaluateBoardAfterLastMove(BigBoard board) {
        int topPlayer = board.getPlayer().getPlayerEnum() == PlayerEnum.MIN ? 1 : 2;
        int opponent = topPlayer == 1 ? 2 : 1;
        int score = 0;

        Move move = (Move) board.getLastMove();
        int moveRow = move.getRow();
        int moveColumn = move.getColumn();

        // winning the small board
        if (board.getSilhouette()[moveRow / 3][moveColumn / 3] == topPlayer) {
            // winning the center board
            if (moveRow / 3 == 1 && moveColumn / 3 == 1) {
                score += 10;
            }

            // winning the corner board
            if ((moveRow / 3 == 0 || moveRow / 3 == 2) && (moveColumn / 3 == 0 || moveColumn / 3 == 2)) {
                score += 3;
            }

            score += 5;
        }

        // getting the center square in any small board
        if ((moveRow == 1 || moveRow == 4 || moveRow == 7) && (moveColumn == 1 || moveColumn == 4 || moveColumn == 7)) {
            score += 3;
        }

        // getting square in the center board
        if (moveRow / 3 == 1 && moveColumn / 3 == 1) {
            score += 3;
        }


        return topPlayer == 1 ? score : (-1) * score;
    }
}

/*
        // check if it is a winning move +100
        if (board.isSilhouetteWon()) {
            return 2000;
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
 */
