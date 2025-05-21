package it.unibo.papasburgeria.model.api;

/**
 * Manages the game state of varius data.
 */
public interface GameModel {

    /**
     * Advances the game by one day.
     *
     * @throws IllegalStateException if the game has reached the maximum day.
     */
    void nextDay();

    /**
     * @return the current day number.
     */
    int getCurrentDay();

    /**
     * Resets the progress to the first day.
     */
    void reset();
}
