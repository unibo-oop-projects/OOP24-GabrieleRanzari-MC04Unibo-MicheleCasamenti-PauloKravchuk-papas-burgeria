package it.unibo.papasburgeria.model.api;

/**
 * Manages the progression of days in a game session.
 */
public interface DayManager {

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
     * Resets the day counter at the starting day.
     */
    void resetDays();
}
