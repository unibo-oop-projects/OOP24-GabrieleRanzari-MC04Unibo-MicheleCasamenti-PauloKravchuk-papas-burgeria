package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class HamburgerImpl implements Hamburger {

    private final List<Ingredient> ingredientList;

    public HamburgerImpl() {
        ingredientList = new ArrayList<>();
    }

    public HamburgerImpl(final List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public boolean addIngredient(final Ingredient ingredient){
        if (ingredientList.isEmpty() && ingredient.getIngredientType() != IngredientEnum.BOTTOMBUN) {
            return false;
        }

        if (!ingredientList.isEmpty() && ingredientList.get(ingredientList.size() - 1).getIngredientType().equals(IngredientEnum.TOPBUN)) {
            return false;
        }

        ingredientList.add(ingredient);
        return true;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredientList;
    }

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
