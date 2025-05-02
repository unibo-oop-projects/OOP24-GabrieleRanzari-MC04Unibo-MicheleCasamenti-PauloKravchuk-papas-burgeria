package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.IngredientEnum;

import java.util.Set;

/**
 * Manages the progressive unlocking of ingredients based on days.
 */
public interface IngredientUnlocker {
    /**
     * Unlocks ingredients appropriate to the current day.
     *
     * @param currentDay : current game day
     */
    void unlockForDay(int currentDay);

    /**
     * @return the set of all currently unlocked ingredient types.
     */
    Set<IngredientEnum> getUnlockedIngredients();

    /**
     * Resets the unlock progress to only the base ingredients.
     */
    void resetUnlocks();
}
