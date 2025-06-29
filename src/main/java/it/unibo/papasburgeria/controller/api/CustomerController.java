package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.Customer;

import java.util.List;

/**
 * Manages the appearance and disappearance of customers.
 */
public interface CustomerController {
    /**
     * Serves the customer and updates the line.
     *
     * @param customer the customer to serve.
     */
    void serveCustomer(Customer customer);

    /**
     * Takes the order from the selected customer.
     *
     * @param customer the customer to take the order from.
     */
    void takeOrderFromCustomer(Customer customer);

    /**
     * clears both lines for the day.
     */
    void clearAllCustomers();

    /**
     * starts the customer thread that periodically adds customers to register line.
     */
    void startClientThread();

    /**
     * kills the customer thread.
     */
    void stopClientThread();

    /**
     * gets the customer thread status.
     *
     * @return true if customer thread is alive
     */
    boolean isCustomerThreadStatus();

    /**
     * gets the register line.
     *
     * @return the customer line.
     */
    List<Customer> getRegisterLine();

    /**
     * @return the wait line.
     */
    List<Customer> getWaitLine();
}
