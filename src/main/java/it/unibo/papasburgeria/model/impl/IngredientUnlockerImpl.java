package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.IngredientUnlocker;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.EnumSet;
import java.util.Collections;

/**
 * @inheritDoc
 */
public class IngredientUnlockerImpl implements IngredientUnlocker {
    private final Map<DaysEnum, Set<IngredientEnum>> unlockSchedule;
    private final Set<IngredientEnum> unlockedIngredients;

    /**
     * Default constructor, creates the schedule for unlocking ingredients and initializes the set of unlocked ingredients.
     */
    public IngredientUnlockerImpl() {
        this.unlockSchedule = new TreeMap<>();
        this.unlockedIngredients = EnumSet.noneOf(IngredientEnum.class);

        // Ingredient unlocking schedule <day, ingredient>
        unlockSchedule.put(DaysEnum.FIRST_DAY, EnumSet.of(
                IngredientEnum.BOTTOMBUN,
                IngredientEnum.TOPBUN,
                IngredientEnum.MEAT,
                IngredientEnum.CHEESE,
                IngredientEnum.KETCHUP));
        unlockSchedule.put(DaysEnum.SECOND_DAY, EnumSet.of(
                IngredientEnum.LETTUCE,
                IngredientEnum.MAYO));
        unlockSchedule.put(DaysEnum.THIRD_DAY, EnumSet.of(
                IngredientEnum.TOMATO,
                IngredientEnum.MUSTARD));
        unlockSchedule.put(DaysEnum.FOURTH_DAY, EnumSet.of(
                IngredientEnum.PICKLE,
                IngredientEnum.BBQ));
        unlockSchedule.put(DaysEnum.FIFTH_DAY, EnumSet.of(
                IngredientEnum.ONION));

        resetUnlocks();
        unlockForDay(DayManagerImpl.START_DAY); // If the starting day is not the first day
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void unlockForDay(final int currentDay) {
        for (final Map.Entry<DaysEnum, Set<IngredientEnum>> entry : unlockSchedule.entrySet()) {
            final int dayNumber = entry.getKey().ordinal();
            if (dayNumber <= currentDay) {
                unlockedIngredients.addAll(entry.getValue());
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<IngredientEnum> getUnlockedIngredients() {
        return Collections.unmodifiableSet(unlockedIngredients);
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void resetUnlocks() {
        unlockedIngredients.clear();
        final Set<IngredientEnum> baseIngredients = unlockSchedule.get(DaysEnum.FIRST_DAY);
        if (baseIngredients != null) {
            unlockedIngredients.addAll(baseIngredients);
        }
    }

    /**
     * @return a string containing all the unlocked ingredients.
     */
    @Override
    public String toString() {
        return "IngredientUnlockerImpl [unlockedIngredients=" + unlockedIngredients + "]";
    }
}
