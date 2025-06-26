package it.unibo.papasburgeria.model.impl;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static it.unibo.papasburgeria.model.DaysEnum.FIFTH_DAY;
import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;
import static it.unibo.papasburgeria.model.DaysEnum.FOURTH_DAY;
import static it.unibo.papasburgeria.model.DaysEnum.SECOND_DAY;
import static it.unibo.papasburgeria.model.DaysEnum.THIRD_DAY;
import static it.unibo.papasburgeria.model.IngredientEnum.BBQ;
import static it.unibo.papasburgeria.model.IngredientEnum.BOTTOM_BUN;
import static it.unibo.papasburgeria.model.IngredientEnum.CHEESE;
import static it.unibo.papasburgeria.model.IngredientEnum.KETCHUP;
import static it.unibo.papasburgeria.model.IngredientEnum.LETTUCE;
import static it.unibo.papasburgeria.model.IngredientEnum.MAYO;
import static it.unibo.papasburgeria.model.IngredientEnum.MUSTARD;
import static it.unibo.papasburgeria.model.IngredientEnum.ONION;
import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.IngredientEnum.PICKLE;
import static it.unibo.papasburgeria.model.IngredientEnum.TOMATO;
import static it.unibo.papasburgeria.model.IngredientEnum.TOP_BUN;

/**
 * Stores the unlock schedule of ingredients.
 */
@Singleton
public final class UnlockSchedule {
    public static final Map<DaysEnum, Set<IngredientEnum>> UNLOCK_SCHEDULE;

    static {
        final Map<DaysEnum, Set<IngredientEnum>> map = new EnumMap<>(DaysEnum.class);
        map.put(FIRST_DAY, EnumSet.of(
                BOTTOM_BUN,
                TOP_BUN,
                PATTY,
                CHEESE,
                KETCHUP
        ));
        map.put(SECOND_DAY, EnumSet.of(
                LETTUCE,
                MAYO
        ));
        map.put(THIRD_DAY, EnumSet.of(
                TOMATO,
                MUSTARD
        ));
        map.put(FOURTH_DAY, EnumSet.of(
                PICKLE,
                BBQ
        ));
        map.put(FIFTH_DAY, EnumSet.of(
                ONION
        ));
        UNLOCK_SCHEDULE = Collections.unmodifiableMap(map);
    }

    /**
     * Default constructor, private to prevent instantiation.
     */
    private UnlockSchedule() {
    }
}
