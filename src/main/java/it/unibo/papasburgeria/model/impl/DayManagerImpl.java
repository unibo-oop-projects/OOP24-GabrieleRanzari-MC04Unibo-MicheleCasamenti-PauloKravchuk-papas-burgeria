package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.DayManager;

/**
 * Implementation of DayManager.
 */
public class DayManagerImpl implements DayManager {
    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int START_DAY = 1;

    private int currentDay;

    /**
     * Default constructor, initializes currentDay with the first day.
     */
    public DayManagerImpl() {
        this.currentDay = START_DAY;
    }

    /**
     * Advances the game by one day.
     *
     * @throws IllegalStateException if the game has reached the maximum day.
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
     * @return the current day number.
     */
    @Override
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * Resets the day counter at the starting day.
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
        return "DayManager [currentDay=" + currentDay + "]";
    }
}
