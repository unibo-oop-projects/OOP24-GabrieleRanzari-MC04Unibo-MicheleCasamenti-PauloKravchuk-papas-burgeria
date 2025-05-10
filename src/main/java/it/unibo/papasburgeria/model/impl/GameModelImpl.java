package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.api.DayManager;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.IngredientUnlocker;

/**
 * @inheritDoc
 */
@Singleton
public class GameModelImpl implements GameModel {
    private final DayManager dayManager;
    private final IngredientUnlocker ingredientUnlocker;

    /**
     * Secondary constructor.
     *
     * @param dayManager         the day manager.
     * @param ingredientUnlocker the ingredient unlocking manager.
     */
    @Inject
    public GameModelImpl(final DayManager dayManager, final IngredientUnlocker ingredientUnlocker) {
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
    public DayManager getDayManager() {
        return dayManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IngredientUnlocker getIngredientUnlocker() {
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
