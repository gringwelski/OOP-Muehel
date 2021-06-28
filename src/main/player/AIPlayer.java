package player;

import common.Move;
import common.PlayerColor;
import common.Point;
import game.GameLogic;
import gui.GUIPlayer;

public class AIPlayer implements Player {

    private final String name;
    private final PlayerColor color;
    private final GameLogic gameLogic;
    private final GUIPlayer gui;

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
    public Point selectThrowStone() {
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
