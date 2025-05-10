package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;

/**
 * Class for creating a simple ingredient.
 */
public class IngredientImpl implements Ingredient {
    private final IngredientEnum type;
    private double accuracy;

    /**
     * @param type type of ingredient created.
     */
    public IngredientImpl(final IngredientEnum type) {
        this.type = type;
        accuracy = 1.0;
    }

    /**
     * @return type of the ingredient.
     */
    @Override
    public IngredientEnum getIngredientType() {
        return type;
    }

    /**
     * @return how accurately the ingredient was positioned in the hamburger.
     */
    @Override
    public double getPlacementAccuracy() {
        return accuracy;
    }

    /**
     * @param setAccuracy how accurately the ingredient was positioned in the hamburger.
     */
    @Override
    public void setPlacementAccuracy(final double setAccuracy) {
        this.accuracy = setAccuracy;
    }

    /**
     * @return a string containing the ingredient's type and placement accuracy.
     */
    @Override
    public String toString() {
        return "[ type:" + this.getIngredientType() + ", acc:" + this.getPlacementAccuracy() + " ]";
    }

}
