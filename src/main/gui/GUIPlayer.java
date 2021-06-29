package gui;

import common.Move;
import player.Player;

public interface GUIPlayer {

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param player the main.player who has to perform an action
     * @return Move which represents the action
     */
    Move makeManualMove(Player player);

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param player the main.player who has to perform an action
     * @param msg as additional parameter. It would be shown if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move makeManualMove(Player player, String msg);

    /**
     * The GUI waits for the user to click and perform an Action.
     * Difference to setStone is the disabling of specific stones
     *
     * @param player the player who has to perform an action
     * @return Move which represents the action
     */
    Move setStone(Player player);

    /**
     * The GUI waits for the user to click and perform an Action.
     * Difference to setStone is the disabling of specific stones
     *
     * @param player the main.player who has to perform an action
     * @param msg as additional parameter. It would be shown if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move setStone(Player player, String msg);

    /**
     * The GUI waits for the user to click and perform an Action (one click).
     * Difference to setStone is the disabling of spesific stones
     *
     * @param player the player who has to perform an action
     * @return Move which represents the action
     */
    Move takeStone(Player player);

    /**
     * The GUI waits for the user to click and perform an Action.
     * Difference to setStone is the disabling of spesific stones
     *
     * @param player the player who has to perform an action
     * @param msg as additional parameter. It would be shown if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move takeStone(Player player, String msg);


}
