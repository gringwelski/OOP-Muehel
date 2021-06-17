package src.gui;

import src.common.Move;

public interface GUIPlayer {

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @return Move which represents the action
     */
    Move makeManualMove();

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param msg as additional parameter. It would be shown as a dialog if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move makeManualMove(String msg);

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @return Move which represents the action
     */
    Move setStone();

    /**
     * The GUI waits for the user to click and perform an Action.
     *
     * @param msg as additional parameter. It would be shown as a dialog if there was for example an illegal Move.
     * @return Move which represents the action
     */
    Move setStone(String msg);


}