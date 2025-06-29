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
     * @return the customer's skin type.
     */
    int getSkinType();

    /**
     * @return true if customer in regiser line.
     */
    boolean isInRegisterLine();

    /**
     * @param setFlag if the customer is in registerLine
     */
    void setInRegisterLine(boolean setFlag);

    /**
     * @return true if customer in wait line.
     */
    boolean isInWaitLine();

    /**
     * @param setFlag if the customer is in WaitLine
     */
    void setInWaitLine(boolean setFlag);

    /**
     * @return true if customer has walked in.
     */
    boolean hasWalkedIn();

    /**
     * @param setFlag if the customer has walked in
     */
    void setWalkedIn(boolean setFlag);
}
