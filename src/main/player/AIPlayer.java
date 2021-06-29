package player;

import common.*;
import game.GameLogic;
import gui.GUIPlayer;

import java.util.*;

public class AIPlayer implements Player {

    private final String name;
    private final PlayerColor color;
    private final GameLogic gameLogic;
    private int madeMoves;
    private final List<FieldPoint> pointsOfMovement = Arrays.asList(
            new FieldPoint(0, 0), new FieldPoint(0, 1), new FieldPoint(0, 2),
            new FieldPoint(1, 0), new FieldPoint(1, 1), new FieldPoint(1, 2),
            new FieldPoint(2, 0), new FieldPoint(2, 1), new FieldPoint(2, 2),
            new FieldPoint(3, 0), new FieldPoint(3, 1), new FieldPoint(3, 2), new FieldPoint(3, 3), new FieldPoint(3, 4), new FieldPoint(3, 5),
            new FieldPoint(4, 0), new FieldPoint(4, 1), new FieldPoint(4, 2),
            new FieldPoint(5, 0), new FieldPoint(5, 1), new FieldPoint(5, 2),
            new FieldPoint(6, 0), new FieldPoint(6, 1), new FieldPoint(6, 2)
    );
    private final HashMap<Point, List<Point>> neighbourPoints = new HashMap<Point, List<Point>>() {{
        put(new FieldPoint(0, 0), Arrays.asList(new FieldPoint(0, 1), new FieldPoint(3, 0)));
        put(new FieldPoint(0, 1), Arrays.asList(new FieldPoint(0, 0), new FieldPoint(1, 1), new FieldPoint(0, 2)));
        put(new FieldPoint(0, 2), Arrays.asList(new FieldPoint(0, 1), new FieldPoint(3, 5)));
        put(new FieldPoint(1, 0), Arrays.asList(new FieldPoint(3, 1), new FieldPoint(1, 1)));
        put(new FieldPoint(1, 1), Arrays.asList(new FieldPoint(0, 1), new FieldPoint(1, 0), new FieldPoint(2, 1), new FieldPoint(1, 2)));
        put(new FieldPoint(1, 2), Arrays.asList(new FieldPoint(1, 1), new FieldPoint(3, 4)));
        put(new FieldPoint(2, 0), Arrays.asList(new FieldPoint(3, 2), new FieldPoint(2, 1)));
        put(new FieldPoint(2, 1), Arrays.asList(new FieldPoint(2, 0), new FieldPoint(1, 1), new FieldPoint(2, 2)));
        put(new FieldPoint(2, 2), Arrays.asList(new FieldPoint(2, 1), new FieldPoint(3, 3)));
        put(new FieldPoint(3, 0), Arrays.asList(new FieldPoint(0, 0), new FieldPoint(6, 0), new FieldPoint(3, 1)));
        put(new FieldPoint(3, 1), Arrays.asList(new FieldPoint(3, 0), new FieldPoint(1, 0), new FieldPoint(3, 2), new FieldPoint(5, 0)));
        put(new FieldPoint(3, 2), Arrays.asList(new FieldPoint(3, 1), new FieldPoint(2, 0), new FieldPoint(4, 0)));
        put(new FieldPoint(3, 3), Arrays.asList(new FieldPoint(2, 2), new FieldPoint(4, 2), new FieldPoint(3, 4)));
        put(new FieldPoint(3, 4), Arrays.asList(new FieldPoint(1, 2), new FieldPoint(3, 3), new FieldPoint(5, 2), new FieldPoint(3, 5)));
        put(new FieldPoint(3, 5), Arrays.asList(new FieldPoint(0, 2), new FieldPoint(3, 4), new FieldPoint(6, 2)));
        put(new FieldPoint(4, 0), Arrays.asList(new FieldPoint(3, 2), new FieldPoint(4, 1)));
        put(new FieldPoint(4, 1), Arrays.asList(new FieldPoint(4, 0), new FieldPoint(5, 1), new FieldPoint(4, 2)));
        put(new FieldPoint(4, 2), Arrays.asList(new FieldPoint(4, 1), new FieldPoint(3, 3)));
        put(new FieldPoint(5, 0), Arrays.asList(new FieldPoint(3, 1), new FieldPoint(5, 1)));
        put(new FieldPoint(5, 1), Arrays.asList(new FieldPoint(5, 0), new FieldPoint(4, 1), new FieldPoint(5, 2), new FieldPoint(6, 1)));
        put(new FieldPoint(5, 2), Arrays.asList(new FieldPoint(5, 1), new FieldPoint(3, 4)));
        put(new FieldPoint(6, 0), Arrays.asList(new FieldPoint(3, 0), new FieldPoint(6, 1)));
        put(new FieldPoint(6, 1), Arrays.asList(new FieldPoint(6, 0), new FieldPoint(5, 1), new FieldPoint(6, 2)));
        put(new FieldPoint(6, 2), Arrays.asList(new FieldPoint(6, 1), new FieldPoint(3, 5)));
    }};
    private final int maxDepth = 6;
    private Player opponent;
    private Move finalMove;
    private PlayerColor[][] field;

