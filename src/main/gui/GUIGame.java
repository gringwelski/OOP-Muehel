package gui;

import common.PlayerColor;
import game.GameLogic;
import player.Player;
import java.util.List;

public interface GUIGame {

    /**
     * synchronizes the GUI to the version of the current Game
     *
     * @param field which shouldbe updated
     */
    void synchronizeGame(PlayerColor[][] field);

    /**
     * says the UI that the the main.game is won by one of the two players
     *
     * @param player who has won
     */
    void win(Player player);

    /**
     * creates the GUI and gives parameters to show some statistics.
     *
     */
    void create(GameLogic gameLogic);

    /**
     * shows the highscores
     *
     * @param playerNames a List of the Names of the Player who played the main.game
     * @param amountGames a List of the amount iof games a specific main.player played
     * @param wonGames a List of the amount of games aspecific main.player won
     */
    void showHighscores(List<String> playerNames, List<String> amountGames, List<String> wonGames);

}
