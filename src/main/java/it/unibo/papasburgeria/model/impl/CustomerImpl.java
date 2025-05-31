package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.Order;

/**
 * Customers in the game. They generate orders and evaluate Burgers.
 */
public class CustomerImpl implements Customer {
    private final Order order;

    CustomerImpl() {
        order = new OrderImpl();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order getOrder() {
        return order;
    }
}
