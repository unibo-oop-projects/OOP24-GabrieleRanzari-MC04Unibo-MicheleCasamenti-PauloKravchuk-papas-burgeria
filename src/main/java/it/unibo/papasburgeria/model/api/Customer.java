package it.unibo.papasburgeria.model.api;

/**
 * Interface used for creating Customers in the game. They generate orders and evaluate Burgers.
 */
@FunctionalInterface
public interface Customer {

    /**
     * @return the customer's order.
     */
    Order getOrder();
}
