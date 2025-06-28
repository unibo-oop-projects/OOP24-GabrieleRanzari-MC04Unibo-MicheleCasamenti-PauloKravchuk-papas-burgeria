package it.unibo.papasburgeria.controller.api;

import java.util.List;

import it.unibo.papasburgeria.model.CustomerDifficultyEnum;
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
     * clears both lines for the day.
     */
    void clearAllCustomers();

    /**
     * @param difficulty defines the difficulty level for the types of customers the thread generates
     */
    void startClientThread(CustomerDifficultyEnum difficulty);

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
