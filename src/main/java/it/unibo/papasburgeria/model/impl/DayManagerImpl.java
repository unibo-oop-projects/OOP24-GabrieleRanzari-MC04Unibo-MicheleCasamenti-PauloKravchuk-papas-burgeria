package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.api.DayManager;

/**
 * @inheritDoc
 */
public class DayManagerImpl implements DayManager {
    public static final int START_DAY = DaysEnum.FIRST_DAY.ordinal();
    private static final int MAX_DAYS = Integer.MAX_VALUE;

    private int currentDay;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    public DayManagerImpl() {
        this.currentDay = START_DAY;
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
    public void resetDays() {
        this.currentDay = START_DAY;
    }

    /**
     * @return a string containing the current day.
     */
    @Override
    public String toString() {
        return "[DayManager: [currentDay=" + currentDay + "]";
    }
}
