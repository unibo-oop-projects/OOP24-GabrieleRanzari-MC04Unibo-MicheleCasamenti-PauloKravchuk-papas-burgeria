package it.unibo.papasburgeria.model.api;

/**
 * Interface used for creating Customers in the game. They generate orders and evaluate Burgers.
 */
public interface CustomerModel {

    /**
     * Get the customer's order.
     *
     * @return the customer's order.
     */
    OrderModel getOrder();

    /**
     * Get the customer's skin type.
     *
     * @return the customer's skin type.
     */
    int getSkinType();

    /**
     * Get the customer registerLine status.
     *
     * @return true if customer in regiser line.
     */
    boolean isInRegisterLine();

    /**
     * Set the customer registerLine status.
     *
     * @param setFlag if the customer is in registerLine
     */
    void setInRegisterLine(boolean setFlag);

    /**
     * Get the customer waitLine status.
     *
     * @return true if customer in wait line.
     */
    boolean isInWaitLine();

    /**
     * Set the customer waitLine status.
     *
     * @param setFlag if the customer is in WaitLine
     */
    void setInWaitLine(boolean setFlag);
}
