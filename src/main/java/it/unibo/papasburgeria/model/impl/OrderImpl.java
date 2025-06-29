package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

import java.util.List;

/**
 * Implementation of Order.
 *
 * <p>
 * See {@link Order} for interface details.
 */
public class OrderImpl implements Order {
    private final int orderNumber;
    private final Hamburger hamburger;
    private final float maxTime;

    /**
     * Default constructor, creates a new order given its number and a hamburger.
     *
     * @param hamburger   possible ingredients used to generate a random hamburger
     * @param orderNumber the order number
     */
    public OrderImpl(final Hamburger hamburger, final int orderNumber) {
        this.hamburger = hamburger.copyOf();
        this.orderNumber = orderNumber;
        maxTime = (float) 10.0;
    }

    /**
     * @param availableIngredients possible ingredients used to generate a random hamburger
     * @param orderNumber          the order number
     */
    public OrderImpl(final List<IngredientEnum> availableIngredients, final int orderNumber) {
        this.orderNumber = orderNumber;
        hamburger = HamburgerImpl.generateRandomHamburger(availableIngredients);
        maxTime = (float) 10.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hamburger getHamburger() {
        return hamburger.copyOf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMaxTime() {
        return maxTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order copyOf() {
        return new OrderImpl(hamburger.copyOf(), orderNumber);
    }

    /**
     * @return order and type.
     */
    @Override
    public String toString() {
        return "[Order: [ " + hamburger.toString() + "] ]";
    }
}
