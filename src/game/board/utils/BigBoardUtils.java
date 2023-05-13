package game.board.utils;

import game.move.Action;
import game.move.Move;

import java.util.*;

import static game.board.BoardConstants.EMPTY;

public class BigBoardUtils {

    public static int[][] getSilhouetteFromGrid(int[][] grid) {
        int[][] silhouette = new int[3][3];
        for (int upperLeftCellRow = 0; upperLeftCellRow < 9; upperLeftCellRow += 3) {
            for (int upperLeftCellCol  = 0; upperLeftCellCol < 9; upperLeftCellCol += 3) {
                // check win by row
                boolean isCellWon = false;
                for (int i = upperLeftCellRow; i < upperLeftCellRow + 3 && !isCellWon; i++) {
                    if (grid[i][upperLeftCellCol] == grid[i][upperLeftCellCol + 1] &&
                            grid[i][upperLeftCellCol + 1] == grid[i][upperLeftCellCol + 2] &&
                            grid[i][upperLeftCellCol] != EMPTY) {
                        silhouette[upperLeftCellRow / 3][upperLeftCellCol / 3] = grid[i][upperLeftCellCol];
                        isCellWon = true;
                    }
                }

                // check win by column
                for (int i = upperLeftCellCol; i < upperLeftCellCol + 3 && !isCellWon; i++) {
                    if (grid[upperLeftCellRow][i] == grid[upperLeftCellRow + 1][i] &&
                            grid[upperLeftCellRow + 1][i] == grid[upperLeftCellRow + 2][i] &&
                            grid[upperLeftCellRow][i] != EMPTY) {
                        silhouette[upperLeftCellRow / 3][upperLeftCellCol / 3] = grid[upperLeftCellRow][i];
                        isCellWon = true;
                    }
                }

                // check win diagonally
                if (grid[upperLeftCellRow][upperLeftCellCol] == grid[upperLeftCellRow + 1][upperLeftCellCol + 1] &&
                        grid[upperLeftCellRow + 1][upperLeftCellCol + 1] == grid[upperLeftCellRow + 2][upperLeftCellCol + 2] &&
                        grid[upperLeftCellRow][upperLeftCellCol] != EMPTY && !isCellWon) {
                    silhouette[upperLeftCellRow / 3][upperLeftCellCol / 3] = grid[upperLeftCellRow][upperLeftCellCol];
                    isCellWon = true;
                }

                if (grid[upperLeftCellRow][upperLeftCellCol + 2] == grid[upperLeftCellRow + 1][upperLeftCellCol + 1] &&
                        grid[upperLeftCellRow + 1][upperLeftCellCol + 1] == grid[upperLeftCellRow + 2][upperLeftCellCol] &&
                        grid[upperLeftCellRow][upperLeftCellCol + 2] != EMPTY && !isCellWon) {
                    silhouette[upperLeftCellRow / 3][upperLeftCellCol / 3] = grid[upperLeftCellRow][upperLeftCellCol + 2];
                    isCellWon = true;
                }
            }
        }

        return silhouette;
    }

