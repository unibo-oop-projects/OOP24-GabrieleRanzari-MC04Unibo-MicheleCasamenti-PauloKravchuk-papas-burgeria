package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;

public class IngredientImpl implements Ingredient {
    private final IngredientEnum type;
    private double accuracy;

    public IngredientImpl(final IngredientEnum type) {
        this.type = type;
        accuracy = 1.0;
    }

    @Override
    public IngredientEnum getIngredientType() {
        return type;
    }

    @Override
    public double getPlacementAccuracy() {
        return accuracy;
    }

    @Override
    public void setPlacementAccuracy(final double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "[ type:" + this.getIngredientType() + ", acc:" + this.getPlacementAccuracy() + " ]";
    }

}
