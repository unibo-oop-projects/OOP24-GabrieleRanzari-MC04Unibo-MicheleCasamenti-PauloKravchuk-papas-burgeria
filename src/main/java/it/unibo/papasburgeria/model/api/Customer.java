package it.unibo.papasburgeria.model.api;

/**
 * Interface used for creating Customers in the game. They generate orders and evaluate Burgers.
 */
public interface Customer {

    /**
     * @return the customer's order.
     */
    Order getOrder();

    /**
     * @param madeHamburger the baked hamburger.
     * @param placementTollerance bonus for placement.
     * @param ingredientTollerance bonus for ingredients.
     * @return the coins.
     */
    int evaluateBurger(Hamburger madeHamburger, float placementTollerance, float ingredientTollerance);
}
