package it.unibo.papasburgeria.model.api;

/**
 * Represents a patty with its top and bottom cook level and if it is flipped.
 */
public interface Patty {
    /**
     * Flips the patty changing the boolean value.
     */
    void flip();

    /**
     * @return true if the patty is flipped.
     */
    boolean isFlipped();

    /**
     * @return the top cook level.
     */
    double getTopCookLevel();

    /**
     * @param cookLevel the top cook level.
     */
    void setTopCookLevel(double cookLevel);

    /**
     * @return the bottom cook level.
     */
    double getBottomCookLevel();

    /**
     * @param cookLevel the bottom cook level.
     */
    void setBottomCookLevel(double cookLevel);
}
