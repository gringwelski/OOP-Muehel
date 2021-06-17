package src.game;
import src.common.Move;
import src.common.PlayerColor;
import src.common.Point;
import src.common.StoneAction;

public interface GameLogic{
    /**
     * @return a 2 dimensional array in shape of the playfield
     */
    PlayerColor[][] getState();

    /**
     * @param move The move which the player want to execute
     * @return returns whether the action is valid or not
     */
    boolean isValidMove(Move move);

    /**
     * start the Game with the current settings
     */
    void runGame();

    /**
     *
     * @return StoneAction an enum type which represents the phase of the game. The phases are SET, PUSH and JUMP.
     */
    StoneAction getPhase();
}