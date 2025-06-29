package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link PantryModelImpl}.
 */
class PantryModelTest {
    private PantryModelImpl pantry;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        pantry = new PantryModelImpl();
    }

    /**
     * Tests {@link PantryModelImpl#PantryModelImpl()}.
     */
    @Test
    void testInitialState() {
        final Set<IngredientEnum> unlocked = pantry.getUnlockedIngredients();
        assertFalse(unlocked.isEmpty());
    }

    /**
     * Tests {@link PantryModelImpl#isIngredientUnlocked(IngredientEnum)}.
     */
    @Test
    void testIsIngredientUnlocked() {
        for (final IngredientEnum ingredient : pantry.getUnlockedIngredients()) {
            assertTrue(pantry.isIngredientUnlocked(ingredient));
        }
    }

    /**
     * Tests {@link PantryModelImpl#unlockForDay(int)}.
     */
    @Test
    void testUnlockForDay() {
        final int initialSize = pantry.getUnlockedIngredients().size();
        pantry.unlockForDay(10);
        final int newSize = pantry.getUnlockedIngredients().size();
        assertTrue(newSize >= initialSize);
    }

    /**
     * Tests {@link PantryModelImpl#resetUnlocks()}.
     */
    @Test
    void testResetUnlocks() {
        pantry.unlockForDay(100);
        pantry.resetUnlocks();
        final Set<IngredientEnum> unlocked = pantry.getUnlockedIngredients();

        assertFalse(unlocked.isEmpty());

        for (final IngredientEnum ingredient : IngredientEnum.values()) {
            if (!unlocked.contains(ingredient)) {
                assertFalse(pantry.isIngredientUnlocked(ingredient));
            }
        }
    }
}
