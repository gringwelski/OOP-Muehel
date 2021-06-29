package player;

import common.Move;
import common.PlayerColor;
import common.Point;
import common.StoneAction;
import game.GameLogic;
import gui.GUIPlayer;

public class RealPlayer implements Player {

    private final String name;
    private final PlayerColor color;
    private final GameLogic gameLogic;
    private final GUIPlayer gui;
    private int madeMoves;

    protected RealPlayer(String name, PlayerColor color, GameLogic gameLogic, GUIPlayer gui) {
        this.name = name;
        this.color = color;
        this.gameLogic = gameLogic;
        this.gui = gui;
        this.madeMoves = 0;
    }

    private Move internalMove(String message) {
        StoneAction currentAction = gameLogic.getPhase();
        if (currentAction == StoneAction.SET && message == null)
            message = "";
        if (currentAction == StoneAction.PUSH && message == null)
            message = "Bitte bewege einen Stein";
        if (currentAction == StoneAction.JUMP && message == null)
            message = "Bitte springe mit einem Stein";
        Move move = null;
        if (currentAction == StoneAction.PUSH || currentAction == StoneAction.JUMP)
            move = gui.makeManualMove(this, message);
        else
            move = gui.setStone(this, message);
        boolean isValidMove = gameLogic.isValidMove(move);
        System.out.println(move.toString());
        System.out.println("Is valid move? " + isValidMove);
        if (!isValidMove)
            return internalMove("Der stein kann dort nicht platziert werden!");

        madeMoves ++;
        return move;
    }

    @Override
    public Move makeMove() {
        return internalMove(null);
    }

    @Override
    public Point selectThrowStone() {
        Point point;
        do {
            point = gui.takeStone(this, "Bitte entferne einen Stein des Gegners").getEndPoint();
        } while (!gameLogic.isThrowStoneValid(point, this));
        return point;
    }

    @Override
    public boolean isDoneSetting() {
        return madeMoves >= 9;
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
