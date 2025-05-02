package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.IngredientUnlocker;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.EnumSet;
import java.util.Collections;

/**
 * Implementation of IngredientUnlocker.
 */
public class IngredientUnlockerImpl implements IngredientUnlocker {
    private static final int FIRST_DAY = 1;
    private static final int SECOND_DAY = 2;
    private static final int THIRD_DAY = 3;
    private static final int FOURTH_DAY = 4;
    private static final int FIFTH_DAY = 5;

    private final Map<Integer, Set<IngredientEnum>> unlockSchedule;
    private final Set<IngredientEnum> unlockedIngredients;

    /**
     * Default constructor, creates the schedule for unlocking ingredients and initializes the set of unlocked ingredients.
     */
    public IngredientUnlockerImpl() {
        this.unlockSchedule = new TreeMap<>();
        this.unlockedIngredients = new HashSet<>();

        // Unlock schedule <day, ingredient>
        unlockSchedule.put(FIRST_DAY, EnumSet.of(
                IngredientEnum.BOTTOMBUN,
                IngredientEnum.TOPBUN,
                IngredientEnum.MEAT,
                IngredientEnum.CHEESE,
                IngredientEnum.KETCHUP));
        unlockSchedule.put(SECOND_DAY, EnumSet.of(
                IngredientEnum.LETTUCE,
                IngredientEnum.MAYO));
        unlockSchedule.put(THIRD_DAY, EnumSet.of(
                IngredientEnum.TOMATO,
                IngredientEnum.MUSTARD));
        unlockSchedule.put(FOURTH_DAY, EnumSet.of(
                IngredientEnum.PICKLE,
                IngredientEnum.BBQ));
        unlockSchedule.put(FIFTH_DAY, EnumSet.of(
                IngredientEnum.ONION));

        // Initial unlocks (day 1)
        resetUnlocks();
    }

    /**
     * Unlocks ingredients appropriate to the current day.
     *
     * @param currentDay : current game day
     */
    @Override
    public void unlockForDay(final int currentDay) {
        for (final Map.Entry<Integer, Set<IngredientEnum>> entry : unlockSchedule.entrySet()) {
            if (entry.getKey() <= currentDay) {
                unlockedIngredients.addAll(entry.getValue());
            }
        }
    }

    /**
     * @return the set of all currently unlocked ingredient types.
     */
    @Override
    public Set<IngredientEnum> getUnlockedIngredients() {
        return Collections.unmodifiableSet(unlockedIngredients);
    }

    /**
     * Resets the unlock progress to only the base ingredients.
     */
    @Override
    public void resetUnlocks() {
        unlockedIngredients.clear();
        if (unlockSchedule.containsKey(FIRST_DAY)) {
            unlockedIngredients.addAll(unlockSchedule.get(FIRST_DAY));
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
