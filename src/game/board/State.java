package game.board;

import game.move.Action;
import game.player.PlayerEnum;
import game.move.Move;

import java.util.Set;

public interface State {
    PlayerEnum getPlayer();
    Set<Action> getApplicableActions();
    State getActionResult(Action action);

    boolean equals(Object that);
    int hashCode();
    Move getLastMove();
}
