package game.player;

import ai.heuristic.RandomAI;
import ai.heuristic.StateEvaluationAi;

import java.util.Objects;

public class Player {
    PlayerEnum player;
    StateEvaluationAi stateEvaluationAi;
    int depthLimit;

    public Player (PlayerEnum player, StateEvaluationAi stateEvaluationAi, int depthLimit) {
        this.player = player;
        this.stateEvaluationAi = stateEvaluationAi;
        this.depthLimit = depthLimit;
    }

    public Player (PlayerEnum player, StateEvaluationAi stateEvaluationAi) {
        this.player = player;
        this.stateEvaluationAi = stateEvaluationAi;
        depthLimit = 1;
    }

    public StateEvaluationAi getStateEvaluationAi() {
        return stateEvaluationAi;
    }

    public void setStateEvaluationAi(StateEvaluationAi stateEvaluationAi) {
        this.stateEvaluationAi = stateEvaluationAi;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public PlayerEnum getPlayerEnum() {
        return player;
    }

    public void setPlayer(PlayerEnum player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player1 = (Player) o;

        if (depthLimit != player1.depthLimit) return false;
        if (player != player1.player) return false;
        return Objects.equals(stateEvaluationAi, player1.stateEvaluationAi);
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (stateEvaluationAi != null ? stateEvaluationAi.hashCode() : 0);
        result = 31 * result + depthLimit;
        return result;
    }
}
