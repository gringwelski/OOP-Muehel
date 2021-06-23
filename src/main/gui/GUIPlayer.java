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
     * @param msg as additional parameter. It would be shown as a dialog if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move makeManualMove(Player player, String msg);

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param player the main.player who has to perform an action
     * @return Move which represents the action
     */
    Move setStone(Player player);

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param player the main.player who has to perform an action
     * @param msg as additional parameter. It would be shown as a dialog if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move setStone(Player player, String msg);


}
