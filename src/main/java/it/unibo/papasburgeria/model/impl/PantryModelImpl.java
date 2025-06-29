package it.unibo.papasburgeria.model.impl;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.PantryModel;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;
import static it.unibo.papasburgeria.model.impl.GameModelImpl.START_DAY;
import static it.unibo.papasburgeria.model.impl.UnlockSchedule.UNLOCK_SCHEDULE;

/**
 * Implementation of PantryModel.
 *
 * <p>
 * See {@link PantryModel} for interface details.
 */
@Singleton
public class PantryModelImpl implements PantryModel {
    private final Set<IngredientEnum> unlockedIngredients = EnumSet.noneOf(IngredientEnum.class);

    /**
     * Default constructor that initializes the set of unlocked ingredients.
     */
    public PantryModelImpl() {
        resetUnlocks();
        unlockForDay(START_DAY.getNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void unlockForDay(final int currentDay) {
        for (final Map.Entry<DaysEnum, Set<IngredientEnum>> entry : UNLOCK_SCHEDULE.entrySet()) {
            final int dayNumber = entry.getKey().getNumber();
            if (dayNumber <= currentDay) {
                unlockedIngredients.addAll(entry.getValue());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IngredientEnum> getUnlockedIngredients() {
        return Collections.unmodifiableSet(unlockedIngredients);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetUnlocks() {
        unlockedIngredients.clear();
        final Set<IngredientEnum> baseIngredients = UNLOCK_SCHEDULE.get(FIRST_DAY);
        if (baseIngredients != null) {
            unlockedIngredients.addAll(baseIngredients);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIngredientUnlocked(final IngredientEnum ingredientType) {
        return unlockedIngredients.contains(ingredientType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PantryModelImpl{"
                + "unlockedIngredients=" + unlockedIngredients
                + '}';
    }
}
