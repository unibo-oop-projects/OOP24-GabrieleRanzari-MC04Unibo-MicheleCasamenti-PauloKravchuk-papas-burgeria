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
    public static final int MIN_INGREDIENTS = 2;
    public static final int MAX_INGREDIENTS = 10;

    private final List<Ingredient> ingredientList;

    /**
     * create empty hamburger.
     */
    public HamburgerImpl() {
        ingredientList = new ArrayList<>();
    }

    /**
     * Creates a burger given the list of ingredients.
     *
     * @param ingredientList the list of ingredients.
     */
    public HamburgerImpl(final List<Ingredient> ingredientList) {
        this.ingredientList = new ArrayList<>();
        for (final Ingredient ingredient : ingredientList) {
            addIngredient(ingredient);
        }
    }

    /**
     * @param availableIngredients list of the ingredients which can be contained in the hamburger
     * @return a randomly generated Hamburger
     */
    public static Hamburger generateRandomHamburger(final List<IngredientEnum> availableIngredients) {
        final Hamburger hamburger = new HamburgerImpl();
        final List<IngredientEnum> currentIngredients = new ArrayList<>();
        currentIngredients.addAll(availableIngredients);
        currentIngredients.remove(IngredientEnum.TOP_BUN);
        currentIngredients.remove(IngredientEnum.BOTTOM_BUN);
        hamburger.addIngredient(new IngredientImpl(IngredientEnum.BOTTOM_BUN));

        if (!currentIngredients.isEmpty()) {
            final int ingredientNumber = (int) ((Math.random() * (MAX_INGREDIENTS - MIN_INGREDIENTS)) + MIN_INGREDIENTS);

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

        hamburger.addIngredient(new IngredientImpl(IngredientEnum.TOP_BUN));
        return hamburger;
    }

    /**
     * @return true if the ingredient was added successfully. false otherwise.
     */
    @Override
    public final boolean addIngredient(final Ingredient ingredient) {
        if (ingredientList.isEmpty() && ingredient.getIngredientType() != IngredientEnum.BOTTOM_BUN) {
            return false;
        }

        if (!ingredientList.isEmpty()
                && ingredientList.getLast().getIngredientType().equals(IngredientEnum.TOP_BUN)) {
            return false;
        }

        ingredientList.add(ingredient);
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean removeLastIngredient() {
        if (!ingredientList.isEmpty()) {
            ingredientList.removeLast();
            return true;
        }
        return false;
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
