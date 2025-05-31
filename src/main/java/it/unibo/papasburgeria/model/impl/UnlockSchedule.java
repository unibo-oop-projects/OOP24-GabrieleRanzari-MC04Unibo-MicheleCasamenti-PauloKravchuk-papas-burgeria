package it.unibo.papasburgeria.model.impl;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores the unlock schedule of ingredients.
 */
@Singleton
public final class UnlockSchedule {
    public static final Map<DaysEnum, Set<IngredientEnum>> UNLOCK_SCHEDULE;

    // Initializes the schedule.
    static {
        final Map<DaysEnum, Set<IngredientEnum>> map = new EnumMap<>(DaysEnum.class);
        map.put(DaysEnum.FIRST_DAY, EnumSet.of(
                IngredientEnum.BOTTOM_BUN,
                IngredientEnum.TOP_BUN,
                IngredientEnum.MEAT,
                IngredientEnum.CHEESE,
                IngredientEnum.KETCHUP
        ));
        map.put(DaysEnum.SECOND_DAY, EnumSet.of(
                IngredientEnum.LETTUCE,
                IngredientEnum.MAYO
        ));
        map.put(DaysEnum.THIRD_DAY, EnumSet.of(
                IngredientEnum.TOMATO,
                IngredientEnum.MUSTARD
        ));
        map.put(DaysEnum.FOURTH_DAY, EnumSet.of(
                IngredientEnum.PICKLE,
                IngredientEnum.BBQ
        ));
        map.put(DaysEnum.FIFTH_DAY, EnumSet.of(
                IngredientEnum.ONION
        ));
        UNLOCK_SCHEDULE = Collections.unmodifiableMap(map);
    }

    /**
     * Default constructor, private to prevent instantiation.
     */
    private UnlockSchedule() {
    }
}
