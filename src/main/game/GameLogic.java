package game;
import common.Move;
import common.PlayerColor;
import common.Point;
import common.StoneAction;

public interface GameLogic{
    /**
     * @return a 2 dimensional array in shape of the playfield
     */
    PlayerColor[][] getState();

    /**
     * @param move The move which the main.player want to execute
     * @return returns whether the action is valid or not
     */
    boolean isValidMove(Move move);

    /**
     * start the Game with the current settings
     */
    void runGame();

    /**
     *
     * @return StoneAction an enum type which represents the phase of the main.game. The phases are SET, PUSH and JUMP.
     */
    StoneAction getPhase();
}
