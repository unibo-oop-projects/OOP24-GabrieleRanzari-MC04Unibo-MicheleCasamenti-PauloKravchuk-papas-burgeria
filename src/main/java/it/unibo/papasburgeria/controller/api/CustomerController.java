package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.CustomerDifficultyEnum;
import it.unibo.papasburgeria.model.api.Customer;

/**
 * Manages the appearance and disappearance of customers.
 */
public interface CustomerController {
    /**
     * @param customer the customer to serve
     */
    void serveCustomer(Customer customer);

    /**
     * 
     */
    void clearAllCustomers();

    /**
     * 
     */
    void startClientThread(CustomerDifficultyEnum difficulty);

    /**
     * 
     */
    void stopClientThread();
}
