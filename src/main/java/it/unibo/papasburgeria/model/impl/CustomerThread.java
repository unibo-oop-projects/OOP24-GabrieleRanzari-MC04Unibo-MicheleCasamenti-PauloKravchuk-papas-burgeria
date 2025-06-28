package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.RegisterModel;

import org.tinylog.Logger;

import java.util.List;

/**
 * defines a thread which adds the customers to the first line periodically.
 */
class CustomerThread extends Thread {
    private final Long intervalMilliSeconds;
    private final int customerAmount;
    private final RegisterModel model;
    private final List<IngredientEnum> unlockedIngredients;

    /**
     * @param delay                 rate at which customers come to the shop
     * @param customerAmount        amount of customer spawned by the thread
     * @param unlockedIngredients   available ingredients used for orders
     * @param model                 manages the lines whith customers
     */
    CustomerThread(final int delay, final int customerAmount,
                   final List<IngredientEnum> unlockedIngredients, final RegisterModel model) {
        this.intervalMilliSeconds = 1000L * delay;
        this.customerAmount = customerAmount;
        this.unlockedIngredients = unlockedIngredients;
        this.model = model;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        Logger.debug("Customer thread started");
        int generatedCustomers = 0;
        while (!currentThread().isInterrupted()) {
            try {
                if (generatedCustomers >= customerAmount) {
                    interrupt();
                } else {
                    model.pushCustomerRegisterLine(new CustomerImpl(unlockedIngredients, generatedCustomers + 1));
                    generatedCustomers++;
                    sleep(intervalMilliSeconds);
                }
            } catch (final InterruptedException e) {
                interrupt();
            }
        }
        Logger.debug("Customer thread terminated");
    }
}
