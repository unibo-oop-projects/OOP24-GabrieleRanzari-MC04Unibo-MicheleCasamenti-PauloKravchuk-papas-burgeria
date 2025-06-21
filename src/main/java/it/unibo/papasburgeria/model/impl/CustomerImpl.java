package it.unibo.papasburgeria.model.impl;

import java.util.List;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.Order;

/**
 * Customers in the game. They generate orders and evaluate Burgers.
 */
public class CustomerImpl implements Customer {
    private final Order order;

    /**
     * @param availableIngredients list containing all available ingredients
     */
    public CustomerImpl(final List<IngredientEnum> availableIngredients) {
        order = new OrderImpl(availableIngredients);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order getOrder() {
        return order;
    }

    /**
     * @return a customer's order and type.
     */
    @Override
    public String toString() {
        return "[Customer: [ " + order.toString() + "] ]";
    }
}
