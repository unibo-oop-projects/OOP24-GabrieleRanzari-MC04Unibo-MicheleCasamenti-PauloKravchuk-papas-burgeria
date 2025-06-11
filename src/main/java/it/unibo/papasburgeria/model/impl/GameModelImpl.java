package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.unibo.papasburgeria.model.api.CustomerManager;
import it.unibo.papasburgeria.model.api.GameModel;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;

/**
 * @inheritDoc
 */
@Singleton
public class GameModelImpl implements GameModel {
    public static final int START_DAY = FIRST_DAY.ordinal();
    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private final CustomerManager customerManager; //TODO Move to relative controller.

    private int currentDay;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    @Inject
    public GameModelImpl() {
        this.currentDay = START_DAY;
        this.customerManager = new CustomerManagerImpl();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        if (currentDay == MAX_DAYS) {
            throw new IllegalStateException("Cannot advance beyond day " + MAX_DAYS);
        } else {
            currentDay++;
            customerManager.clearLines();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        this.currentDay = START_DAY;
        customerManager.clearLines();
    }

    /**
     * @return a string containing the current day and the customer manager.
     */
    @Override
    public String toString() {
        return "[currentDay=" + currentDay + " , " + customerManager + "]";
    }
}
