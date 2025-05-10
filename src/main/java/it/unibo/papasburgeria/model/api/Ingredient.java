package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.IngredientEnum;

/**
 * Interface used for creating Ingredients in the game.
 */
public interface Ingredient {

    /**
     * @return The type of ingredient
     */
    IngredientEnum getIngredientType();

    /**
     * @return a double value representing how accurately the ingredient was placed (in %)
     */
    double getPlacementAccuracy();

    /**
     * @param accuracy double value representing how accurately the ingredient was placed (in %)
     */
    void setPlacementAccuracy(double accuracy);
}
