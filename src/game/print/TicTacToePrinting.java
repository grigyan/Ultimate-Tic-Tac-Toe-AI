package game.print;

import game.move.Action;
import game.board.State;
import game.move.Move;
import game.board.BigBoard;

import java.util.Arrays;

public class TicTacToePrinting extends Printing {
    @Override
    public void print(Action action) {
        Move move = (Move) action;
        System.out.println("mark (" + move.getRow() + ", " + move.getColumn() + ")");
    }

    @Override
    public void print(State state) {
        BigBoard board = (BigBoard) state;
        System.out.println("\\/");

        System.out.println("  |-0---1---2-||-3---4---5-||-6---7---8-|");
        System.out.print("0 "+printRow(board.getGrid()[0]));
        System.out.print("1 "+printRow(board.getGrid()[1]));
        System.out.print("2 "+printRow(board.getGrid()[2]));
        System.out.println("  |-----------||-----------||-----------|");
        System.out.print("3 "+printRow(board.getGrid()[3]));
        System.out.print("4 "+printRow(board.getGrid()[4]));
        System.out.print("5 "+printRow(board.getGrid()[5]));
        System.out.println("  |-----------||-----------||-----------|");
        System.out.print("6 "+printRow(board.getGrid()[6]));
        System.out.print("7 "+printRow(board.getGrid()[7]));
        System.out.print("8 "+printRow(board.getGrid()[8]));
        System.out.println("  |-----------||-----------||-----------|");
    }

    private String printRow(int[] gridRow) {
        var row = Arrays.stream(gridRow).sequential()
                        .mapToObj(num -> num == 1 ? "X" : num == 2 ? "O" : "_")
                                .toArray();
        return String.format("| %s   %s   %s || %s   %s   %s || %s   %s   %s |%n",
                row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
    }
}
