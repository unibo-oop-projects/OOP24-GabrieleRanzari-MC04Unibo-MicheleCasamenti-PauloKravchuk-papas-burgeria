package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

import java.util.List;

/**
 * Class used for managing hamburgers' assembling instructions and max time.
 */
public class OrderImpl implements Order {
    private final Hamburger hamburger;
    private final float maxTime;

    /**
     * @param availableIngredients possible ingredients used to generate a random hamburger
     */
    public OrderImpl(final List<IngredientEnum> availableIngredients) {
        hamburger = HamburgerImpl.generateRandomHamburger(availableIngredients);
        maxTime = (float) 10.0;
    }

    /**
     * @return the order's hamburger.
     */
    @Override
    public Hamburger getHamburger() {
        return hamburger.copyOf();
    }

    /**
     * @return the order's maximum time.
     */
    @Override
    public float getMaxTime() {
        return maxTime;
    }

    /**
     * @return order and type.
     */
    @Override
    public String toString() {
        return "[Order: [ " + hamburger.toString() + "] ]";
    }
}
