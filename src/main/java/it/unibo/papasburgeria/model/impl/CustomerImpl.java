package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Order;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Customers in the game. They generate orders and evaluate Burgers.
 */
public class CustomerImpl implements Customer {
    public static final int MAX_PAYMENT = 50;
    public static final int EXISTING_SKIN_TYPES = 9;
    private final Order order;
    private boolean inRegisterLine;
    private boolean inWaitLine;
    private boolean walkedIn;

    /*just a 0-9 value used to indicate the customer's appearance */
    private final int skinType;

    /**
     * @param availableIngredients list containing all available ingredients
     * @param orderNumber          the order number
     */
    public CustomerImpl(final List<IngredientEnum> availableIngredients, final int orderNumber) {
        order = new OrderImpl(availableIngredients, orderNumber);
        skinType = ThreadLocalRandom.current().nextInt(EXISTING_SKIN_TYPES);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order getOrder() {
        return order;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int evaluateBurger(final Hamburger madeHamburger, final double placementTollerance,
                              final double ingredientTollerance) {
        final List<Ingredient> list1 = this.order.getHamburger().getIngredients();
        final List<Ingredient> list2 = madeHamburger.getIngredients();

        /*if either of them are empty, give no money */
        if (list1 == null || list2 == null || list1.isEmpty() || list2.isEmpty()) {
            return 0;
        }

        final int minLength = Math.min(list1.size(), list2.size());
        int matchCount = 0;

        for (int i = 0; i < minLength; i++) {
            final Object a = list1.get(i);
            final Object b = list2.get(i);

            if (a == null || b == null) {
                continue;
            }

            if (a.equals(b) && a.getClass().equals(b.getClass())) {
                matchCount++;
            }
        }

        /* normalize by the max length to penalize extra/missing elements */
        final int maxLength = Math.max(list1.size(), list2.size());
        double similarityPercentage = (double) matchCount / maxLength + ingredientTollerance;
        if (similarityPercentage > 1.0) {
            similarityPercentage = 1.0;
        }

        double placementAccuracyTotal = 0.0;
        for (final Ingredient ingredient : list1) {
            placementAccuracyTotal += ingredient.getPlacementAccuracy();
        }
        /* calculates the placement accuracy (1 - (averageAccuracy)) */
        double placementPercentage = 1.0 - (placementAccuracyTotal / list1.size()) + placementTollerance;
        if (placementPercentage > 1.0) {
            placementPercentage = 1.0;
        }

        /* calculates the difficulty percentage (size/maxsize) */
        final double difficultyPercentage = (double) list1.size() / HamburgerImpl.MAX_INGREDIENTS;

        /*
         * calculates the amount of money to award the player
         * MAX * difficulty% * similarity% * placement%
         */
        return (int) (MAX_PAYMENT * difficultyPercentage * similarityPercentage * placementPercentage);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getSkinType() {
        return skinType;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isInRegisterLine() {
        return inRegisterLine;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isInWaitLine() {
        return inWaitLine;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasWalkedIn() {
        return walkedIn;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setInRegisterLine(final boolean setFlag) {
        this.inRegisterLine = setFlag;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setInWaitLine(final boolean setFlag) {
        this.inWaitLine = setFlag;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setWalkedIn(final boolean setFlag) {
        this.walkedIn = setFlag;
    }

    /**
     * @return a customer's order and type.
     */
    @Override
    public String toString() {
        return "[Customer: [ " + order.toString() + "] ]";
    }
}
