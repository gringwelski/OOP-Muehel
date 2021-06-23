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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import common.*;
import game.GameLogic;
import player.Player;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GUI extends Application implements GUIGame, GUIPlayer {
    private static GridPane grid;
    private static BorderPane highscorePane;
    private static Label message = new Label();
    private static Label alert = new Label();
    private GameLogic gameLogic;
    private Move move;
    private boolean firstClickDone = false;
    private static final Object synchronize = new Object();
    private static ConcurrentHashMap<Point, Point> mapping = new ConcurrentHashMap<Point, Point>();

    @Override
    public void start(Stage stage) {
        createMapping();

        grid = createGrid();
        fill(grid);

        alert.setId("alert");
        message.setId("message");

        highscorePane = createHighscorePane();
        highscorePane.setVisible(false);

        MenuBar menu = createMenu(stage);

        message.setText("Druecke auf Spiel und dann auf Start, um zu spielen.");
        message.setPadding(new Insets(10, 10, 10, 10));
        alert.setText("");
        alert.setPadding(new Insets(0, 10, 10, 10));

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
        label.setPadding(new Insets(25, 10, 10, 10));
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
        hBox.setPadding(new Insets(10, 10, 10, 10));

        highscorePane.setTop(label);
        highscorePane.setBottom(hBox);

        highscorePane.setStyle("-fx-background-color: rgb(255, 255, 255, 0.7);");

        return highscorePane;
    }


    @Override
    public synchronized void  synchronizeGame(PlayerColor[][] field) {
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
                            label.setStyle("-fx-background-image: none; -fx-background-color: rgba(100, 100, 100, 0.2);");
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
                gameLogic.runGame();
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
                synchronize.wait(3000);
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
        playerColumn.setMinWidth(150);
        playerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        TableColumn<List<String>, String> playedGamesColumn = new TableColumn("gespielt");
        playedGamesColumn.setMinWidth(150);
        playedGamesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        TableColumn<List<String>, String> wonGamesColumn = new TableColumn("gewonnen");
        wonGamesColumn.setMinWidth(150);
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
        return makeManualMove(player, "");
    }

    @Override
    public Move makeManualMove(Player player, String msg) {
        firstClickDone = false;
        Platform.runLater(() -> {
            message.setText(msg);
            alert.setText(player.getName() + " ist am Zug.");
        });

        synchronized (synchronize) {
            try {
                synchronize.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return move;
    }

    @Override
    public Move setStone(Player player, String msg) {
        firstClickDone = true;
        Platform.runLater(() -> {
            message.setText(msg);
            alert.setText(player.getName() + " ist am Zug.");
        });

        synchronized (synchronize) {
            try {
                synchronize.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return move;
    }


    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);

        RowConstraints rowConstraints = new RowConstraints();
        for (int r = 0; r < 7; r++) {
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        ColumnConstraints columnConstraints = new ColumnConstraints();
        for (int c = 0; c < 7; c++) {
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        return gridPane;
    }

    private void fill(GridPane grid) {
        grid.setStyle("-fx-background-color: rgba(100, 100, 100, 0.0); -fx-background-image:url('/images/board.png'); -fx-background-size: cover;");

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                Label label = new Label();
                label.setAlignment(Pos.CENTER);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setMinSize(75, 75);
                label.setFont(new Font(18));
                try {
                    Point point = getKeyOfMapping(new FieldPoint(c, r));
                    label.setId("point" + point.getX() + "-" + point.getY());
                } catch (NullPointerException e) {
                    label.setId("gridpoint");
                }


                Point point = getKeyOfMapping(new FieldPoint(r, c));


                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        synchronized (synchronize) {
                            if (!firstClickDone) {
                                move = new StoneMove(point, new FieldPoint(-1, -1));
                                firstClickDone = true;
                            } else {
                                move = new StoneMove(move.getStartPoint(), point);
                                synchronize.notifyAll();
                            }
                        }
                    }
                });


                grid.add(label, c, r);

                label.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5);");
            }
        }
    }

    private MenuBar createMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu menuGame = new Menu("Spiel");
        MenuItem menuItemNewGame = new MenuItem("Start");
        MenuItem menuItemExit = new MenuItem("Beenden");
        menuGame.getItems().addAll(menuItemNewGame, menuItemExit);

        Menu menuSettings = new Menu("Einstellungen");
        MenuItem menuItemPlayer = new MenuItem("Spielernamen");
        menuSettings.getItems().add(menuItemPlayer);

        menuBar.getMenus().addAll(menuGame, menuSettings);
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
