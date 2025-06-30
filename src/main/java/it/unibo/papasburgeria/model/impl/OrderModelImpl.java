package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;

import java.util.List;

/**
 * Implementation of Order.
 *
 * <p>
 * See {@link OrderModel} for interface details.
 */
public class OrderModelImpl implements OrderModel {
    private final int orderNumber;
    private final HamburgerModel hamburger;

    /**
     * Costructor that generates an order with a given Hamburger.
     *
     * @param hamburger   hamburger added to order.
     * @param orderNumber the order number.
     */
    public OrderModelImpl(final HamburgerModel hamburger, final int orderNumber) {
        this.hamburger = hamburger.copyOf();
        this.orderNumber = orderNumber;
    }

    /**
     * Costructor that generates an order with a random Hamburger.
     *
     * @param availableIngredients possible ingredients used to generate a random hamburger.
     * @param orderNumber          the order number.
     */
    public OrderModelImpl(final List<IngredientEnum> availableIngredients, final int orderNumber) {
        this.orderNumber = orderNumber;
        hamburger = HamburgerModelImpl.generateRandomHamburger(availableIngredients);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel getHamburger() {
        return hamburger.copyOf();
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
    public OrderModel copyOf() {
        return new OrderModelImpl(hamburger.copyOf(), orderNumber);
    }

    /**
     * @return order and type.
     */
    @Override
    public String toString() {
        return "[Order: [ " + hamburger.toString() + "] ]";
    }
}
