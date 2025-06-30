package it.unibo.papasburgeria.model.impl;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.CustomerModel;
import it.unibo.papasburgeria.model.api.RegisterModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of RegisterModel.
 *
 * <p>
 * See {@link RegisterModel} for interface details.
 */
@Singleton
public class RegisterModelImpl implements RegisterModel {
    private final List<CustomerModel> registerLine;
    private final List<CustomerModel> waitLine;
    private CustomerThread customerThread;

    /**
     * Constructs the register model.
     */
    public RegisterModelImpl() {
        this.registerLine = new LinkedList<>();
        this.waitLine = new LinkedList<>();
        this.customerThread = new CustomerThread(0, -1, null, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startCustomerThread(final int delay, final int customerAmount,
                                    final List<IngredientEnum> availableingredients) {
        killCustomerThread();
        customerThread = new CustomerThread(delay, customerAmount, availableingredients, this);
        customerThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void killCustomerThread() {
        if (customerThread.isAlive()) {
            customerThread.interrupt();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomerRegisterLine(final CustomerModel customer) {
        customer.setInRegisterLine(true);
        registerLine.add(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCustomerRegisterLine(final CustomerModel customer) {
        customer.setInRegisterLine(false);
        registerLine.remove(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomerWaitLine(final CustomerModel customer) {
        customer.setInWaitLine(true);
        waitLine.add(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCustomerWaitLine(final CustomerModel customer) {
        waitLine.remove(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearLines() {
        clearRegisterLine();
        clearWaitLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearRegisterLine() {
        for (final CustomerModel customer : registerLine) {
            customer.setInRegisterLine(false);
        }
        registerLine.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearWaitLine() {
        for (final CustomerModel customer : waitLine) {
            customer.setInWaitLine(false);
        }
        waitLine.clear();
    }

    /**
     * @return the register line
     */
    @Override
    public List<CustomerModel> getRegisterLine() {
        return List.copyOf(registerLine);
    }

    /**
     * @return the wait line
     */
    @Override
    public List<CustomerModel> getWaitLine() {
        return List.copyOf(waitLine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[CustomerManager: [registerLine=" + registerLine.toString() + "], [waitLine=" + waitLine.toString() + "] ]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCustomerThreadStatus() {
        return customerThread.isAlive();
    }
}
