package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.Order;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of Customer.
 *
 * <p>
 * See {@link Customer} for interface details.
 */
public class CustomerImpl implements Customer {
    public static final int MAX_PAYMENT = 50;
    public static final int EXISTING_SKIN_TYPES = 9;
    private final Order order;
    /*just a 0-9 value used to indicate the customer's appearance */
    private final int skinType;
    private boolean inRegisterLine;
    private boolean inWaitLine;
    private boolean walkedIn;

    /**
     * @param availableIngredients list containing all available ingredients
     * @param orderNumber          the order number
     */
    public CustomerImpl(final List<IngredientEnum> availableIngredients, final int orderNumber) {
        order = new OrderImpl(availableIngredients, orderNumber);
        skinType = ThreadLocalRandom.current().nextInt(EXISTING_SKIN_TYPES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getOrder() {
        return order;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSkinType() {
        return skinType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInRegisterLine() {
        return inRegisterLine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInRegisterLine(final boolean setFlag) {
        this.inRegisterLine = setFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInWaitLine() {
        return inWaitLine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInWaitLine(final boolean setFlag) {
        this.inWaitLine = setFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWalkedIn() {
        return walkedIn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWalkedIn(final boolean setFlag) {
        this.walkedIn = setFlag;
    }

    /**
     * @return a customer's order and type.
     */
    @Override
    public String toString() {
        return "[Customer: [ " + order.toString() + "] ]";
    }
}
