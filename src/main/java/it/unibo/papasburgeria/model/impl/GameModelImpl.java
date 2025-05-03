package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.GameModel;

/**
 * @inheritDoc
 */
public class GameModelImpl implements GameModel {
    private final DayManagerImpl dayManager;
    private final IngredientUnlockerImpl ingredientUnlocker;

    /**
     * Default constructor.
     */
    public GameModelImpl() {
        this.dayManager = new DayManagerImpl();
        this.ingredientUnlocker = new IngredientUnlockerImpl();
    }

    /**
     * Secondary constructor.
     *
     * @param dayManager the day manager.
     * @param ingredientUnlocker the ingredient unlocking manager.
     */
    public GameModelImpl(final DayManagerImpl dayManager, final IngredientUnlockerImpl ingredientUnlocker) {
        this.dayManager = dayManager;
        this.ingredientUnlocker = ingredientUnlocker;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        dayManager.nextDay();
        ingredientUnlocker.unlockForDay(dayManager.getCurrentDay());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        dayManager.resetDays();
        ingredientUnlocker.resetUnlocks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public DayManagerImpl getDayManager() {
        return dayManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IngredientUnlockerImpl getIngredientUnlocker() {
        return ingredientUnlocker;
    }

    /**
     * @return a string containing the day manager and ingredient unlocker.
     */
    @Override
    public String toString() {
        return "[GameModelImpl: " + dayManager.toString() + ", " + ingredientUnlocker.toString() + "]";
    }
}
