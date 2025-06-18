package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;

/**
 * Class for creating a simple ingredient.
 */
public class IngredientImpl implements Ingredient {
    public static final double MAX_RIGHT_ACCURACY = 1.0;
    public static final double MAX_LEFT_ACCURACY = -MAX_RIGHT_ACCURACY;
    public static final double PERFECT_ACCURACY = MAX_RIGHT_ACCURACY + MAX_LEFT_ACCURACY;
    public static final double ACCURACY_NOT_SET = Double.MAX_VALUE;

    private final IngredientEnum type;
    private double accuracy;

    /**
     * @param type type of ingredient created.
     */
    public IngredientImpl(final IngredientEnum type) {
        this.type = type;
        accuracy = ACCURACY_NOT_SET;
    }

    /**
     * @param type     the type of the ingredient.
     * @param accuracy range from -1.0 to 1.0 indicating how far it is from the centre.
     */
    public IngredientImpl(final IngredientEnum type, final double accuracy) {
        this.type = type;
        this.accuracy = accuracy;
    }

    /**
     * @return the type of the ingredient.
     */
    @Override
    public IngredientEnum getIngredientType() {
        return type;
    }

    /**
     * @return a value between 1.0 and 0.0 indicating how precisely the ingredient was placed.
     */
    @Override
    public double getPlacementAccuracy() {
        return accuracy;
    }

    /**
     * @param newAccuracy the new accuracy compared to the bottom bun.
     */
    @Override
    public void setPlacementAccuracy(final double newAccuracy) {
        this.accuracy = newAccuracy;
    }

    /**
     * @return a string containing the ingredient's type and placement accuracy.
     */
    @Override
    public String toString() {
        return "[ type:" + this.getIngredientType() + ", accuracy:" + this.getPlacementAccuracy() + " ]";
    }

}
