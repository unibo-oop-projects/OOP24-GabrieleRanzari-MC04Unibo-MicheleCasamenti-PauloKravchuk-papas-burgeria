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
    public static final int MININGREDIENTS = 2;
    public static final int MAXINGREDIENTS = 10;

    private final List<Ingredient> ingredientList;

    /**
     * create empty hamburger.
     */
    public HamburgerImpl() {
        ingredientList = new ArrayList<>();
    }
    /**
     * @param availableIngredients list of the ingredients which can be contained in the hamburger
     * @return a randomly generated Hamburger 
     */

    public static Hamburger generateRandomHamburger(final List<IngredientEnum> availableIngredients) {
        final Hamburger hamburger = new HamburgerImpl();
        final List<IngredientEnum> currentIngredients = new ArrayList<>();
        currentIngredients.addAll(availableIngredients);
        currentIngredients.remove(IngredientEnum.TOPBUN);
        currentIngredients.remove(IngredientEnum.BOTTOMBUN);
        hamburger.addIngredient(new IngredientImpl(IngredientEnum.BOTTOMBUN));

        if (!currentIngredients.isEmpty()) {
           final int ingredientNumber = (int) ((Math.random() * (MAXINGREDIENTS - MININGREDIENTS)) + MININGREDIENTS);

            for (int i = 0; i < ingredientNumber; i++) {
                Ingredient ingredient;
                do {
                    final IngredientEnum ingredientType
                    = currentIngredients.get((int) (Math.random() * currentIngredients.size()));
                    if (IngredientEnum.MEAT.equals(ingredientType)) {
                        ingredient = new MeatImpl();
                    } else {
                        ingredient = new IngredientImpl(ingredientType);
                    }
                } while (hamburger.addIngredient(ingredient));
            }
        }

        hamburger.addIngredient(new IngredientImpl(IngredientEnum.TOPBUN));
        return hamburger;
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
