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
     * @return the cook level increased every second for the patties.
     */
    int getCookLevelPerSecond();

    /**
     * @param cookLevelPerSecond the cook level increased every second for the patties to set.
     */
    void setCookLevelPerSecond(int cookLevelPerSecond);

    /**
     * Resets the progress to the first day.
     */
    void reset();
}
