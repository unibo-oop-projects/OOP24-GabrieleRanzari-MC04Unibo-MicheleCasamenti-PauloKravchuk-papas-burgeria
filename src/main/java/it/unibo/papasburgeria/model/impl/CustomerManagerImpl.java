package it.unibo.papasburgeria.model.impl;

import java.util.LinkedList;
import java.util.List;

import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.CustomerManager;

/**
 * Manages the lines of customers in the shop.
 */
public class CustomerManagerImpl implements CustomerManager {
    private final List<Customer> registerLine = new LinkedList<>();
    private final List<Customer> waitLine = new LinkedList<>();

    /**
     * @inheritDoc
     */
    @Override
    public Customer popCustomerRegisterLine() {
        return ((LinkedList<Customer>) registerLine).pop();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void pushCustomerRegisterLine(final Customer customer) {
        ((LinkedList<Customer>) registerLine).push(customer);
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
        return ((LinkedList<Customer>) waitLine).pop();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void pushCustomerWaitLine(final Customer customer) {
        ((LinkedList<Customer>) waitLine).push(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCustomerWaitLine(final Customer customer) {
        waitLine.remove(customer);
    }

    /**
     * Clears both Register line and Wait line.
     */
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
}
