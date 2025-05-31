package it.unibo.papasburgeria.model.api;

/**
 * Manages the appearance and disappearance of customers.
 */
public interface CustomerManager {

    /**
     * @return first customer of register line.
     */
    Customer popCustomerRegisterLine();

    /**
     * @param customer Customer added to end of register line.
     */
    void pushCustomerRegisterLine(Customer customer);

    /**
     * @param customer Customer added to end of register line.
     */
    void removeCustomerRegisterLine(Customer customer);

    /**
     * @return first customer of wait line.
     */
    Customer popCustomerWaitLine();

    /**
     * @param customer Customer added to end of wait line.
     */
    void pushCustomerWaitLine(Customer customer);

    /**
     * @param customer Customer removed from wait line.
     */
    void removeCustomerWaitLine(Customer customer);

    /**
     * Empties the Register line.
     */
    void clearRegisterLine();

    /**
     * Empties the Wait line.
     */
    void clearWaitLine();
}
