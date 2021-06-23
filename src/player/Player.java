package src.player;

import src.common.Move;
import src.common.PlayerColor;
import src.game.GameLogic;
import src.gui.GUIPlayer;

public interface Player {
  /**
   * Creates a player object with ai or as manuel player
   *
   * @param ai boolean if the player should be an ai or not
   * @param name name of the player
   * @param color color of the player
   * @param game running instance of {@link GameLogic}
   * @return Instance of {@link Player}
   */
  static Player create(boolean ai, String name, PlayerColor color, GameLogic game, GUIPlayer gui) {
    return ai ? new AIPlayer(name, color, game, gui) : new RealPlayer(name, color, game, gui);
  }

  /**
   * Get name of current player
   *
   * @return {@link String}, name of current player
   */
  String getName();

  /**
   * Gets the color of the current player
   *
   * @return player color
   */
  PlayerColor getColor();

  /**
   * Get if the player is an ai or not
   *
   * @return boolean, true if ai, false if manuel player
   */
  boolean isAi();

  /**
   * Makes a move based on the current game state. If an ai, a "random" move will be made, otherwise it will wait for
   * the gui to make a move. It will then validate the move by utilizing the game#isValidMove. If the move is invalid
   * and it is an ai player, it will just continue to try moving, if the player is a manuel player it will request the
   * ui to retry the move. If that's done, it will return the move that was valid.
   *
   * @return the validated {@link Move}
   */
  Move makeMove();
}
