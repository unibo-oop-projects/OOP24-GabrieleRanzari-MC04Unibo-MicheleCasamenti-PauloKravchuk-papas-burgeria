package it.unibo.papasburgeria;

import it.unibo.papasburgeria.model.Ingredient;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.IngredientImpl;

public class Main {

    public static void main(final String[] args) {
        // main stuff here
        //micky wus here

        Ingredient ingredient = new IngredientImpl(IngredientEnum.CHEESE);
        ingredient.setPlacementAccuracy(0.99);
        System.out.println("type: " + ingredient.getIngredientType() + " score: " + ingredient.getPlacementAccuracy());
    }
}
