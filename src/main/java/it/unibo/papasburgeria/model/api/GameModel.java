package it.unibo.papasburgeria.model.api;

/**
 * Manages the game logic.
 */
public interface GameModel {
    /**
     * Advances by one day and unlocks the new ingredients.
     */
    void nextDay();

    /**
     * Resets the progress to the first day.
     */
    void reset();

    /**
     * @return the day manager.
     */
    DayManager getDayManager();

    /**
     * @return the ingredient unlocking manager.
     */
    IngredientUnlocker getIngredientUnlocker();
}
