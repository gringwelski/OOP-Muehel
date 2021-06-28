package game;

import common.Move;
import common.PlayerColor;
import common.Point;
import common.StoneAction;
import gui.GUI;
import gui.GUIGame;
import player.Player;


public class Game implements GameLogic {
    PlayerColor[][] field = new PlayerColor[7][6];
    StoneAction phase = StoneAction.SET;
    PlayerColor currentPlayColor;
    int remainingStonesP1;
    int remainingStonesP2;
    boolean isLoosed;


    public PlayerColor[][] getState() {
        return field;
    }

    public boolean isValidMove(Move mv) {
        if ((phase != StoneAction.SET && mv.getStartPoint() == null) || mv.getEndPoint() == null ){
           return false;
        }
        // --------------------------------------------- for Phase Set ---------------------------------
        if (phase == StoneAction.SET) {

            Point endPoint = mv.getEndPoint();
            return field[endPoint.getX()][endPoint.getY()] == PlayerColor.NONE;
        }
        // --------------------------------------------- for Phase Push -------------------------------
        if (phase == StoneAction.PUSH) {
            Point startPoint = mv.getStartPoint();
            int startX = startPoint.getX();
            int startY = startPoint.getY();
            Point endPoint = mv.getEndPoint();
            int endX = endPoint.getX();
            int endY = endPoint.getY();
            return field[startX][startY] == currentPlayColor
                    && field[endY][endY] == PlayerColor.NONE
                    //for Point 0 / 0
                    && ((startX == 0 && startY == 0 && endX == 0 && endY == 1)
                    || (startX == 0 && startY == 0 && endX == 3 && endY == 0)
                    //for Point 0 / 1
                    || (startX == 0 && startY == 1 && endX == 0 && endY == 0)
                    || (startX == 0 && startY == 1 && endX == 1 && endY == 1)
                    || (startX == 0 && startY == 1 && endX == 0 && endY == 2)
                    //for Point 0 / 2
                    || (startX == 0 && startY == 2 && endX == 0 && endY == 1)
                    || (startX == 0 && startY == 2 && endX == 3 && endY == 5)
                    //for Point 1 / 0
                    || (startX == 1 && startY == 0 && endX == 3 && endY == 1)
                    || (startX == 1 && startY == 0 && endX == 1 && endY == 1)
                    //for Point 1/ 1
                    || (startX == 1 && startY == 1 && endX == 3 && endY == 1)
                    || (startX == 1 && startY == 1 && endX == 1 && endY == 0)
                    || (startX == 1 && startY == 1 && endX == 1 && endY == 2)
                    || (startX == 1 && startY == 1 && endX == 2 && endY == 1)
                    //for Point 1 / 2
                    || (startX == 1 && startY == 2 && endX == 1 && endY == 1)
                    || (startX == 1 && startY == 2 && endX == 3 && endY == 4)
                    //for Point 2 / 0
                    || (startX == 2 && startY == 0 && endX == 3 && endY == 2)
                    || (startX == 2 && startY == 0 && endX == 2 && endY == 1)
                    //for Point 2 / 1
                    || (startX == 2 && startY == 1 && endX == 2 && endY == 0)
                    || (startX == 2 && startY == 1 && endX == 2 && endY == 2)
                    || (startX == 2 && startY == 1 && endX == 1 && endY == 1)
                    //for Point 2 / 2
                    || (startX == 2 && startY == 2 && endX == 2 && endY == 1)
                    || (startX == 2 && startY == 2 && endX == 3 && endY == 3)
                    //for Point 3 / 0
                    || (startX == 3 && startY == 0 && endX == 0 && endY == 0)
                    || (startX == 3 && startY == 0 && endX == 6 && endY == 0)
                    //for Point 3 / 1
                    || (startX == 3 && startY == 1 && endX == 3 && endY == 0)
                    || (startX == 3 && startY == 1 && endX == 5 && endY == 0)
                    || (startX == 3 && startY == 1 && endX == 3 && endY == 2)
                    || (startX == 3 && startY == 1 && endX == 1 && endY == 0)
                    //for Point 3 / 2
                    || (startX == 3 && startY == 2 && endX == 2 && endY == 2)
                    || (startX == 3 && startY == 2 && endX == 3 && endY == 4)
                    || (startX == 3 && startY == 2 && endX == 4 && endY == 2)
                    //for Point 3 / 3
                    || (startX == 3 && startY == 3 && endX == 2 && endY == 2)
                    || (startX == 3 && startY == 3 && endX == 3 && endY == 4)
                    || (startX == 3 && startY == 3 && endX == 4 && endY == 2)
                    //for Point 3 / 4
                    || (startX == 3 && startY == 4 && endX == 3 && endY == 3)
                    || (startX == 3 && startY == 4 && endX == 1 && endY == 2)
                    || (startX == 3 && startY == 4 && endX == 3 && endY == 5)
                    || (startX == 3 && startY == 4 && endX == 5 && endY == 2)
                    //for Point 3 / 5
                    || (startX == 3 && startY == 5 && endX == 3 && endY == 4)
                    || (startX == 3 && startY == 5 && endX == 0 && endY == 2)
                    || (startX == 3 && startY == 5 && endX == 6 && endY == 2)
                    //for Point 4 / 0
                    || (startX == 4 && startY == 0 && endX == 3 && endY == 2)
                    || (startX == 4 && startY == 0 && endX == 4 && endY == 1)
                    //for Point 4 / 1
                    || (startX == 4 && startY == 1 && endX == 4 && endY == 0)
                    || (startX == 4 && startY == 1 && endX == 4 && endY == 2)
                    //for Point 4 / 2
                    || (startX == 4 && startY == 2 && endX == 4 && endY == 1)
                    || (startX == 4 && startY == 2 && endX == 3 && endY == 3)
                    //for Point 5 / 0
                    || (startX == 5 && startY == 0 && endX == 3 && endY == 1)
                    || (startX == 5 && startY == 0 && endX == 5 && endY == 1)
                    //for Point 5 / 1
                    || (startX == 5 && startY == 1 && endX == 5 && endY == 0)
                    || (startX == 5 && startY == 1 && endX == 4 && endY == 1)
                    || (startX == 5 && startY == 1 && endX == 5 && endY == 2)
                    || (startX == 5 && startY == 1 && endX == 6 && endY == 1)
                    //for Point 5 / 2
                    || (startX == 5 && startY == 2 && endX == 5 && endY == 1)
                    || (startX == 5 && startY == 2 && endX == 3 && endY == 5)
                    //for Point 6 / 0
                    || (startX == 6 && startY == 0 && endX == 3 && endY == 0)
                    || (startX == 6 && startY == 0 && endX == 6 && endY == 1)
                    //for 6 / 1
                    || (startX == 6 && startY == 1 && endX == 6 && endY == 0)
                    || (startX == 6 && startY == 1 && endX == 5 && endY == 1)
                    || (startX == 6 && startY == 1 && endX == 6 && endY == 2)
                    //for 6/2
                    || (startX == 6 && startY == 2 && endX == 6 && endY == 1)
                    || (startX == 6 && startY == 2 && endX == 3 && endY == 5)

            );
        }

        // ----------------------------------------- For Phase Jump----------------------
        if (phase == StoneAction.JUMP) {
            Point startPoint = mv.getStartPoint();
            Point endPoint = mv.getEndPoint();
            return field[startPoint.getX()][startPoint.getY()] == currentPlayColor
                    && field[endPoint.getX()][endPoint.getY()] == PlayerColor.NONE;
        } else {
            return false;
        }
    }


