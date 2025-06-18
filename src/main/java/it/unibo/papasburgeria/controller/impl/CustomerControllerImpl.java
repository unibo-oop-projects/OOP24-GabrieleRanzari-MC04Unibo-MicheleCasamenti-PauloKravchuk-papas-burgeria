package it.unibo.papasburgeria.controller.impl;

import java.util.Deque;
import java.util.LinkedList;

import com.google.inject.Singleton;

import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.model.api.Customer;

/**
 * @inheritDoc
 */
@Singleton
public class CustomerControllerImpl implements CustomerController {
    private final Deque<Customer> registerLine = new LinkedList<>();
    private final Deque<Customer> waitLine = new LinkedList<>();

    /**
     * @inheritDoc
     */
    @Override
    public Customer popCustomerRegisterLine() {
        return registerLine.pop();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void pushCustomerRegisterLine(final Customer customer) {
        registerLine.push(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCustomerRegisterLine(final Customer customer) {
        registerLine.remove(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Customer popCustomerWaitLine() {
        return waitLine.pop();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void pushCustomerWaitLine(final Customer customer) {
        waitLine.push(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCustomerWaitLine(final Customer customer) {
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
        registerLine.clear();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void clearWaitLine() {
        waitLine.clear();
    }

    /**
     * @return a string containing all customer in each line.
     */
    @Override
    public String toString() {
        return "[CustomerManager: [registerLine=" + registerLine.toString() + "], [waitLine=" + waitLine.toString() + "] ]";
    }
}
