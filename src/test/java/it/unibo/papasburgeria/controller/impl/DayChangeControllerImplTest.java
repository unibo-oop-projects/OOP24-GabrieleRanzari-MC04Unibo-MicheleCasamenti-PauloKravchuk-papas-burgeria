package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static it.unibo.papasburgeria.model.impl.UnlockSchedule.UNLOCK_SCHEDULE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link DayChangeControllerImpl}.
 */
class DayChangeControllerImplTest {
    private GameModel model;
    private DayChangeControllerImpl controller;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        controller = new DayChangeControllerImpl(model);
    }

    /**
     * Tests {@link DayChangeControllerImpl#getCurrentDayNumber()}.
     */
    @Test
    void testGetCurrentDayNumber() {
        assertEquals(model.getCurrentDay(), controller.getCurrentDayNumber());
        model.nextDay();
        assertEquals(model.getCurrentDay(), controller.getCurrentDayNumber());
    }

    /**
     * Tests {@link DayChangeControllerImpl#getIngredientsUnlockedToday()}.
     */
    @Test
    void testGetIngredientsUnlockedToday() {
        DaysEnum today = DaysEnum.getDay(model.getCurrentDay());
        Set<IngredientEnum> unlockedIngredients = UNLOCK_SCHEDULE.get(today);

        List<IngredientEnum> unlocked = controller.getIngredientsUnlockedToday();

        assertTrue(unlocked.containsAll(unlockedIngredients));
        assertEquals(unlockedIngredients.size(), unlocked.size());

        model.nextDay();
        today = DaysEnum.getDay(model.getCurrentDay());
        unlockedIngredients = UNLOCK_SCHEDULE.get(today);

        unlocked = controller.getIngredientsUnlockedToday();
        assertTrue(unlocked.containsAll(unlockedIngredients));
        assertEquals(unlockedIngredients.size(), unlocked.size());
    }
}
