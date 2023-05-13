package game.move;


public class Move implements Action {
    private final int row;  // in range [0, 8]
    private final int col;  // in range [0, 8]

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return col;
    }
}
