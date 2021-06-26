package game;
import common.Move;
import common.PlayerColor;
import common.Point;
import common.StoneAction;

public interface
GameLogic{
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
     * @param namePlayer1 name of the first Player
     * @param player1IsAi true if player1 is an AI
     * @param namePlayer2 name of the second Player
     * @param player2IsAi true if player2 is an AI
     * start the Game with the current settings
     */
    void runGame(String namePlayer1, boolean player1IsAi, String namePlayer2, boolean player2IsAi);

    /**
     *
     * @return StoneAction an enum type which represents the phase of the main.game. The phases are SET, PUSH and JUMP.
     */
    StoneAction getPhase();

    /**
     *
     * @param point the point of the Stone you want to throw away
     * @return return true if you can throw the stone
     */
    boolean isThrowStoneValid(Point point);
}
