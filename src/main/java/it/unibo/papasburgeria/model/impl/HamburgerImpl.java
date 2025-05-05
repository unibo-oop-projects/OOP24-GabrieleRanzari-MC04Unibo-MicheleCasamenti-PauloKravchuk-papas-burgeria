package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * class used for creating a simple hamburger.
 */
public class HamburgerImpl implements Hamburger {

    private final List<Ingredient> ingredientList;

    /**
     * create empty hamburger.
     */
    public HamburgerImpl() {
        ingredientList = new ArrayList<>();
    }

    /**
     * @return true if the ingredient was added successfully. false otherwise. 
     */
    @Override
    public boolean addIngredient(final Ingredient ingredient) {
        if (ingredientList.isEmpty() && ingredient.getIngredientType() != IngredientEnum.BOTTOMBUN) {
            return false;
        }

        if (!ingredientList.isEmpty()
        && ingredientList.get(ingredientList.size() - 1).getIngredientType().equals(IngredientEnum.TOPBUN)) {
            return false;
        }

        ingredientList.add(ingredient);
        return true;
    }

    /**
     * @return hamburger's list of ingredients.
     */
    @Override
    public List<Ingredient> getIngredients() {
        return List.copyOf(ingredientList);
    }

    /**
     * @return a string detailing all of hamburger's ingredient and their status.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Ingredient ingredient : ingredientList) {
            sb.append(ingredient.toString()).append(", ");
        }
        sb.append(']');

        return sb.toString();
    }

}
