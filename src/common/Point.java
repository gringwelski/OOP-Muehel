package src.common;

/**
 * X and Y <b>do not</b> mean pixels in the image, rather they mean the specific point in the game field as
 * illustrated below<br>
 * <br>
 * Length: 6<br>
 * Height: 7<br>
 * <br>
 * <pre>
 *       (0,0)             (0,1)             (0,2)
 *         o --------------- o --------------- o
 *         |   (1,0)       (1|1)       (1,2)   |
 *         |     o --------- o ----------o     |
 *         |     |   (2,0) (2|1) (2,2)   |     |
 *         |     |     o --- o --- o     |     |
 *       (3|0) (3|1) (3|2)       (3|3) (3|4) (3|5)
 *         o --- o --- o           o --- o --- o
 *         |     |     |           |     |     |
 *         |     |     o --- o --- o     |     |
 *         |     |   (4,0) (4|1) (4,2)   |     |
 *         |     o --------- o ----------o     |
 *         |   (5,0)       (5|1)       (5,2)   |
 *         o --------------- o --------------- o
 *       (6,0)             (6,1)             (6,2)
 * </pre>
 */
public interface Point {
    /**
     * Get x coordinate of the current point
     *
     * @return x coordinate
     */
    int getX();

    /**
     * Get y coordinate of the current point
     *
     * @return y coordinate
     */
    int getY();
}
