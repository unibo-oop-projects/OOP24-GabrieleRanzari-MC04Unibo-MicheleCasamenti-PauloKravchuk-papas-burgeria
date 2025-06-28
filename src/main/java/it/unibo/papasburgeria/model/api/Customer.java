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
     * @param madeHamburger        the baked hamburger.
     * @param placementTollerance  bonus for placement.
     * @param ingredientTollerance bonus for ingredients.
     * @return the coins.
     */
    int evaluateBurger(Hamburger madeHamburger, double placementTollerance, double ingredientTollerance);

    /**
     * @return the customer's skin type.
     */
    int getSkinType();

    /**
     * @return true if customer in regiser line.
     */
    boolean isInRegisterLine();

    /**
     * @return true if customer in wait line.
     */
    boolean isInWaitLine();

    /**
     * @return true if customer has walked in.
     */
    boolean hasWalkedIn();

    /**
     * @param setFlag if the customer is in registerLine
     */
    void setInRegisterLine(boolean setFlag);

    /**
     * @param setFlag if the customer is in WaitLine
     */
    void setInWaitLine(boolean setFlag);

    /**
     * @param setFlag if the customer has walked in
     */
    void setWalkedIn(boolean setFlag);
}