    public static void updateSilhouetteAfterLastMove(Move lastMove, int[][] newSilhouette, int[][] newBoard) {
        // coordinates of the upper left cell of the small board on which the last move was done
        int upperLeftCellRow = lastMove.getRow() - lastMove.getRow() % 3;
        int upperLeftCellCol = lastMove.getColumn() - lastMove.getColumn() % 3;

        // check win by rows
        for (int i = upperLeftCellRow; i < upperLeftCellRow + 3; i++) {
            if (newBoard[i][upperLeftCellCol] == newBoard[i][upperLeftCellCol + 1] &&
                    newBoard[i][upperLeftCellCol + 1] == newBoard[i][upperLeftCellCol + 2] && newBoard[i][upperLeftCellCol] != EMPTY) {
                newSilhouette[lastMove.getRow() / 3][lastMove.getColumn() / 3] = newBoard[i][upperLeftCellCol];
                return;
            }
        }

        // check win by columns
        for (int i = upperLeftCellCol; i < upperLeftCellCol + 3; i++) {
            if (newBoard[upperLeftCellRow][i] == newBoard[upperLeftCellRow + 1][i] &&
                    newBoard[upperLeftCellRow + 1][i] == newBoard[upperLeftCellRow + 2][i] && newBoard[upperLeftCellRow][i] != EMPTY) {
                newSilhouette[lastMove.getRow() / 3][lastMove.getColumn() / 3] = newBoard[upperLeftCellRow][i];
                return;
            }
        }

        // check win diagonally
        if (newBoard[upperLeftCellRow][upperLeftCellCol] == newBoard[upperLeftCellRow + 1][upperLeftCellCol + 1] &&
                newBoard[upperLeftCellRow + 1][upperLeftCellCol + 1] == newBoard[upperLeftCellRow + 2][upperLeftCellCol + 2] &&
                newBoard[upperLeftCellRow][upperLeftCellCol] != EMPTY) {
            newSilhouette[lastMove.getRow() / 3][lastMove.getColumn() / 3] = newBoard[upperLeftCellRow][upperLeftCellCol];
            return;

        }

        if (newBoard[upperLeftCellRow][upperLeftCellCol + 2] == newBoard[upperLeftCellRow + 1][upperLeftCellCol + 1] &&
                newBoard[upperLeftCellRow + 1][upperLeftCellCol + 1] == newBoard[upperLeftCellRow + 2][upperLeftCellCol] &&
                newBoard[upperLeftCellRow][upperLeftCellCol + 2] != EMPTY) {
            newSilhouette[lastMove.getRow() / 3][lastMove.getColumn() / 3] = newBoard[upperLeftCellRow][upperLeftCellCol + 2];
        }
    }

    public static List<Action> getApplicableActionsFromStart() {
        List<Action> actions = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                actions.add(new Move(row, col));
            }
        }

        return actions;
    }

    public static int[][] getTwoDimensionalArrayCopy(int[][] array) {
        int[][] copy = new int[array.length][];

        for (int i = 0; i < array.length; i++) {
            int[] rowData = array[i];
            int rowLength = rowData.length;
            copy[i] = new int[rowLength];
            System.arraycopy(rowData, 0, copy[i], 0, rowLength);
        }

        return copy;
    }

    public static List<Action> getApplicableActionsAfterLastMove(Move lastMove, int[][] silhouette, int[][] grid) {
        int i = lastMove.getRow() % 3;
        int j = lastMove.getColumn() % 3;
        List<Action> actions = new ArrayList<>();

        // if the small board is free
        if (silhouette[i][j] == EMPTY) {
            // loop through the small board
            for (int row = 3*i; row < 3*i + 3; row++) {
                for (int col = 3*j; col < 3*j + 3; col++) {
                    if (grid[row][col] == EMPTY) {
                        Move move = new Move(row, col);
                        actions.add(move);
                    }
                }
            }
        }
        // find all other applicable moves on the entire grid
        else {
            // loop through silhouette's empty cells and find the applicable moves on the big board
            for (int silhouetteRow = 0; silhouetteRow < 3; silhouetteRow++) {
                for (int silhouetteCol = 0; silhouetteCol < 3; silhouetteCol++) {
                    if(silhouette[silhouetteRow][silhouetteCol] == EMPTY) {
                        for (int bigBoardRow = silhouetteRow  * 3; bigBoardRow < silhouetteRow * 3 + 3; bigBoardRow++) {
                            for (int bigBoardCol = silhouetteCol * 3; bigBoardCol < silhouetteCol * 3 + 3; bigBoardCol++) {
                                if(grid[bigBoardRow][bigBoardCol] == EMPTY) {
                                    actions.add(new Move(bigBoardRow, bigBoardCol));
                                }
                            }
                        }
                    }
                }
            }
        }

        return actions;
    }
}
