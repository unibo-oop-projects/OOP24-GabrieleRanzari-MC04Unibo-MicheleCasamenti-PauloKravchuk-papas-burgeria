package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.DayManager;
import java.lang.Integer;

public class DayManagerImpl implements DayManager {
    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int START_DAY = 1;

    private int currentDay;

    public DayManagerImpl() {
        this.currentDay = START_DAY;
    }

    @Override
    public void nextDay() {
        if (currentDay == MAX_DAYS) {
            throw new IllegalStateException("Cannot advance beyond day " + MAX_DAYS);
        } else {
            currentDay++;
        }
    }

    @Override
    public int getCurrentDay() {
        return currentDay;
    }

    @Override
    public void resetDays() {
        this.currentDay = START_DAY;
    }
}