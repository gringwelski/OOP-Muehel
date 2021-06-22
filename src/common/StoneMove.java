package src.common;

import java.util.Objects;

public class StoneMove implements Move {

    private final Point startPoint;
    private final Point endPoint;

    public StoneMove(int startX, int startY, int endX, int endY) {
        this(new FieldPoint(startX, startY), new FieldPoint(endX, endY));
    }

    public StoneMove(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public Point getStartPoint() {
        return startPoint;
    }

    @Override
    public Point getEndPoint() {
        return endPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoneMove stoneMove = (StoneMove) o;
        return getStartPoint().equals(stoneMove.getStartPoint()) && getEndPoint().equals(stoneMove.getEndPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartPoint(), getEndPoint());
    }
}
