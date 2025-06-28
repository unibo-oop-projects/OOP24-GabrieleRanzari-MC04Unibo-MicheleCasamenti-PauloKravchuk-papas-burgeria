package it.unibo.papasburgeria.controller.api;

import java.util.List;

import it.unibo.papasburgeria.model.api.Customer;

/**
 * Manages the appearance and disappearance of customers.
 */
public interface CustomerController {
    /**
     * @param customer the customer to serve.
     */
    void serveCustomer(Customer customer);

    /**
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
     * @return the customer line.
     */
    List<Customer> getRegisterLine();

    /**
     * @return the wait line.
     */
    List<Customer> getWaitLine();
}
