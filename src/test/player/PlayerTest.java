package player;

import common.Move;
import common.PlayerColor;
import common.StoneMove;

public class PlayerTest {

    public static void main(String[] args) {

    }

    void testGetName(){
        Player testPlayer = Player.create(false, "Test Player 1", PlayerColor.WHITE, null, null);

        testPlayer.getName();
        eingabeGleich(testPlayer.getName(), "player1", "player1");
    }

    void testGetColor(){
        Player testPlayer = Player.create(false, "Test Player 1", PlayerColor.WHITE, null, null);
        eingabeGleich(testPlayer.getColor(), PlayerColor.WHITE, "GetColor");

    }
    void testIsAi(){
        Player testPlayer = Player.create(false, "Test Player 1", PlayerColor.WHITE, null, null);
        eingabeGleich(testPlayer.isAi(), true, "isAi");
    }
    void testMakeMove(){
        Player testPlayer = Player.create(false, "Test Player 1", PlayerColor.WHITE, null, null);
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
