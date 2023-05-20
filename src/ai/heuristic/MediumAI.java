package ai.heuristic;

import game.player.PlayerEnum;
import game.move.Move;
import game.board.BigBoard;

import static game.board.BoardConstants.EMPTY;

/*
Variables:
1. topPlayer is the player for whom we do the evaluation (e.g. last move was done by MAX we need to evaluate for MAX).
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

        Move lastMove = board.getLastMove();
        int lastMoveRow = lastMove.getRow();
        int lastMoveCol = lastMove.getColumn();
        int smallBoardI = lastMoveRow / 3;
        int smallBoardJ = lastMoveCol / 3;

        // winning the small board
        if (board.getSilhouette()[smallBoardI][smallBoardJ] == topPlayer) {
            score += 5;
            // winning the center board
            if (smallBoardI == 1 && smallBoardJ == 1) {
                score += 5;
            }

            // winning the corner board
            if ((smallBoardI == 0 || smallBoardI == 2) && (smallBoardJ == 0 || smallBoardJ == 2)) {
                score += 3;
            }
        }

        // creating two in a row/column/diagonal that can later become three in a row/column/diagonal
        if (createsWinningSequenceInBoard(board.getSilhouette(), smallBoardI, smallBoardJ)) {
            score += 10;
        }


        int[][] copyOfSmallBoard = new int[3][3];
        int leftTopCellRow = (lastMoveRow / 3) * 3;     // left top cell row from the small board of the last move
        int leftTopCellCol = (lastMoveRow / 3) * 3;     // left top cell col from the small board of the last move
        for (int i = leftTopCellRow, copyBoardRow = 0; i < leftTopCellRow + 3; i++, copyBoardRow++) {
            for (int j = leftTopCellCol, copyBoardCol = 0; j < leftTopCellCol + 3; j++, copyBoardCol++) {
                copyOfSmallBoard[copyBoardRow][copyBoardCol] = board.getCell(i, j);
            }
        }

        if (createsWinningSequenceInBoard(copyOfSmallBoard, lastMoveRow % 3, lastMoveCol % 3)) {
            score += 3;
        }

        return topPlayer == 1 ? score : (-1) * score;
    }

    private boolean createsWinningSequenceInBoard(int[][] board, int lastMoveRow, int lastMoveCol) {
        int player = board[lastMoveRow][lastMoveCol];

        // check rows
        int rowCount = 0;
        int opponentCount = 0;
        for (int col = 0; col < 3; col++) {
            if (board[lastMoveRow][col] == player) {
                rowCount++;
            } else if (board[lastMoveRow][col] != EMPTY) {
                opponentCount++;
            }
        }

        if (rowCount == 2 && opponentCount == 0) {
            return true;
        }

        // check columns
        int colCount = 0;
        opponentCount = 0;
        for (int row = 0; row < 3; row++) {
            if (board[row][lastMoveCol] == player) {
                colCount++;
            } else if (board[row][lastMoveCol] != EMPTY) {
                opponentCount++;
            }
        }
        if (colCount == 2 && opponentCount == 0) {
            return true;
        }

        // check diagonal if the last move is on one
        if (lastMoveRow == lastMoveCol || lastMoveRow + lastMoveCol == 2) {
            int mainDiagonalCount = 0;
            int mainDiagonalOpponentCount = 0;

            int antiDiagonalCount = 0;
            int antiDiagonalOpponentCount = 0;

            for (int i = 0; i < 3; i++) {
                if (board[i][i] == player) {
                    mainDiagonalCount++;
                } else if (board[i][i] != EMPTY) {
                    mainDiagonalOpponentCount++;
                }
                if (board[i][2 - i] == player) {
                    antiDiagonalCount++;
                } else if (board[i][2 - i] != EMPTY) {
                    antiDiagonalOpponentCount++;
                }
            }

            if ((mainDiagonalCount == 2 && mainDiagonalOpponentCount == 0) || (antiDiagonalCount == 2 && antiDiagonalOpponentCount == 0)) {
                return true;
            }
        }

        // no winning sequences created
        return false;
    }

}
