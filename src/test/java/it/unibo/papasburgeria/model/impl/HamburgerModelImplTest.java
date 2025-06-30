package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.IngredientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link HamburgerModelImpl}.
 */
class HamburgerModelImplTest {
    private HamburgerModel hamburger;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        hamburger = new HamburgerModelImpl();
    }

    /**
     * Tests {@link HamburgerModelImpl#addIngredient(IngredientModel)}.
     */
    @Test
    void testAddIngredient() {
        IngredientModel ingredient = new IngredientModelImpl(IngredientEnum.LETTUCE);
        assertFalse(hamburger.addIngredient(ingredient));

        ingredient = new IngredientModelImpl(IngredientEnum.BOTTOM_BUN);
        assertTrue(hamburger.addIngredient(ingredient));

        final List<IngredientModel> ingredients = hamburger.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals(ingredient, ingredients.getFirst());
    }

    /**
     * Tests {@link HamburgerModelImpl#removeLastIngredient()}.
     */
    @Test
    void testRemoveLastIngredient() {
        assertFalse(hamburger.removeLastIngredient());

        final IngredientModel ingredient1 = new IngredientModelImpl(IngredientEnum.BOTTOM_BUN);
        final IngredientModel ingredient2 = new IngredientModelImpl(IngredientEnum.TOMATO);
        hamburger.addIngredient(ingredient1);
        hamburger.addIngredient(ingredient2);

        assertTrue(hamburger.removeLastIngredient());

        final List<IngredientModel> ingredients = hamburger.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals(ingredient1, ingredients.getFirst());
    }

    /**
     * Tests {@link HamburgerModelImpl#copyOf()}.
     */
    @Test
    void testCopyOf() {
        final IngredientModel ingredient = new IngredientModelImpl(IngredientEnum.BOTTOM_BUN);
        hamburger.addIngredient(ingredient);

        final HamburgerModel copy = hamburger.copyOf();
        assertNotSame(hamburger, copy);
        assertEquals(hamburger.getIngredients(), copy.getIngredients());

        copy.removeLastIngredient();
        assertEquals(1, hamburger.getIngredients().size());
        assertEquals(0, copy.getIngredients().size());
    }
}
