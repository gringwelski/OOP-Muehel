package test.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxService;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.TableViewMatchers;
import src.common.PlayerColor;
import src.gui.GUIGame;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * To run these tests, make sure junit5, testfx-junit5, testfx-core is in your classpath
 * Download testfx-junit5 jar here: https://repo1.maven.org/maven2/org/testfx/testfx-junit5/4.0.16-alpha/testfx-junit5-4.0.16-alpha.jar
 * Download testfx-core jar here: https://repo1.maven.org/maven2/org/testfx/testfx-core/4.0.16-alpha/testfx-core-4.0.16-alpha.jar
 * Download hamcrest jar here: https://repo1.maven.org/maven2/org/hamcrest/hamcrest-all/1.3/hamcrest-all-1.3.jar
 */
@ExtendWith(ApplicationExtension.class)
class GUIGameTest {

    private GUIGame game;
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
        FxToolkit.cleanupStages();
        FxToolkit.cleanupApplication((Application) game);
        robot.release(new KeyCode[]{});
        robot.release(new MouseButton[]{});
    }

    @Test
    void testSynchroniseGame() {
        PlayerColor[][] matrix = new PlayerColor[7][6];
        for (PlayerColor[] row : matrix)
            Arrays.fill(row, PlayerColor.NONE);
        matrix[0][0] = PlayerColor.BLACK;
        matrix[3][2] = PlayerColor.WHITE;
        matrix[6][2] = PlayerColor.BLACK;

        game.synchronizeGame(matrix);

        FxAssert.verifyThat("#point0-0", node -> node.getStyleClass().contains("black"));
        FxAssert.verifyThat("#point3-2", node -> node.getStyleClass().contains("white"));
        FxAssert.verifyThat("#point6-2", node -> node.getStyleClass().contains("black"));
    }

    @Test
    void testWinDialog() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> game.win(null));
        Platform.runLater(latch::countDown);
        latch.await();
        Assertions.assertEquals(2, FxService.serviceContext().getWindowFinder().listWindows().size());
    }

    @Test
    void testCreate() throws ExecutionException, InterruptedException {
        Stage primaryStage = FxToolkit.toolkitContext().getPrimaryStageFuture().get();
        Assertions.assertTrue(primaryStage.isShowing());
    }

    @Test
    void testShowHighscores() {
        game.showHighscores(Arrays.asList("Foo", "Bar"), Arrays.asList("300", "100"), Arrays.asList("150", "250"));
        FxAssert.verifyThat("#highscores", TableViewMatchers.containsRow("Foo", "300", "150"));
        FxAssert.verifyThat("#highscores", TableViewMatchers.containsRow("Bar", "100", "250"));
    }
}
