package it.unibo.papasburgeria.model.api;

/**
 * Models a patty that can be flipped and cooked on both sides.
 */
public interface PattyModel {
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
     * Returns the top cook level.
     *
     * @return the top cook level
     */
    double getTopCookLevel();

    /**
     * Sets the top cook level.
     *
     * @param cookLevel the top cook level
     */
    void setTopCookLevel(double cookLevel);

    /**
     * Returns the bottom cook level.
     *
     * @return the bottom cook level
     */
    double getBottomCookLevel();

    /**
     * Sets the bottom cook level.
     *
     * @param cookLevel the bottom cook level
     */
    void setBottomCookLevel(double cookLevel);
}
