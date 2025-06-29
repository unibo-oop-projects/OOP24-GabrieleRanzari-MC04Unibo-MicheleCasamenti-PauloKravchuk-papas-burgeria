package it.unibo.papasburgeria.model.impl;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.RegisterModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Models how the customers interact with the register.
 */
@Singleton
public class RegisterModelImpl implements RegisterModel {
    private final List<Customer> registerLine = new LinkedList<>();
    private final List<Customer> waitLine = new LinkedList<>();
    private CustomerThread customerThread = new CustomerThread(0, -1, null, this);

    /**
     * @inheritDoc
     */
    @Override
    public void startCustomerThread(final int delay, final int customerAmount,
                                    final List<IngredientEnum> availableingredients) {
        killCustomerThread();
        customerThread = new CustomerThread(delay, customerAmount, availableingredients, this);
        customerThread.start();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void killCustomerThread() {
        if (customerThread.isAlive()) {
            customerThread.interrupt();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addCustomerRegisterLine(final Customer customer) {
        customer.setInRegisterLine(true);
        registerLine.add(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCustomerRegisterLine(final Customer customer) {
        customer.setInRegisterLine(false);
        registerLine.remove(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addCustomerWaitLine(final Customer customer) {
        customer.setInWaitLine(true);
        waitLine.add(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCustomerWaitLine(final Customer customer) {
        customer.setWalkedIn(false);
        waitLine.remove(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void clearLines() {
        clearRegisterLine();
        clearWaitLine();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void clearRegisterLine() {
        for (final Customer customer : registerLine) {
            customer.setInRegisterLine(false);
        }
        registerLine.clear();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void clearWaitLine() {
        for (final Customer customer : waitLine) {
            customer.setInWaitLine(false);
        }
        waitLine.clear();
    }

    /**
     * @return the register line
     */
    @Override
    public List<Customer> getRegisterLine() {
        return List.copyOf(registerLine);
    }

    /**
     * @return the wait line
     */
    @Override
    public List<Customer> getWaitLine() {
        return List.copyOf(waitLine);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "[CustomerManager: [registerLine=" + registerLine.toString() + "], [waitLine=" + waitLine.toString() + "] ]";
    }
}
