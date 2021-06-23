package player;

import common.Move;
import common.PlayerColor;
import game.GameLogic;
import gui.GUIPlayer;

public interface Player {
  /**
   * Creates a main.player object with ai or as manuel main.player
   *
   * @param ai boolean if the main.player should be an ai or not
   * @param name name of the main.player
   * @param color color of the main.player
   * @param game running instance of {@link GameLogic}
   * @return Instance of {@link Player}
   */
  static Player create(boolean ai, String name, PlayerColor color, GameLogic game, GUIPlayer gui) {
    return ai ? new AIPlayer(name, color, game, gui) : new RealPlayer(name, color, game, gui);
  }

  /**
   * Get name of current main.player
   *
   * @return {@link String}, name of current main.player
   */
  String getName();

  /**
   * Gets the color of the current main.player
   *
   * @return main.player color
   */
  PlayerColor getColor();

  /**
   * Get if the main.player is an ai or not
   *
   * @return boolean, true if ai, false if manuel main.player
   */
  boolean isAi();

  /**
   * Makes a move based on the current main.game state. If an ai, a "random" move will be made, otherwise it will wait for
   * the main.gui to make a move. It will then validate the move by utilizing the main.game#isValidMove. If the move is invalid
   * and it is an ai main.player, it will just continue to try moving, if the main.player is a manuel main.player it will request the
   * ui to retry the move. If that's done, it will return the move that was valid.
   *
   * @return the validated {@link Move}
   */
  Move makeMove();
}
