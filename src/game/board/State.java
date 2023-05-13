package game.board;

import game.move.Action;
import game.player.Player;
import game.move.Move;

import java.util.List;

public interface State {
    Player getPlayer();

    Player getOpponent();
    List<Action> getApplicableActions();
    State getActionResult(Action action);

    boolean equals(Object that);
    int hashCode();
    Move getLastMove();
}
