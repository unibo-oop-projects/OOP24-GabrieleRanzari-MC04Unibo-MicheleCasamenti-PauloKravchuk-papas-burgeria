package it.unibo.papasburgeria.model.api;

/**
 * Models a patty that can be flipped and cooked on both sides.
 */
public interface Patty {
    /**
     * Flips the patty.
     */
    void flip();

    /**
     * Returns whether the patty is flipped or not.
     *
     * @return true if the patty is flipped, false otherwise
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
