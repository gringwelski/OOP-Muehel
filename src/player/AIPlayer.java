package src.player;

import src.common.Move;
import src.common.PlayerColor;
import src.game.GameLogic;
import src.gui.GUIPlayer;

public class AIPlayer implements Player {

    private final String name;
    private final PlayerColor color;
    private final GameLogic gameLogic;
    private final  GUIPlayer gui;

    protected AIPlayer(String name, PlayerColor color, GameLogic gameLogic, GUIPlayer gui) {
        this.name = name;
        this.color = color;
        this.gameLogic = gameLogic;
        this.gui = gui;
    }

    @Override
    public Move makeMove() {
        return null;
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
        return true;
    }
}
