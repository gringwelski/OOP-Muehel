package game;

import common.Move;
import common.PlayerColor;
import common.Point;
import common.StoneAction;
import gui.GUI;
import gui.GUIGame;
import player.Player;


public class Game implements GameLogic{
    PlayerColor[][] field = new PlayerColor[6][5];
    StoneAction phase = StoneAction.SET;
    PlayerColor currentPlayColor;

    public PlayerColor[][] getState(){
        return field;
    }

    public boolean isValidMove(Move mv) {
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
                    && field[startPoint.getX()][startPoint.getY()] == PlayerColor.NONE;
        }
        else{
            return false;
        }
    }


    public void runGame(String namePlayer1, boolean player1IsAi, String namePlayer2, boolean player2IsAi){
        for(int x = 0; x<field.length; x++){
            for(int y = 0; y<field[0].length; y++){
                field[x][y] = PlayerColor.NONE;
            }
        }
    }

    public StoneAction getPhase(){
        return phase;
    }

    public static void main(String[] args){
        GUIGame gui = new GUI();

    }
}