    protected AIPlayer(String name, PlayerColor color, GameLogic gameLogic) {
        this.name = name;
        this.color = color;
        this.gameLogic = gameLogic;
        this.madeMoves = 0;
    }

    @Override
    public Move makeMove() {
        if (this.opponent == null)
            this.opponent = gameLogic.getPlayers()[0] == this ? gameLogic.getPlayers()[1] : gameLogic.getPlayers()[0];
        field = gameLogic.getState();
        int value = max(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("================== EXECUTING SEARCH ==================");
        System.out.println("Evaluated, maximizing: " + evaluate(true));
        System.out.println("Evaluated, minimizing: " + evaluate(false));
        System.out.println("Search value: " + value);
        System.out.println("Final move: " + finalMove);
        System.out.println("Is valid move? " + gameLogic.isValidMove(finalMove));

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        madeMoves++;
        return finalMove;
    }

    @Override
    public Point selectThrowStone() {
        //TODO: Implement
        return null;
    }

    @Override
    public boolean isDoneSetting() {
        return madeMoves >= 9;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PlayerColor getColor() {
        return this.color;
    }

    @Override
    public boolean isAi() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIPlayer aiPlayer = (AIPlayer) o;
        return getName().equals(aiPlayer.getName()) && getColor() == aiPlayer.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getColor());
    }



    // Alpha Beta Pruning

    private int getCountOfStonesFor(Player player) {
        //System.out.println("Getting count of stones for " + player.getName());
        int count = 0;
        for (PlayerColor[] row : field)
            for (PlayerColor color : row)
                if (player.getColor() == color)
                    count++;
        return count;
    }

