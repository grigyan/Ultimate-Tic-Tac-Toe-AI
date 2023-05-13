package game.board;

import game.move.Action;
import game.player.PlayerEnum;
import game.move.Move;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static game.board.BoardConstants.*;

public class BigBoard implements State {
    private final int[][] grid; // grid is 9x9 array
    private final int[][] silhouette; // the high level 3x3 representation of the gird
    private final PlayerEnum player;
    private Move lastMove;

    // for creating initial state
    public BigBoard() {
        player = PlayerEnum.MAX;
        grid = new int[WIDTH][HEIGHT];
        silhouette = new int[3][3];
    }

    // for creating
    public BigBoard(PlayerEnum player, int[][] grid, int[][] silhouette, Move lastMove) {
        this.player = player;
        this.grid = grid;
        this.lastMove = lastMove;
        this.silhouette = silhouette;
    }

    public BigBoard(PlayerEnum player, int[][] grid, Move lastMove) {
        this.player = player;
        this.grid = grid;
        this.lastMove = lastMove;
        this.silhouette = getSilhouetteFromGrid(grid);
    }

    public int[][] getSilhouette() {
        return silhouette;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public int[][] getSilhouetteFromGrid(int[][] grid) {
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


    public boolean isGridFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Set<Action> getApplicableActions() {
        if (lastMove == null) {
            return getApplicableActionsFromStart();
        }

        return getApplicableActionsAfterLastMove(lastMove);
    }

    @Override
    public State getActionResult(Action action) {
        Move move = (Move) action;

        int[][] newBoard = getGridCopy();
        int[][] newSilhouette = getSilhouetteCopy();


        if (player == PlayerEnum.MAX) {
            newBoard[move.getRow()][move.getColumn()] = X;
            updateSilhouetteAfterLastMove(move, newSilhouette, newBoard);
            return new BigBoard(PlayerEnum.MIN, newBoard, newSilhouette, move);
        } else { // player == Player.MIN
            newBoard[move.getRow()][move.getColumn()] = O;
            updateSilhouetteAfterLastMove(move, newSilhouette, newBoard);
            return new BigBoard(PlayerEnum.MAX, newBoard, newSilhouette, move);
        }
    }

    public void updateSilhouetteAfterLastMove(Move lastMove, int[][] newSilhouette, int[][] newBoard) {
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

    /**
     * This method takes a last move
     * and returns set of actions in the corresponding small board
     *
     * @param lastMove
     *        Last move of the player. Values range from 0 to 8.
     * @return Set of actions
     */
    public Set<Action> getApplicableActionsAfterLastMove(Move lastMove) {
        int i = lastMove.getRow() % 3;
        int j = lastMove.getColumn() % 3;
        Set<Action> actions = new LinkedHashSet<>();

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

    public Set<Action> getApplicableActionsFromStart() {
        Set<Action> actions = new LinkedHashSet<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                actions.add(new Move(row, col));
            }
        }

        return actions;
    }

    private int[][] getGridCopy() {
        int[][] copy = new int[grid.length][];

        for (int i = 0; i < grid.length; i++) {
            int[] rowData = grid[i];
            int rowLength = rowData.length;
            copy[i] = new int[rowLength];
            System.arraycopy(rowData, 0, copy[i], 0, rowLength);
        }

        return copy;
    }

    private int[][] getSilhouetteCopy() {
        int[][] copy = new int[silhouette.length][];

        for (int i = 0; i < silhouette.length; i++) {
            int[] rowData = silhouette[i];
            int rowLength = rowData.length;
            copy[i] = new int[rowLength];
            System.arraycopy(rowData, 0, copy[i], 0, rowLength);
        }

        return copy;
    }


    public boolean isSilhouetteWon() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (silhouette[i][0] == silhouette[i][1] && silhouette[i][1] == silhouette[i][2] && silhouette[i][0] != EMPTY) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (silhouette[0][j] == silhouette[1][j] && silhouette[1][j] == silhouette[2][j] && silhouette[0][j] != EMPTY) {
                return true;
            }
        }

        // Check diagonals
        if (silhouette[0][0] == silhouette[1][1] && silhouette[1][1] == silhouette[2][2] && silhouette[0][0] != EMPTY) {
            return true;
        }

        if (silhouette[0][2] == silhouette[1][1] && silhouette[1][1] == silhouette[2][0] && silhouette[0][2] != EMPTY) {
            return true;
        }

        // If no winner is found
        return false;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getCell(int row, int col) {  // row and col are from range [0, 8]
        return grid[row][col];
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public PlayerEnum getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true; // checking if that is a reference to this
        if (that == null) return false; // checking if that exists
        if (!(that instanceof BigBoard)) return false; // checking if that and this are instances of the same class

        // two Board instances should be considered equal if they have the same height, weight, corresponding marks
        // at each index of the board array, as well as the same player

        BigBoard thatBoard = (BigBoard) that;
        if (HEIGHT != thatBoard.getHeight()) return false;
        if (WIDTH != thatBoard.getWidth()) return false;
        if (player != thatBoard.getPlayer()) return false;

        // to check that the two boards have corresponding marks, we have to loop over the board cells
        for (int row = 0; row < HEIGHT; row++)
            for (int col = 0; col < WIDTH; col++)
                if (this.getCell(row, col) != thatBoard.getCell(row, col))
                    return false;

        return true;
    }

    @Override
    public int hashCode() {
        // hashing Board instances based on height, width, as well as the hashCodes of the board array and player
        final int prime = 31;
        int result = prime * (prime + HEIGHT) + WIDTH;
        result = prime * result + Arrays.deepHashCode(grid);
        return prime * result + player.hashCode();
    }

    public boolean isSilhouetteFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (silhouette[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