    public void runGame(String namePlayer1, boolean player1IsAi, String namePlayer2, boolean player2IsAi) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                field[x][y] = PlayerColor.NONE;
            }
        }
        GUI gui = new GUI();
        isLoosed = false;
        Player player1 = Player.create(player1IsAi, namePlayer1, PlayerColor.WHITE, this, gui);
        Player player2 = Player.create(player2IsAi, namePlayer2, PlayerColor.BLACK, this, gui);
        remainingStonesP1 = 9;
        remainingStonesP2 = 9;

        for (int z = 0; z < 9; z++) {
            phase = StoneAction.SET;
            Move mv1 = player1.makeMove();
            if (isValidMove(mv1)) {
                Point endP = mv1.getEndPoint();
                field[endP.getX()][endP.getY()] = PlayerColor.WHITE;
                gui.synchronizeGame(field);
                throwIfMuehle(endP, player1);
            }

            Move mv2 = player2.makeMove();
            if (isValidMove(mv2)) {
                Point endP = mv2.getEndPoint();
                field[endP.getX()][endP.getY()] = PlayerColor.BLACK;
                gui.synchronizeGame(field);
                throwIfMuehle(endP, player2);
            }
        }

        while (!isLoosed) {
            currentPlayColor = PlayerColor.WHITE;
            //Player 2 Move
            if (remainingStonesP1 > 2) {
                phase = StoneAction.PUSH;
                Move mv = player1.makeMove();
                gui.synchronizeGame(field);
                throwIfMuehle(mv.getEndPoint(), player1);
            } else {
                phase = StoneAction.JUMP;
                Move mv = player1.makeMove();
                gui.synchronizeGame(field);
                throwIfMuehle(mv.getEndPoint(), player1);
            }
            if (remainingStonesP2 < 2) {
                isLoosed = true;
                break;
            }
            //Player 2 Move
            currentPlayColor = PlayerColor.BLACK;
            if (remainingStonesP2 > 2) {
                phase = StoneAction.PUSH;
                Move mv = player2.makeMove();
                gui.synchronizeGame(field);
                throwIfMuehle(mv.getEndPoint(), player2);
            } else {
                phase = StoneAction.JUMP;
                Move mv = player2.makeMove();
                gui.synchronizeGame(field);
                throwIfMuehle(mv.getEndPoint(), player2);
            }
            if (remainingStonesP1 < 2) {
                isLoosed = true;
            }

        }
    }

    /**
     *
     * @param point the stone which should belong to the muehle group
     * @param color the color of the muehle group
     * @return true if the stone belong to a muehle group
     */

    boolean isMuehle(Point point, PlayerColor color) {
        int x = point.getX();
        int y = point.getY();

        return (
                //Point 0 / 0
                (x == 0 && y == 0 && (field[0][1] == color && field[0][2] == color) || (field[3][0] == color && field[6][0] == color))
                //Point 0 / 1
                || (x == 0 && y == 1 && (field[0][0] == color && field[0][2] == color) || (field[1][1] == color && field[2][1] == color))
                //Point 0 / 2
                || (x == 0 && y == 2 && (field[0][0] == color && field[0][1] == color) || (field[3][5] == color && field[6][2] == color))
                //Point 1 / 0
                || (x == 1 && y == 0 && (field[1][1] == color && field[1][2] == color) || (field[3][1] == color && field[5][0] == color))
                //Point 1 / 1
                || (x == 1 && y == 1 && (field[1][0] == color && field[1][2] == color) || (field[2][1] == color && field[0][1] == color))
                //Point 1 / 2
                || (x == 1 && y == 2 && (field[1][0] == color && field[1][1] == color) || (field[3][4] == color && field[5][2] == color))
                //Point 3 / 0
                || (x == 3 && y == 0 && (field[3][1] == color && field[3][2] == color) || (field[0][0] == color && field[6][0] == color))
                //Point 3 / 1
                || (x == 3 && y == 1 && (field[3][0] == color && field[3][2] == color) || (field[1][0] == color && field[5][0] == color))
                //Point 3 / 2
                || (x == 3 && y == 2 && (field[3][0] == color && field[3][1] == color) || (field[2][0] == color && field[4][0] == color))
                //Point 3 / 3
                || (x == 3 && y == 3 && (field[3][4] == color && field[3][5] == color) || (field[2][2] == color && field[4][2] == color))
                //Point 3 / 4
                || (x == 3 && y == 4 && (field[3][3] == color && field[3][5] == color) || (field[1][2] == color && field[5][2] == color))
                //Point 3 / 5
                || (x == 3 && y == 5 && (field[3][3] == color && field[3][4] == color) || (field[0][2] == color && field[6][2] == color))
                //Point 4 / 0
                || (x == 4 && y == 0 && (field[4][1] == color && field[4][2] == color) || (field[3][2] == color && field[2][0] == color))
                //Point 4 / 1
                || (x == 4 && y == 1 && (field[4][0] == color && field[4][2] == color) || (field[5][1] == color && field[6][1] == color))
                //Point 4 / 2
                || (x == 4 && y == 2 && (field[4][1] == color && field[4][0] == color) || (field[3][3] == color && field[2][2] == color))
                //Point 5 / 0
                || (x == 5 && y == 0 && (field[5][1] == color && field[5][2] == color) || (field[3][1] == color && field[1][0] == color))
                //Point 5 / 1
                || (x == 5 && y == 1 && (field[4][1] == color && field[6][2] == color) || (field[5][0] == color && field[5][2] == color))
                //Point 5 / 2
                || (x == 5 && y == 2 && (field[5][1] == color && field[5][0] == color) || (field[3][4] == color && field[1][2] == color))
                //Point 6 / 0
                || (x == 6 && y == 0 && (field[6][1] == color && field[6][2] == color) || (field[0][0] == color && field[3][0] == color))
                //Point 6 / 1
                || (x == 6 && y == 1 && (field[6][0] == color && field[6][2] == color) || (field[5][1] == color && field[4][1] == color))
                //Point 6 / 2
                || (x == 6 && y == 2 && (field[6][0] == color && field[6][1] == color) || (field[3][5] == color && field[0][2] == color))
                );


    }

    public StoneAction getPhase() {
        return phase;
    }

    public static void main(String[] args) {
        GUIGame gui = new GUI();
        gui.create(new Game());
    }

    /**
     * if the stone was moved so that a muehle is existing now, the player is asked which stone of the enemie he wants to throw
     * if the stone is valid, he gets thrown away
     * @param point the Endpoint of the last Move
     * @param player the player who moved the Stone
     */
    void throwIfMuehle(Point point, Player player) {
        if (isMuehle(point, currentPlayColor)) {
            Point selectedStone = player.selectThrowStone();
            PlayerColor enemiesColor;
            if (currentPlayColor == PlayerColor.WHITE) {
                enemiesColor = PlayerColor.BLACK;
            } else {
                enemiesColor = PlayerColor.WHITE;
            }
            if (!(field[selectedStone.getX()][selectedStone.getY()] == currentPlayColor || field[selectedStone.getX()][selectedStone.getY()] == PlayerColor.NONE || isMuehle(selectedStone, enemiesColor))) {
                field[selectedStone.getX()][selectedStone.getY()] = PlayerColor.NONE;
            }

        }
    }

    /**
     *
      * @param point the point of the Stone you want to throw away
     * @return return true if you can throw the stone
     */
    public boolean isThrowStoneValid(Point point){
        PlayerColor enemiesColor;
        if(currentPlayColor == PlayerColor.WHITE) {
            enemiesColor = PlayerColor.BLACK;
        }else{
            enemiesColor = PlayerColor.WHITE;
        }

        return !(field[point.getX()][point.getY()] == currentPlayColor || field[point.getX()][point.getY()] == PlayerColor.NONE || isMuehle(point, enemiesColor));
    }
}