    private ArrayList<Move> generatePossibleMovesFor(Player player) {
        //System.out.println("Generating possible moves for " + player.getName());
        ArrayList<Point> selfStonePlacements = new ArrayList<>();
        ArrayList<Point> opponentStonePlacements = new ArrayList<>();
        for (int x = 0; x < field.length; x++)
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == null)
                    continue;
                if (field[x][y] == this.color)
                    selfStonePlacements.add(new FieldPoint(x, y));
                if (field[x][y] == opponent.getColor())
                    opponentStonePlacements.add(new FieldPoint(x, y));
            }

        ArrayList<Move> possibleMoves = new ArrayList<>();

        // Check if player is in set phase or only has three stones left
        if (!player.isDoneSetting() || (player.isDoneSetting() && getCountOfStonesFor(player) <= 3)) {
            // Every free field is a possible move
            for (Point point : pointsOfMovement) {
                if (opponentStonePlacements.contains(point) || selfStonePlacements.contains(point))
                    continue;
                possibleMoves.add(new StoneMove(null, point));
            }
        } else {
            // Move is only possible if the neighbour field of a stone is free
            for (Point point : player == opponent ? opponentStonePlacements : selfStonePlacements) {
                for (Point neighbour : neighbourPoints.get(point)) {
                    if (opponentStonePlacements.contains(point) || selfStonePlacements.contains(point))
                        continue;
                    possibleMoves.add(new StoneMove(point, neighbour));
                }
            }
        }

        Collections.shuffle(possibleMoves);
        return possibleMoves;
    }

    private int countOfMills(Player player) {
        PlayerColor currentColor = player.getColor();
        int count = 0;
        for (int x = 0; x < 7; x++) {
            if (x == 3)
                continue;
            int countOfStones = 0;
            for (int y = 0; y < 3; y++) {
                if (field[x][y] == currentColor)
                    countOfStones++;
            }
            if (countOfStones == 3)
                count++;
        }
        if (field[3][0] == currentColor && field[3][1] == currentColor && field[3][2] == currentColor) count++;
        if (field[3][3] == currentColor && field[3][4] == currentColor && field[3][5] == currentColor) count++;

        if (field[0][0] == currentColor && field[3][0] == currentColor && field[6][0] == currentColor) count++;
        if (field[1][0] == currentColor && field[3][1] == currentColor && field[5][0] == currentColor) count++;
        if (field[2][0] == currentColor && field[3][2] == currentColor && field[4][0] == currentColor) count++;

        if (field[0][1] == currentColor && field[1][1] == currentColor && field[2][1] == currentColor) count++;
        if (field[4][1] == currentColor && field[5][1] == currentColor && field[6][1] == currentColor) count++;

        if (field[2][2] == currentColor && field[3][3] == currentColor && field[4][2] == currentColor) count++;
        if (field[1][2] == currentColor && field[3][4] == currentColor && field[5][2] == currentColor) count++;
        if (field[0][2] == currentColor && field[3][5] == currentColor && field[6][2] == currentColor) count++;

        return count;
    }

    private int countOfPossibleMills(Player player) {
        PlayerColor currentColor = player.getColor();
        int count = 0;
        for (int x = 0; x < 7; x++) {
            if (x == 3)
                continue;
            int countOfStones = 0;
            for (int y = 0; y < 3; y++) {
                if (field[x][y] == currentColor)
                    countOfStones++;
            }
            if (countOfStones >= 2)
                count++;
        }
        if ((field[3][0] == currentColor && field[3][1] == currentColor) || (field[3][1] == currentColor && field[3][2] == currentColor)) count++;
        if ((field[3][3] == currentColor && field[3][4] == currentColor) || (field[3][4] == currentColor && field[3][5] == currentColor)) count++;

        if ((field[0][0] == currentColor && field[3][0] == currentColor) || (field[3][0] == currentColor && field[6][0] == currentColor)) count++;
        if ((field[1][0] == currentColor && field[3][1] == currentColor) || (field[3][1] == currentColor && field[5][0] == currentColor)) count++;
        if ((field[2][0] == currentColor && field[3][2] == currentColor) || (field[3][2] == currentColor && field[4][0] == currentColor)) count++;

        if ((field[0][1] == currentColor && field[1][1] == currentColor) || (field[1][1] == currentColor && field[2][1] == currentColor)) count++;
        if ((field[4][1] == currentColor && field[5][1] == currentColor) || (field[5][1] == currentColor && field[6][1] == currentColor)) count++;

        if ((field[2][2] == currentColor && field[3][3] == currentColor) || (field[3][3] == currentColor && field[4][2] == currentColor)) count++;
        if ((field[1][2] == currentColor && field[3][4] == currentColor) || (field[3][4] == currentColor && field[5][2] == currentColor)) count++;
        if ((field[0][2] == currentColor && field[3][5] == currentColor) || (field[3][5] == currentColor && field[6][2] == currentColor)) count++;

        return count;
    }

    private int evaluate(boolean maximizing) {
        //TODO: Is it evaluated correctly, optimize evaluation?
        int selfEval = getCountOfStonesFor(this);
        selfEval += countOfMills(this) * 5;
        selfEval += countOfPossibleMills(this) * 15;

        int opponentEval = getCountOfStonesFor(opponent);
        opponentEval += countOfMills(opponent) * 5;
        opponentEval += countOfPossibleMills(opponent) * 15;

        return (selfEval - opponentEval) * (maximizing ? 1 : -1);
    }

    private void doMove(Player player, Move move) {
        if (move.getStartPoint() != null)
            field[move.getStartPoint().getX()][move.getStartPoint().getY()] = PlayerColor.NONE;
        field[move.getEndPoint().getX()][move.getEndPoint().getY()] = player.getColor();
    }

    private void undoMove(Player player, Move move) {
        if (move.getStartPoint() != null)
            field[move.getStartPoint().getX()][move.getStartPoint().getY()] = player.getColor();
        field[move.getEndPoint().getX()][move.getEndPoint().getY()] = PlayerColor.NONE;
    }

    private int max(int depth, int alpha, int beta) {
        ArrayList<Move> possibleMoves = generatePossibleMovesFor(this);
        if (depth == 0 || possibleMoves.size() == 0)
            return evaluate(true);
        int maxEval = alpha;
        for (Move move : possibleMoves) {
            doMove(this, move);
            int eval = min(depth - 1, maxEval, beta);
            undoMove(this, move);
            if (eval > maxEval) {
                maxEval = eval;
                if (depth == maxDepth)
                    finalMove = move;
                if (maxEval >= beta)
                    break;
            }
        }
        return maxEval;
    }

    private int min(int depth, int alpha, int beta) {
        ArrayList<Move> possibleMoves = generatePossibleMovesFor(opponent);
        if (depth == 0 || possibleMoves.size() == 0)
            return evaluate(false);
        int minEval = beta;
        for (Move move : possibleMoves) {
            doMove(opponent, move);
            int eval = max(depth - 1, alpha, minEval);
            undoMove(opponent, move);
            if (eval < minEval) {
                minEval = eval;
                if (minEval <= alpha)
                    break;
            }
        }
        return minEval;
    }
}
