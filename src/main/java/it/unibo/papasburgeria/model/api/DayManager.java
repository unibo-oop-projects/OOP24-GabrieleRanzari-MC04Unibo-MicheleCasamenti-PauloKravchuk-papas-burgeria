package it.unibo.papasburgeria.model.api;

/**
 * Manages the progression of days in a game session.
 */
public interface DayManager {
    /**
     * Advances the game by one day and triggers unlock events.
     */
    void nextDay();

    /**
     * @return the current day number.
     */
    int getCurrentDay();

    /**
     * Resets the day counter.
     */
    void resetDays();
}