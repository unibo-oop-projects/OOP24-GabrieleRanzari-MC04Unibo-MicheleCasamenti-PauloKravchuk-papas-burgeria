package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.CustomerModel;
import it.unibo.papasburgeria.model.api.OrderModel;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of Customer.
 *
 * <p>
 * See {@link CustomerModel} for interface details.
 */
public class CustomerModelImpl implements CustomerModel {
    /**
     * How many types of skin are present.
     */
    public static final int EXISTING_SKIN_TYPES = 9;
    private final OrderModel order;
    /*just a 0-9 value used to indicate the customer's appearance */
    private final int skinType;
    private boolean inRegisterLine;
    private boolean inWaitLine;

    /**
     * Constructs a customer and generates a random Hamburger.
     *
     * @param availableIngredients list containing all available ingredients
     * @param orderNumber          the order number
     */
    public CustomerModelImpl(final List<IngredientEnum> availableIngredients, final int orderNumber) {
        order = new OrderModelImpl(availableIngredients, orderNumber);
        skinType = ThreadLocalRandom.current().nextInt(EXISTING_SKIN_TYPES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderModel getOrder() {
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
     * @return a customer's order and type.
     */
    @Override
    public String toString() {
        return "[Customer: [ " + order.toString() + "] ]";
    }
}
