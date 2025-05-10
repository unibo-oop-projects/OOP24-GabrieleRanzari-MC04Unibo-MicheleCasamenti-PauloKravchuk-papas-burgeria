package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;
import org.tinylog.Logger;

import java.util.Arrays;

/**
 * Class used for managing hamburgers' assembling instructions and max time.
 */
public class OrderImpl implements Order {
    private final Hamburger hamburger;
    private final float maxTime;

    /**
     * Simple constructor. Ramdomly generates an order.
     */
    public OrderImpl() {
        hamburger = HamburgerImpl.generateRandomHamburger(Arrays.asList(IngredientEnum.values()));
        maxTime = (float) 10.0;
    }

    /**
     * @return the order's hamburger.
     */
    @Override
    public Hamburger getHamburger() {
        Logger.debug(hamburger);
        return new HamburgerImpl(); //this.hamburger needs to be cloned, if you need to change it make order methods;
    }

    /**
     * @return the order's maximum time.
     */
    @Override
    public float getMaxTime() {
        return maxTime;
    }

}
