package player;

import common.Move;
import common.PlayerColor;
import common.StoneAction;
import game.GameLogic;
import gui.GUIPlayer;

public class RealPlayer implements Player {

    private final String name;
    private final PlayerColor color;
    private final GameLogic gameLogic;
    private final GUIPlayer gui;

    protected RealPlayer(String name, PlayerColor color, GameLogic gameLogic, GUIPlayer gui) {
        this.name = name;
        this.color = color;
        this.gameLogic = gameLogic;
        this.gui = gui;
    }

    private Move internalMove(String message) {
        StoneAction currentAction = gameLogic.getPhase();
        if (currentAction == StoneAction.SET && message == null)
            message = "";
        if (currentAction == StoneAction.PUSH && message == null)
            message = "Bitte bewege einen Stein";
        if (currentAction == StoneAction.JUMP && message == null)
            message = "Bitte springe mit einem Stein";
        Move move = gui.setStone(this, message);
        if (!gameLogic.isValidMove(move))
            return internalMove("Der stein kann dort nicht platziert werden!");
        return move;
    }

    @Override
    public Move makeMove() {
        return internalMove(null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PlayerColor getColor() {
        return this.color;
    }

    @Override
    public boolean isAi() {
        return false;
    }
}
