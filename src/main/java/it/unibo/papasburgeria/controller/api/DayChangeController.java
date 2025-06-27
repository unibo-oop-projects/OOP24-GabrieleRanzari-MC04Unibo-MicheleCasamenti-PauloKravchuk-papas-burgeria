package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.IngredientEnum;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the day change scene.
 */
public interface DayChangeController {
    /**
     * Returns the list of ingredient types unlocked today.
     *
     * @return list of ingredient types
     */
    List<IngredientEnum> getIngredientsUnlockedToday();

    /**
     * Returns the current day number.
     *
     * @return the day number
     */
    int getCurrentDayNumber();
}
