package game.print;

import game.move.Action;
import game.board.State;

import java.util.Map;

public abstract class Printing {
    public void printStrategy(Map<State, Action> strategy) {
        for (Map.Entry<State, Action> e : strategy.entrySet()) {
            System.out.println("In below state last move was " + e.getKey().getLastMove().getRow() + " " + e.getKey().getLastMove().getColumn());
            print(e.getKey());
            print(e.getValue());
            System.out.println();
            System.out.println();
        }
    }
    public abstract void print(Action action);
    public abstract void print(State state);
}
