package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Test class for {@link IngredientImpl}.
 */
class IngredientImplTest {
    private IngredientImpl ingredient;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        ingredient = new IngredientImpl(IngredientEnum.LETTUCE);
    }

    /**
     * Tests {@link IngredientImpl#IngredientImpl(IngredientEnum)}.
     */
    @Test
    void testInitialState() {
        assertEquals(0.0, ingredient.getPlacementAccuracy());
    }

    /**
     * Tests {@link IngredientImpl#IngredientImpl(Ingredient)}.
     */
    @Test
    void testCopyConstructor() {
        ingredient.setPlacementAccuracy(0.5);
        final IngredientImpl copy = new IngredientImpl(ingredient);
        assertEquals(ingredient.getIngredientType(), copy.getIngredientType());
        assertEquals(0.5, copy.getPlacementAccuracy());
        assertNotSame(ingredient, copy);
    }

    /**
     * Tests {@link IngredientImpl#getIngredientType()}.
     */
    @Test
    void testGetIngredientType() {
        assertEquals(IngredientEnum.LETTUCE, ingredient.getIngredientType());
    }

    /**
     * Tests {@link IngredientImpl#setPlacementAccuracy(double)}
     * and {@link IngredientImpl#getPlacementAccuracy()}.
     */
    @Test
    void testSetAndGetPlacementAccuracy() {
        final double accuracy = 0.75;
        ingredient.setPlacementAccuracy(accuracy);
        assertEquals(accuracy, ingredient.getPlacementAccuracy());
    }

    /**
     * Tests {@link IngredientImpl#equals(Object)}
     * and {@link IngredientImpl#hashCode()}.
     */
    @Test
    void testEqualsAndHashCode() {
        final IngredientImpl sameType = new IngredientImpl(IngredientEnum.LETTUCE);
        final IngredientImpl differentType = new IngredientImpl(IngredientEnum.TOMATO);

        assertEquals(ingredient, sameType);
        assertEquals(ingredient.hashCode(), sameType.hashCode());
        assertNotEquals(ingredient, differentType);
    }
}
