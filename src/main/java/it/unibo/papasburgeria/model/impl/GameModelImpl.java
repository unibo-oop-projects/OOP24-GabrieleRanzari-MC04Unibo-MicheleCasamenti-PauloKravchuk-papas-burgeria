package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.unibo.papasburgeria.model.api.GameModel;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;

/**
 * @inheritDoc
 */
@Singleton
public class GameModelImpl implements GameModel {
    public static final int START_DAY = FIRST_DAY.ordinal();
    private static final int MAX_DAYS = Integer.MAX_VALUE;

    private int currentDay;
    private int cookLevelPerSecond;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    @Inject
    public GameModelImpl() {
        this.currentDay = START_DAY;
        this.cookLevelPerSecond = 2;
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
    public int getCookLevelPerSecond() {
        return cookLevelPerSecond;
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setCookLevelPerSecond(final int newCookLevelPerSecond) {
        cookLevelPerSecond = newCookLevelPerSecond;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        this.currentDay = START_DAY;
    }

    /**
     * @return a string containing the current day.
     */
    @Override
    public String toString() {
        return "[currentDay=" + currentDay + "]";
    }
}
