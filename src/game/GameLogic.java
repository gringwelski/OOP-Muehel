package src.game;
import src.common.Move;
import src.common.Point;

public interface GameLogic{
    /**
     * @return a 2 dimensional array in shape of the playfield
     */
    String[][] getState();

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
     * @return 1 for Setting Stones, 2 for push Stones, 3 for jump with stones
     */
    int getPhase();
}