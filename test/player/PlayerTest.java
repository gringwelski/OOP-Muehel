package test.player;

import src.common.StoneMove;
import src.player.Player;
import test.player.PlayerDummyClass;
import src.common.PlayerColor;
import src.common.Move;

public class PlayerTest {
    void testGetName(){
        Player testPlayer = new PlayerDummyClass();

        testPlayer.getName();
        eingabeGleich(testPlayer.getName(), "player1", "player1");
    }

    void testGetColor(){
        Player testPlayer = new PlayerDummyClass();
        eingabeGleich(testPlayer.getColor(), PlayerColor.WHITE, "GetColor");

    }
    void testIsAi(){
        Player testPlayer = new PlayerDummyClass();
        eingabeGleich(testPlayer.isAi(), true, "isAi");
    }
    void testMakeMove(){
        Player testPlayer = new PlayerDummyClass();
        Move x = new StoneMove(1,1,2,2);
        eingabeGleich(testPlayer.makeMove(), x, "MakeMove");
    }

    void eingabeGleich(String ist, String soll, String testName){
        if (ist.equals(soll)) {
            System.out.println(testName + " war erfolgreich.");
        }else{
            System.out.println("[ERROR] " + testName + " ist gescheitert.");
        }
    }
    void eingabeGleich(PlayerColor ist, PlayerColor soll, String testName){
        if (ist == soll) {
            System.out.println(testName + " war erfolgreich.");
        }else{
            System.out.println("[ERROR] " + testName + " ist gescheitert.");
        }
    }
    void eingabeGleich(boolean ist, boolean soll, String testName){
        if (ist == soll) {
            System.out.println(testName + " war erfolgreich.");
        }else{
            System.out.println("[ERROR] " + testName + " ist gescheitert.");
        }
    }
    void eingabeGleich(Move ist, Move soll, String testName){
        if (ist == soll) {
            System.out.println(testName + " war erfolgreich.");
        }else{
            System.out.println("[ERROR] " + testName + " ist gescheitert.");
        }
    }
}
