package test.gui;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.LabeledMatchers;
import src.common.Move;
import src.gui.GUIGame;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;


/**
 * To run these tests, make sure junit5, testfx-junit5, testfx-core is in your classpath
 * Download testfx-junit5 jar here: https://repo1.maven.org/maven2/org/testfx/testfx-junit5/4.0.16-alpha/testfx-junit5-4.0.16-alpha.jar
 * Download testfx-core jar here: https://repo1.maven.org/maven2/org/testfx/testfx-core/4.0.16-alpha/testfx-core-4.0.16-alpha.jar
 * Download hamcrest jar here: https://repo1.maven.org/maven2/org/hamcrest/hamcrest-all/1.3/hamcrest-all-1.3.jar
 */
@ExtendWith(ApplicationExtension.class)
class GUIPlayerTest {
    private GUIGameDummy game;
    private final FxRobot robot = new FxRobot();

    /**
     * Condition for this test to run successfully, is that the {@link GUIGame} extends the {@link Application} and that
     * the {@link Application#launch(String...)} method is packet into a try catch that ignores the
     * {@link IllegalStateException} which is thrown by {@link Application#launch(String...)}, because TestFX already
     * launches the {@link Application}!
     */
    @BeforeEach
    void setUp() throws TimeoutException {
        this.game = new GUIGameDummy();
        FxToolkit.setupApplication(() -> {
            this.game.create();
            return (Application) this.game;
        });
    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupApplication((Application) game);
        robot.release(new KeyCode[]{});
        robot.release(new MouseButton[]{});
    }


    @Test
    void testMakeManualMove_WithoutMessage() throws InterruptedException {
        AtomicReference<Move> move = null;
        Thread thread = new Thread(() -> {
            // !!!! REPLACE LATER WITH REAL PLAYER OBJECT !!!!
            move.set(game.makeManualMove(null));
        });
        thread.start();
        robot.clickOn("#point1-1", MouseButton.PRIMARY);
        robot.clickOn("#point1-2", MouseButton.PRIMARY);
        thread.join();
        Move move1 = move.get();
        Assertions.assertSame(move1.getStartPoint().getX(), 1);
        Assertions.assertSame(move1.getStartPoint().getY(), 1);
        Assertions.assertSame(move1.getEndPoint().getX(), 1);
        Assertions.assertSame(move1.getEndPoint().getY(), 2);
    }

    @Test
    void testMakeManuelMove_Message() {
        String message = "This is a test message";
        game.makeManualMove(null, message);
        FxAssert.verifyThat("#alert", LabeledMatchers.hasText(message));
    }

    @Test
    void testSetStone_WithoutMessage() throws InterruptedException {
        AtomicReference<Move> move = null;
        Thread thread = new Thread(() -> {
            // !!!! REPLACE LATER WITH REAL PLAYER OBJECT !!!!
            move.set(game.setStone(null));
        });
        thread.start();
        robot.clickOn("#point1-1", MouseButton.PRIMARY);
        thread.join();
        Move move1 = move.get();
        Assertions.assertSame(move1.getStartPoint(), null);
        Assertions.assertSame(move1.getEndPoint().getX(), 1);
        Assertions.assertSame(move1.getEndPoint().getY(), 1);
    }

    @Test
    void testSetStone_Message() {
        String message = "This is a test message";
        game.setStone(null, message);
        FxAssert.verifyThat("#alert", LabeledMatchers.hasText(message));
    }
}
