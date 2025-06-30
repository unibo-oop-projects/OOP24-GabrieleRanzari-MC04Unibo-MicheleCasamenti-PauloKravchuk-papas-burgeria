package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.IngredientEnum;

/**
 * Interface used for creating Ingredients in the game.
 */
public interface IngredientModel {

    /**
     * Returns the type of this ingredient.
     *
     * @return the type of ingredient
     */
    IngredientEnum getIngredientType();

    /**
     * Return the placement accuracy of this ingredient in a scale value.
     *
     * @return the placement accuracy
     */
    double getPlacementAccuracy();

    /**
     * Sets a new placement accuracy for this ingredient.
     *
     * @param accuracy the new placement accuracy
     */
    void setPlacementAccuracy(double accuracy);

}
