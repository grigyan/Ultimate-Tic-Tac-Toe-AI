package ai.heuristic.util;

import game.board.BigBoard;

import static game.board.BoardConstants.EMPTY;

public class MoveQualityCheckers {
    public static boolean isInCenterSquareOfSmallBoard(BigBoard board) {
        int lastMoveRow = board.getLastMove().getRow();
        int lastMoveCol = board.getLastMove().getColumn();

        return lastMoveRow % 3 == 1 && lastMoveCol % 3 == 1;
    }

    public static boolean is2InARowColDiagonalAfterLastMove(BigBoard board) {
        int lastMoveRow = board.getLastMove().getRow();
        int lastMoveCol = board.getLastMove().getColumn();
        int[][] copyOfLastMovesBoard = getCopyOfLastMovesBoard(board, lastMoveRow, lastMoveCol);

        return playerOpponentMarkAppearances(copyOfLastMovesBoard, lastMoveRow % 3, lastMoveCol % 3,
                2 ,0);
    }

    public static boolean isOpponent2BlockedAfterLastMove(BigBoard board) {
        int lastMoveRow = board.getLastMove().getRow();
        int lastMoveCol = board.getLastMove().getColumn();
        int[][] copyOfLastMovesBoard = getCopyOfLastMovesBoard(board, lastMoveRow, lastMoveCol);

        return playerOpponentMarkAppearances(copyOfLastMovesBoard, lastMoveRow % 3, lastMoveCol % 3,
                1, 2);
    }

    // returns true if player mark count and opponent mark count is present in the row, column or diagonal of the last move
    public static boolean playerOpponentMarkAppearances(int[][] board, int lastMoveRow, int lastMoveCol,
                                                  int playerMarkCount, int opponentMarkCount) {
        // lastMoveRow, lastMoveCol -> [0, 2]
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

        if (rowCount == playerMarkCount && opponentCount == opponentMarkCount) {
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
        if (colCount == playerMarkCount && opponentCount == opponentMarkCount) {
            return true;
        }

        // check diagonals if the last move is in one
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

            if ((mainDiagonalCount == playerMarkCount && mainDiagonalOpponentCount == opponentMarkCount) ||
                    (antiDiagonalCount == playerMarkCount && antiDiagonalOpponentCount == opponentMarkCount)) {
                return true;
            }
        }

        // no searched sequences were created
        return false;
    }

    private static int[][] getCopyOfLastMovesBoard(BigBoard board, int lastMoveRow, int lastMoveCol) {
        int[][] copyOfSmallBoard = new int[3][3];
        int leftTopCellRow = (lastMoveRow / 3) * 3;     // left top cell row from the small board of the last move
        int leftTopCellCol = (lastMoveCol / 3) * 3;     // left top cell col from the small board of the last move
        for (int i = leftTopCellRow, copyBoardRow = 0; i < leftTopCellRow + 3; i++, copyBoardRow++) {
            for (int j = leftTopCellCol, copyBoardCol = 0; j < leftTopCellCol + 3; j++, copyBoardCol++) {
                copyOfSmallBoard[copyBoardRow][copyBoardCol] = board.getCell(i, j);
            }
        }
        return copyOfSmallBoard;
    }

}
