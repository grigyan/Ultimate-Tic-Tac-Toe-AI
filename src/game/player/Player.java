package game.player;

import game.heuristic.StateEvaluationAi;

public class Player {
    PlayerEnum player;
    StateEvaluationAi stateEvaluationAi;
    int depthLimit;

    public Player(PlayerEnum player, StateEvaluationAi stateEvaluationAi, int depthLimit) {
        this.player = player;
        this.stateEvaluationAi = stateEvaluationAi;
        this.depthLimit = depthLimit;
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

    public PlayerEnum getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEnum player) {
        this.player = player;
    }

}
