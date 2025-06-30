package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.IngredientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Test class for {@link IngredientModelImpl}.
 */
class IngredientModelImplTest {
    private IngredientModelImpl ingredient;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        ingredient = new IngredientModelImpl(IngredientEnum.LETTUCE);
    }

    /**
     * Tests {@link IngredientModelImpl#IngredientImpl(IngredientEnum)}.
     */
    @Test
    void testInitialState() {
        assertEquals(0.0, ingredient.getPlacementAccuracy());
    }

    /**
     * Tests {@link IngredientModelImpl#IngredientImpl(IngredientModel)}.
     */
    @Test
    void testCopyConstructor() {
        ingredient.setPlacementAccuracy(0.5);
        final IngredientModelImpl copy = new IngredientModelImpl(ingredient);
        assertEquals(ingredient.getIngredientType(), copy.getIngredientType());
        assertEquals(0.5, copy.getPlacementAccuracy());
        assertNotSame(ingredient, copy);
    }

    /**
     * Tests {@link IngredientModelImpl#getIngredientType()}.
     */
    @Test
    void testGetIngredientType() {
        assertEquals(IngredientEnum.LETTUCE, ingredient.getIngredientType());
    }

    /**
     * Tests {@link IngredientModelImpl#setPlacementAccuracy(double)}
     * and {@link IngredientModelImpl#getPlacementAccuracy()}.
     */
    @Test
    void testSetAndGetPlacementAccuracy() {
        final double accuracy = 0.75;
        ingredient.setPlacementAccuracy(accuracy);
        assertEquals(accuracy, ingredient.getPlacementAccuracy());
    }

    /**
     * Tests {@link IngredientModelImpl#equals(Object)}
     * and {@link IngredientModelImpl#hashCode()}.
     */
    @Test
    void testEqualsAndHashCode() {
        final IngredientModelImpl sameType = new IngredientModelImpl(IngredientEnum.LETTUCE);
        final IngredientModelImpl differentType = new IngredientModelImpl(IngredientEnum.TOMATO);

        assertEquals(ingredient, sameType);
        assertEquals(ingredient.hashCode(), sameType.hashCode());
        assertNotEquals(ingredient, differentType);
    }
}
