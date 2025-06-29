package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static it.unibo.papasburgeria.model.IngredientEnum.BOTTOM_BUN;
import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.IngredientEnum.TOP_BUN;

/**
 * Implementation of Hamburger.
 *
 * <p>
 * See {@link Hamburger} for interface details.
 */
public class HamburgerImpl implements Hamburger {
    public static final int MIN_INGREDIENTS = 2;
    public static final int MAX_INGREDIENTS = 10;

    private final List<Ingredient> ingredientList;

    /**
     * Default constructor, creates an empty hamburger.
     */
    public HamburgerImpl() {
        ingredientList = new ArrayList<>();
    }

    /**
     * Creates a burger given the list of ingredients.
     *
     * @param ingredientList the list of ingredients
     */
    public HamburgerImpl(final List<Ingredient> ingredientList) {
        this.ingredientList = new ArrayList<>();
        for (final Ingredient ingredient : ingredientList) {
            addIngredient(ingredient);
        }
    }

    /**
     * Generates a random hamburger using the unlocked ingredients.
     *
     * @param availableIngredients the list of unlocked ingredient types
     * @return the randomly generated hamburger
     */
    public static Hamburger generateRandomHamburger(final List<IngredientEnum> availableIngredients) {
        final Hamburger hamburger = new HamburgerImpl();
        final List<IngredientEnum> currentIngredients = new ArrayList<>(availableIngredients);
        currentIngredients.remove(TOP_BUN);
        currentIngredients.remove(BOTTOM_BUN);
        hamburger.addIngredient(new IngredientImpl(BOTTOM_BUN));

        if (!currentIngredients.isEmpty()) {
            final int ingredientNumber = (int) ((Math.random() * (MAX_INGREDIENTS - MIN_INGREDIENTS)) + MIN_INGREDIENTS);

            for (int i = 0; i < ingredientNumber; i++) {
                Ingredient ingredient;
                do {
                    final IngredientEnum ingredientType =
                            currentIngredients.get((int) (Math.random() * currentIngredients.size()));
                    if (PATTY.equals(ingredientType)) {
                        ingredient = new PattyImpl();
                    } else {
                        ingredient = new IngredientImpl(ingredientType);
                    }
                } while (!hamburger.addIngredient(ingredient));
            }
        }

        hamburger.addIngredient(new IngredientImpl(TOP_BUN));
        return hamburger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addIngredient(final Ingredient ingredient) {
        if (ingredientList.isEmpty() && ingredient.getIngredientType() != BOTTOM_BUN) {
            Logger.debug("first ingredient is NOT a bun");
            return false;
        }

        if (!ingredientList.isEmpty() && ingredientList.getLast().getIngredientType().equals(TOP_BUN)) {
            return false;
        }

        if (ingredientList.isEmpty()) {
            ingredient.setPlacementAccuracy(IngredientImpl.PERFECT_ACCURACY);
        }

        ingredientList.add(ingredient);
        return true;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public List<Ingredient> getIngredients() {
        return List.copyOf(ingredientList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hamburger copyOf() {
        return new HamburgerImpl(ingredientList);
    }

    /**
     * {@inheritDoc}
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
