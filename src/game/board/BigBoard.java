package game.board;

import game.board.utils.BigBoardUtils;
import game.move.Action;
import game.player.Player;
import game.player.PlayerEnum;
import game.move.Move;

import java.util.Arrays;
import java.util.List;

import static game.board.BoardConstants.*;
import static game.board.utils.BigBoardUtils.*;

public class BigBoard implements State {
    private final int[][] grid;         // grid is 9x9 array
    private final int[][] silhouette;   // the high level 3x3 representation of the gird
    private final Player player;        // player that should move next (or first)
    private final Player opponent;      // player that made the previous move (or will move second)
    private Move lastMove;              // move done in the previous position

    // for creating initial state
    public BigBoard (Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        grid = new int[WIDTH][HEIGHT];
        silhouette = new int[3][3];
    }

    // for creating
    public BigBoard (Player player, Player opponent, int[][] grid, int[][] silhouette, Move lastMove) {
        this.player = player;
        this.opponent = opponent;
        this.grid = grid;
        this.lastMove = lastMove;
        this.silhouette = silhouette;
    }

    public BigBoard (Player player, Player opponent, int[][] grid, Move lastMove) {
        this.player = player;
        this.opponent = opponent;
        this.grid = grid;
        this.lastMove = lastMove;
        this.silhouette = getSilhouetteFromGrid(grid);
    }

    @Override
    public List<Action> getApplicableActions() {
        if (lastMove == null) {
            return getApplicableActionsFromStart();
        }

        return getApplicableActionsAfterLastMove(lastMove);
    }

    @Override
    public State getActionResult(Action action) {
        Move move = (Move) action;

        int[][] newBoard = getTwoDimensionalArrayCopy(grid);
        int[][] newSilhouette = getTwoDimensionalArrayCopy(silhouette);

        if (player.getPlayerEnum() == PlayerEnum.MAX) {
            newBoard[move.getRow()][move.getColumn()] = X;
        } else { // player == Player.MIN
            newBoard[move.getRow()][move.getColumn()] = O;
        }

        updateSilhouetteAfterLastMove(move, newSilhouette, newBoard);
        return new BigBoard(opponent, player, newBoard, newSilhouette, move);
    }

    @Override
    public Player getPlayer() {
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


    public List<Action> getApplicableActionsAfterLastMove(Move lastMove) {
        return BigBoardUtils.getApplicableActionsAfterLastMove(lastMove, silhouette, grid);
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

    public Player getOpponent() {
        return opponent;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] getSilhouette() {
        return silhouette;
    }

    public Move getLastMove() {
        return lastMove;
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
}
