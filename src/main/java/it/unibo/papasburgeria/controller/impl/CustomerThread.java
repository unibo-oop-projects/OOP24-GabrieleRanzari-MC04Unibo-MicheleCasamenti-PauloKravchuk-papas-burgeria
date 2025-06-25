package it.unibo.papasburgeria.controller.impl;

import java.util.List;

import org.tinylog.Logger;

import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.impl.CustomerImpl;

class CustomerThread extends Thread {
    private final Long intervalMilliSeconds;
    private final int customerAmount;
    private final CustomerController controller;
    private final List<IngredientEnum> unlockedIngredients;

    /**
     * @param delay rate at which customers come to the shop
     * @param customerAmount amount of customer spawned by the thread
     * @param unlockedIngredients available ingredients used for orders
     * @param controller manages the lines whith customers
     */
    CustomerThread(final int delay, final int customerAmount,
    final List<IngredientEnum> unlockedIngredients, final CustomerControllerImpl controller) {
        this.intervalMilliSeconds = 1000L * delay;
        this.customerAmount = customerAmount;
        this.unlockedIngredients = unlockedIngredients;
        this.controller = controller;
    }

    @Override
    public void run() {
        int generatedCustomers = 0;
        while (!currentThread().isInterrupted()) {
            try {
                if (generatedCustomers >= customerAmount) {
                    interrupt();
                } else {
                    controller.pushCustomerRegisterLine(new CustomerImpl(unlockedIngredients));
                    generatedCustomers++;
                    sleep(intervalMilliSeconds);
                }
            } catch (final InterruptedException e) {
                Logger.debug("sleep() did not work");
            }
        }
    }
}
