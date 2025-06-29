package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.IngredientEnum;

import java.util.Set;

/**
 * Manages the progressive unlocking of ingredients based on days.
 */
public interface PantryModel {
    /**
     * Unlocks ingredients appropriate to the current day.
     *
     * @param currentDay current game day number
     */
    void unlockForDay(int currentDay);

    /**
     * Returns the set of ingredient types that are currently unlocked.
     *
     * @return the set unlocked ingredient types
     */
    Set<IngredientEnum> getUnlockedIngredients();

    /**
     * Resets the unlock progress to only the base ingredients.
     */
    void resetUnlocks();

    /**
     * Returns whether the ingredient is unlocked.
     *
     * @param ingredientType the ingredient
     * @return true if the ingredient is unlocked, false otherwise
     */
    boolean isIngredientUnlocked(IngredientEnum ingredientType);
}
