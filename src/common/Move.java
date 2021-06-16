package src.common;

public interface Move {
    /**
     * Get the point where the move started
     *
     * @return null if there is no starting point, or the {@link Point} where the move started
     */
    Point getStartPoint();

    /**
     * Get the point where the move ended
     *
     * @return {@link Point} where the move ended
     */
    Point getEndPoint();
}
