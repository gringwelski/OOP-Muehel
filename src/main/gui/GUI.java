package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import common.*;
import game.GameLogic;
import javafx.util.Pair;
import player.Player;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class GUI extends Application implements GUIGame, GUIPlayer {
    private static GridPane grid;
    private static BorderPane highscorePane;
    private static Label message;
    private static Label alert;
    private static GameLogic gameLogic;
    private static Move move;
    private static boolean firstClick;
    private static boolean singleClick;
    private static final Object synchronize = new Object();
    private static ConcurrentHashMap<Point, Point> mapping;
    private static String player1Name = "Spieler 1";
    private static String player2Name = "Spieler 2";
    private static boolean player1AI = false;
    private static boolean player2AI = true;



    @Override
    public void start(Stage stage) {
        mapping = new ConcurrentHashMap<Point, Point>();
        createMapping();

        message = new Label();
        alert = new Label();

        grid = createGrid();
        fill(grid);

        alert.setId("alert");
        message.setId("message");

        highscorePane = createHighscorePane();
        highscorePane.setVisible(false);

        MenuBar menu = createMenu();

        message.setText("Druecke auf Spiel und dann auf Start, um zu spielen.");
        message.setPadding(new Insets(GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING));
        alert.setText("");
        alert.setPadding(new Insets(0, GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menu, alert, message);

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setTop(vbox);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Muehle");
        stage.show();

        synchronized (synchronize) {
            synchronize.notifyAll();
        }

    }

    private void createMapping() {
        // field (Game)   ||   grid (GUI)
        mapping.put(new FieldPoint(0, 0), new FieldPoint(0, 0));
        mapping.put(new FieldPoint(0, 1), new FieldPoint(0,3));
        mapping.put(new FieldPoint(0, 2), new FieldPoint(0, 6));
        mapping.put(new FieldPoint(1, 0), new FieldPoint(1, 1));
        mapping.put(new FieldPoint(1, 1), new FieldPoint(1, 3));
        mapping.put(new FieldPoint(1, 2), new FieldPoint(1, 5));
        mapping.put(new FieldPoint(2, 0), new FieldPoint(2, 2));
        mapping.put(new FieldPoint(2, 1), new FieldPoint(2, 3));
        mapping.put(new FieldPoint(2, 2), new FieldPoint(2, 4));
        mapping.put(new FieldPoint(3, 0), new FieldPoint(3, 0));
        mapping.put(new FieldPoint(3, 1), new FieldPoint(3, 1));
        mapping.put(new FieldPoint(3, 2), new FieldPoint(3, 2));
        mapping.put(new FieldPoint(3, 3), new FieldPoint(3, 4));
        mapping.put(new FieldPoint(3, 4), new FieldPoint(3, 5));
        mapping.put(new FieldPoint(3, 5), new FieldPoint(3, 6));
        mapping.put(new FieldPoint(4, 0), new FieldPoint(4, 2));
        mapping.put(new FieldPoint(4, 1), new FieldPoint(4, 3));
        mapping.put(new FieldPoint(4, 2), new FieldPoint(4, 4));
        mapping.put(new FieldPoint(5, 0), new FieldPoint(5, 1));
        mapping.put(new FieldPoint(5, 1), new FieldPoint(5, 3));
        mapping.put(new FieldPoint(5, 2), new FieldPoint(5, 5));
        mapping.put(new FieldPoint(6, 0), new FieldPoint(6, 0));
        mapping.put(new FieldPoint(6, 1), new FieldPoint(6, 3));
        mapping.put(new FieldPoint(6, 2), new FieldPoint(6, 6));
    }

    private Point getKeyOfMapping(FieldPoint point) {
        for (Map.Entry<Point, Point> entry : mapping.entrySet()) {
            if (entry.getValue().equals(point)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private BorderPane createHighscorePane() {
        highscorePane = new BorderPane();

        Label label = new Label();
        label.setText("Highscores");
        label.setPadding(new Insets(GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");

        Button restartButton = new Button();
        restartButton.setText("Neues Spiel");

        Button exitButton = new Button();
        exitButton.setText("Beenden");

        HBox hBox = new HBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        region2.setMinWidth(20);
        HBox.setHgrow(region2, Priority.SOMETIMES);

        hBox.getChildren().addAll(region1, restartButton, region2, exitButton);
        hBox.setPadding(new Insets(GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING));

        highscorePane.setTop(label);
        highscorePane.setBottom(hBox);

        highscorePane.setStyle(GUIValues.HIGHSCORE_BACKGROUND);

        return highscorePane;
    }

    @Override
    public void synchronizeGame(PlayerColor[][] field) {
        highscorePane.setVisible(false);

        for(int x = 0; x < 7; x++) {
            for(int y = 0; y < 6; y++) {
                Point point = mapping.get(new FieldPoint(x, y));

                Node node = null;
                try {
                    node = getNodeByRowColumn(point.getX(), point.getY());
                } catch (NullPointerException ignored) {}

                if (node != null) {
                    Label label = (Label) node;
                    if (field[x][y] == PlayerColor.BLACK) {
                        Platform.runLater(() -> {
                            label.setStyle("-fx-background-image:url('/images/blackstone.png'); -fx-background-size: cover;");
                            label.getStyleClass().clear();
                            label.getStyleClass().add("black");
                        });
                    } else if (field[x][y] == PlayerColor.WHITE) {
                        Platform.runLater(() -> {
                            label.setStyle("-fx-background-image:url('/images/whitestone.png'); -fx-background-size: cover;");
                            label.getStyleClass().clear();
                            label.getStyleClass().add("white");
                        });
                    } else if (field[x][y] == PlayerColor.NONE) {
                        Platform.runLater(() -> {
                            label.setStyle("-fx-background-image: none;" + GUIValues.INVISIBLE_BACKGROUND);
                            label.getStyleClass().clear();
                        });
                    }
                }
            }
        }
    }

    private Node getNodeByRowColumn(int row, int column) {
        ObservableList<Node> children = grid.getChildren();

        for (Node node : children) {
            if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column && node.getClass() == Label.class) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void win(Player player) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(player.getName() + " hat gewonnen.");
        alert.setContentText("Neues Spiel starten?");

        ButtonType buttonTypeNewGame = new ButtonType("Neues Spiel");
        ButtonType buttonTypeExit = new ButtonType("Beenden");

        alert.getButtonTypes().setAll(buttonTypeNewGame, buttonTypeExit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonTypeNewGame) {
                gameLogic.runGame(player1Name, player1AI, player2Name, player2AI);
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void create(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        new Thread(() -> {
            try {
                launch();
            } catch (IllegalStateException ignored) {}
        }).start();
        synchronized (synchronize) {
            try {
                synchronize.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showHighscores(List<String> playerNames, List<String> amountGames, List<String> wonGames) {
        TableView<List<String>> tableView = new TableView<>();
        tableView.setId("highscores");
        highscorePane.setVisible(true);

        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<String> third = new ArrayList<>();
        List<String> fourth = new ArrayList<>();
        List<String> fifth = new ArrayList<>();

        convertHighscoreLists(playerNames, amountGames, wonGames, first, second, third, fourth, fifth);

        TableColumn<List<String>, String> playerColumn = new TableColumn("Spieler");
        playerColumn.setMinWidth(GUIValues.COLUMN_MIN_WIDTH);
        playerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        TableColumn<List<String>, String> playedGamesColumn = new TableColumn("gespielt");
        playedGamesColumn.setMinWidth(GUIValues.COLUMN_MIN_WIDTH);
        playedGamesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        TableColumn<List<String>, String> wonGamesColumn = new TableColumn("gewonnen");
        wonGamesColumn.setMinWidth(GUIValues.COLUMN_MIN_WIDTH);
        wonGamesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        tableView.getColumns().addAll(playerColumn, playedGamesColumn, wonGamesColumn);

        tableView.getItems().addAll(first);
        tableView.getItems().addAll(second);
        tableView.getItems().addAll(third);
        tableView.getItems().addAll(fourth);
        tableView.getItems().addAll(fifth);

        highscorePane.setCenter(tableView);
        Platform.runLater(() -> {
            grid.add(highscorePane, 0,0, 7, 7);
        });

    }

    @Override
    public Move makeManualMove(Player player) {
        return makeManualMove(player, "");
    }

    @Override
    public Move setStone(Player player) {
        return setStone(player, "");
    }

    @Override
    public Move makeManualMove(Player player, String msg) {
        firstClick = true;
        singleClick = false;

        waitForAction(player, msg);
        return move;
    }

    @Override
    public Move setStone(Player player, String msg) {
        singleClick = true;

        waitForAction(player, msg);
        return move;
    }

    private void waitForAction(Player player, String msg) {
        Platform.runLater(() -> {
            message.setText(msg);
            alert.setText(player.getName() + " ist am Zug. (" + player.getColor().getColorName() + ")");
        });

        synchronized (synchronize) {
            try {
                synchronize.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        RowConstraints rowConstraints = new RowConstraints();
        ColumnConstraints columnConstraints = new ColumnConstraints();

        for (int r = 0; r < 7; r++) {
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setFillHeight(false);
            rowConstraints.setPercentHeight(100/7);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int c = 0; c < 7; c++) {
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setFillWidth(false);
            columnConstraints.setPercentWidth(100/7);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        Image image = new Image(getClass().getResourceAsStream("/images/board.png"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(Double.MAX_VALUE);
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false))));

        gridPane.prefWidthProperty().bind(imageView.fitWidthProperty());
//        gridPane.prefHeightProperty().bind(imageView.fitHeightProperty());

        return gridPane;
    }

    private void fill(GridPane grid) {
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setMinSize(70, 70);
                label.setFont(new Font(18));

                try {
                    Point point = getKeyOfMapping(new FieldPoint(r, c));
                    label.setId("point" + point.getX() + "-" + point.getY());
                } catch (NullPointerException e) {
                    label.setId("gridpoint");
                    label.setDisable(true);
                }

                Point point = getKeyOfMapping(new FieldPoint(r, c));

                label.setOnMouseClicked(event -> {
                    synchronized (synchronize) {
                        if (singleClick) {
                            move = new StoneMove(null, point);
                            synchronize.notifyAll();
                        } else {
                            if (firstClick) {
                                move = new StoneMove(point, new FieldPoint(-1, -1));
                                firstClick = false;
                            } else {
                                move = new StoneMove(move.getStartPoint(), point);
                                synchronize.notifyAll();
                            }
                        }
                    }
                });

                grid.add(label, c, r);
                label.setStyle(GUIValues.INVISIBLE_BACKGROUND);
            }
        }

    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();

        Menu menuGame = new Menu("Spiel");
        MenuItem menuItemNewGame = new MenuItem("Start");
        MenuItem menuItemExit = new MenuItem("Beenden");
        MenuItem menuItemSettings = new MenuItem("Einstellungen");
        menuGame.getItems().addAll(menuItemNewGame, menuItemSettings, menuItemExit);

        menuItemExit.setOnAction(event -> {
            System.exit(0);
        });

        menuItemNewGame.setOnAction(event -> {
            new Thread(() -> {
                gameLogic.runGame(player1Name, player1AI, player2Name, player2AI);
            }).start();

        });


        menuItemSettings.setOnAction(event -> {
            Dialog<Pair<List<String>, List<Boolean>>> dialog = new Dialog<>();
            dialog.setTitle("Einstellungen bearbeiten");
            Label label = new Label("Bitte Spielernamen eingeben. \n" +
                    "Der erste Spieler spielt mit den schwarzen Steinen. Der zweite mit den schwarzen.\n" +
                    "Ungültige Werte werden durch vorher eingestellte Werte oder Standardwerte eingesetzt.");
            label.setWrapText(true);

            ButtonType startButtonType = new ButtonType("Einstellungen speichern", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(startButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(GUIValues.PADDING);
            grid.setVgap(GUIValues.PADDING);
            grid.setPadding(new Insets(GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING, GUIValues.PADDING));

            // first line
            grid.add(new Label("Farbe"), 0, 0);
            grid.add(new Label("Spielernamen"), 1, 0);
            grid.add(new Label("Computer"), 2, 0);

            // first column -> to show colors
            Label black = new Label("\t");
            black.setStyle(GUIValues.BLACK);
            Label white = new Label("\t");
            white.setStyle(GUIValues.WHITE);
            grid.add(white, 0, 1);
            grid.add(black, 0, 2);

            // second column -> textFields for names
            TextField textFieldPlayer1 = new TextField();
            textFieldPlayer1.setPromptText("Spieler 1");
            TextField textFieldPlayer2 = new TextField();
            textFieldPlayer2.setPromptText("Spieler 2");

            grid.add(textFieldPlayer1, 1, 1);
            grid.add(textFieldPlayer2, 1, 2);

            // third column -> chckbox to select implementation
            CheckBox firstPlayerAI = new CheckBox();
            CheckBox secondPlayerAI = new CheckBox();
            secondPlayerAI.setSelected(true);

            grid.add(firstPlayerAI, 2, 1);
            grid.add(secondPlayerAI, 2, 2);


            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == startButtonType) {
                    List<String> strings = new ArrayList();
                    strings.add(textFieldPlayer1.getText());
                    strings.add(textFieldPlayer2.getText());

                    List<Boolean> bools = new ArrayList();
                    bools.add(firstPlayerAI.isSelected());
                    bools.add(secondPlayerAI.isSelected());

                    return new Pair<>(strings, bools);
                }
                return null;
            });

            VBox vBox = new VBox();
            vBox.getChildren().addAll(label, grid);
            vBox.setPadding(new Insets(GUIValues.PADDING, GUIValues.PADDING,0, GUIValues.PADDING));

            dialog.getDialogPane().setContent(vBox);

            Optional<Pair<List<String>, List<Boolean>>> result = dialog.showAndWait();

            result.ifPresent(results -> {
                if (!results.getKey().get(0).trim().isEmpty()) {
                    player1Name = results.getKey().get(0);
                }
                if (!results.getKey().get(1).trim().isEmpty()) {
                    player2Name = results.getKey().get(1);
                }
                player1AI = results.getValue().get(0);
                player2AI = results.getValue().get(1);

            });


        });


        menuBar.getMenus().addAll(menuGame);
        return menuBar;
    }

    private void convertHighscoreLists(List<String> playerNames, List<String> amountGames, List<String> wonGames, List<String> first, List<String> second, List<String> third, List<String> fourth, List<String> fifth) {
        try {
            first.add(playerNames.get(0));
        } catch (IndexOutOfBoundsException e) {
            first.add("");
        }
        try {
            second.add(playerNames.get(1));
        } catch (IndexOutOfBoundsException e) {
            second.add("");
        }
        try {
            third.add(playerNames.get(2));
        } catch (IndexOutOfBoundsException e) {
            third.add("");
        }
        try {
            fourth.add(playerNames.get(3));
        } catch (IndexOutOfBoundsException e) {
            fourth.add("");
        }
        try {
            fifth.add(playerNames.get(4));
        } catch (IndexOutOfBoundsException e) {
            fifth.add("");
        }

        try {
            first.add(amountGames.get(0));
        } catch (IndexOutOfBoundsException e) {
            first.add("");
        }
        try {
            second.add(amountGames.get(1));
        } catch (IndexOutOfBoundsException e) {
            second.add("");
        }
        try {
            third.add(amountGames.get(2));
        } catch (IndexOutOfBoundsException e) {
            third.add("");
        }
        try {
            fourth.add(amountGames.get(3));
        } catch (IndexOutOfBoundsException e) {
            fourth.add("");
        }
        try {
            fifth.add(amountGames.get(4));
        } catch (IndexOutOfBoundsException e) {
            fifth.add("");
        }

        try {
            first.add(wonGames.get(0));
        } catch (IndexOutOfBoundsException e) {
            first.add("");
        }
        try {
            second.add(wonGames.get(1));
        } catch (IndexOutOfBoundsException e) {
            second.add("");
        }
        try {
            third.add(wonGames.get(2));
        } catch (IndexOutOfBoundsException e) {
            third.add("");
        }
        try {
            fourth.add(wonGames.get(3));
        } catch (IndexOutOfBoundsException e) {
            fourth.add("");
        }
        try {
            fifth.add(wonGames.get(4));
        } catch (IndexOutOfBoundsException e) {
            fifth.add("");
        }

    }

}
