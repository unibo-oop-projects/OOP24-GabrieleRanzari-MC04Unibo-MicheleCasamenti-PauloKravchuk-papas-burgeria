package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.IngredientModel;
import it.unibo.papasburgeria.model.api.PattyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static it.unibo.papasburgeria.model.IngredientEnum.BOTTOM_BUN;
import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.IngredientEnum.TOP_BUN;

/**
 * Implementation of Hamburger.
 *
 * <p>
 * See {@link HamburgerModel} for interface details.
 */
public class HamburgerModelImpl implements HamburgerModel {
    /**
     * Defines the minimum number of ingredients of a hamburger.
     */
    public static final int MIN_INGREDIENTS = 2;
    /**
     * Defines the maximum number of ingredients of a hamburger.
     */
    public static final int MAX_INGREDIENTS = 10;

    private final List<IngredientModel> ingredientList;

    /**
     * Default constructor, creates an empty hamburger.
     */
    public HamburgerModelImpl() {
        ingredientList = new ArrayList<>();
    }

    /**
     * Creates a burger given the list of ingredients.
     *
     * @param ingredientList the list of ingredients
     */
    public HamburgerModelImpl(final List<IngredientModel> ingredientList) {
        this.ingredientList = new ArrayList<>();
        for (final IngredientModel ingredient : ingredientList) {
            addIngredient(ingredient);
        }
    }

    /**
     * Generates a random hamburger using the unlocked ingredients.
     *
     * @param availableIngredients the list of unlocked ingredient types
     * @return the randomly generated hamburger
     */
    public static HamburgerModel generateRandomHamburger(final List<IngredientEnum> availableIngredients) {
        final HamburgerModel hamburger = new HamburgerModelImpl();
        final List<IngredientEnum> currentIngredients = new ArrayList<>(availableIngredients);
        currentIngredients.remove(TOP_BUN);
        currentIngredients.remove(BOTTOM_BUN);
        hamburger.addIngredient(new IngredientModelImpl(BOTTOM_BUN));

        if (!currentIngredients.isEmpty()) {
            final int ingredientNumber = (int) ((Math.random() * (MAX_INGREDIENTS - MIN_INGREDIENTS)) + MIN_INGREDIENTS);

            for (int i = 0; i < ingredientNumber; i++) {
                IngredientModel ingredient;
                do {
                    final IngredientEnum ingredientType =
                            currentIngredients.get((int) (Math.random() * currentIngredients.size()));
                    if (PATTY.equals(ingredientType)) {
                        ingredient = new PattyModelImpl();
                        ((PattyModel) ingredient).setTopCookLevel(ThreadLocalRandom.current().nextDouble());
                        ((PattyModel) ingredient).setBottomCookLevel(ThreadLocalRandom.current().nextDouble());
                    } else {
                        ingredient = new IngredientModelImpl(ingredientType);
                    }
                } while (!hamburger.addIngredient(ingredient));
            }
        }

        hamburger.addIngredient(new IngredientModelImpl(TOP_BUN));
        return hamburger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addIngredient(final IngredientModel ingredient) {
        if (ingredientList.isEmpty() && ingredient.getIngredientType() != BOTTOM_BUN) {
            return false;
        }

        if (!ingredientList.isEmpty() && ingredientList.getLast().getIngredientType().equals(TOP_BUN)) {
            return false;
        }

        if (ingredientList.isEmpty()) {
            ingredient.setPlacementAccuracy(IngredientModelImpl.PERFECT_ACCURACY);
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
    public List<IngredientModel> getIngredients() {
        return List.copyOf(ingredientList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel copyOf() {
        return new HamburgerModelImpl(ingredientList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final IngredientModel ingredient : ingredientList) {
            sb.append(ingredient.toString()).append(", ");
        }
        sb.append(']');

        return sb.toString();
    }
}
