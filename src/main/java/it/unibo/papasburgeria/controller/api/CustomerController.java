package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.CustomerModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;

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
    void serveCustomer(CustomerModel customer);

    /**
     * Takes the order from the selected customer.
     *
     * @param customer the customer to take the order from.
     */
    void takeOrderFromCustomer(CustomerModel customer);

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
    List<CustomerModel> getRegisterLine();

    /**
     * Gets the waitLine.
     *
     * @return the wait line.
     */
    List<CustomerModel> getWaitLine();

    /**
     * calculates the satisfaction percentage of the customer.
     *
     * @param startingHamburger the starting hamburger
     * @param madeHamburger     the hamburger cooked by the player
     * @return the satisfaction percentage
     */
    double calculateSatisfactionPercentage(HamburgerModel startingHamburger, HamburgerModel madeHamburger);

    /**
     * calculates the payment based on the satisfaction percentage.
     *
     * @param percentage the satisfaction percentage.
     * @return the total payment.
     */
    int calculatePayment(double percentage);

    /**
     * has a chance to generate a tip.
     *
     * @param payment the total payment without tip.
     * @return the tip.
     */
    int calculateTips(int payment);

    /**
     * add money to balance.
     *
     * @param payment money to be added to the balance
     */
    void addBalance(int payment);

    /**
     * gets the customer's balance.
     *
     * @return current balance.
     */
    int getBalance();
}
