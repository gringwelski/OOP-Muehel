package test.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.common.Move;
import src.common.PlayerColor;
import src.common.StoneAction;
import src.game.GameLogic;
import src.player.Player;
import test.common.MoveDummy;
import test.common.PointDummy;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    GameLogicDummy gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogicDummy();
    }

    @Test
    void testGetState_noStones() {
        GameLogic gameLogic = new GameLogicDummy();
        PlayerColor[][] state = gameLogic.getState();

        for (PlayerColor[] playerColor : state) {
            for (PlayerColor playerColor1 : playerColor) {
                assertEquals(PlayerColor.NONE, playerColor1);
            }
        }
    }


    @Test
    void testIsValidMove_validMove_Null() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        Move move = new MoveDummy(null, new PointDummy(1, 1));

        gameLogic = new GameLogicDummy();
        gameLogic.setField(field);
        boolean valid = gameLogic.isValidMove(move);

        assertTrue(valid);
    }

    @Test
    void testIsValidMove_invalidMove_Null() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        Move move = new MoveDummy(new PointDummy(1, 1), null);

        gameLogic = new GameLogicDummy();
        boolean valid = gameLogic.isValidMove(move);

        assertFalse(valid);
    }

    @Test
    void testIsValidMove_invalidMove_NullNull() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        Move move = new MoveDummy(null, null);

        gameLogic = new GameLogicDummy();
        boolean valid = gameLogic.isValidMove(move);

        assertFalse(valid);
    }

    @Test
    void testIsValidMove_invalidMove_NullStone() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        field[0][0] = PlayerColor.BLACK;

        Move move = new MoveDummy(null, new PointDummy(0, 0));

        gameLogic = new GameLogicDummy();
        boolean valid = gameLogic.isValidMove(move);

        assertFalse(valid);
    }

    @Test
    void testIsValidMove_validMove_notNull() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        field[1][1] = PlayerColor.BLACK;

        Move move = new MoveDummy(new PointDummy(1, 1), new PointDummy(2, 1));

        gameLogic = new GameLogicDummy();
        boolean valid = gameLogic.isValidMove(move);

        assertTrue(valid);
    }

    @Test
    void testIsValidMove_invalidMove_notNull() {
        PlayerColor[][] field = new PlayerColor[7][6];
        for (PlayerColor[] row : field) Arrays.fill(row, PlayerColor.NONE);

        field[3][2] = PlayerColor.BLACK;
        field[3][1] = PlayerColor.WHITE;

        Move move = new MoveDummy(new PointDummy(3, 2), new PointDummy(3, 3));

        gameLogic = new GameLogicDummy();
        boolean valid = gameLogic.isValidMove(move);

        assertFalse(valid);
    }


    @Test
    void testRunGame_playersCreated() {
        gameLogic = new GameLogicDummy();
        gameLogic.runGame();

        Player[] players = gameLogic.getPlayer();

        assertNotNull(players[0]);
        assertNotNull(players[1]);
    }

    @Test
    void testGetPhase_SET() {
        gameLogic = new GameLogicDummy();
        StoneAction phase = gameLogic.getPhase();

        gameLogic.setAmountStone(8);

        assertEquals(StoneAction.SET, phase);
    }

    @Test
    void testGetPhase_PUSH() {
        gameLogic = new GameLogicDummy();
        StoneAction phase = gameLogic.getPhase();

        gameLogic.setAmountStone(8);

        assertEquals(StoneAction.PUSH, phase);
    }

    @Test
    void testGetPhase_JUMP() {
        gameLogic = new GameLogicDummy();
        StoneAction phase = gameLogic.getPhase();

        gameLogic.setAmountStone(3);

        assertEquals(StoneAction.JUMP, phase);
    }
}