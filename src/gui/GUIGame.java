package gui;

import player.Player;
import java.util.List;

public interface GUIGame {

    /**
     * synchronizes the GUI to the version of the current Game
     *
     * @param field which shouldbe updated
     */
    void synchronizeGame(String[][] field);

    /**
     * says the UI that the the game is won by one of the two players
     *
     * @param player who has won
     */
    void win(Player player);

    /**
     * creates the GUI and gives parameters to show some statistics.
     *
     * @param playerNames a List of the Names of the Player who played the game
     * @param amountGames a List of the amount iof games a specific player played
     * @param wonGames a List of the amount of games aspecific player won
     */
    void create(List<String> playerNames, List<Integer> amountGames, List<Integer> wonGames);

}