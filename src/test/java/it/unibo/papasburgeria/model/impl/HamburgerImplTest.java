package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link HamburgerImpl}.
 */
class HamburgerImplTest {
    private Hamburger hamburger;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        hamburger = new HamburgerImpl();
    }

    /**
     * Tests {@link HamburgerImpl#addIngredient(Ingredient)}.
     */
    @Test
    void testAddIngredient() {
        Ingredient ingredient = new IngredientImpl(IngredientEnum.LETTUCE);
        assertFalse(hamburger.addIngredient(ingredient));

        ingredient = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        assertTrue(hamburger.addIngredient(ingredient));

        final List<Ingredient> ingredients = hamburger.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals(ingredient, ingredients.getFirst());
    }

    /**
     * Tests {@link HamburgerImpl#removeLastIngredient()}.
     */
    @Test
    void testRemoveLastIngredient() {
        assertFalse(hamburger.removeLastIngredient());

        final Ingredient ingredient1 = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        final Ingredient ingredient2 = new IngredientImpl(IngredientEnum.TOMATO);
        hamburger.addIngredient(ingredient1);
        hamburger.addIngredient(ingredient2);

        assertTrue(hamburger.removeLastIngredient());

        final List<Ingredient> ingredients = hamburger.getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals(ingredient1, ingredients.getFirst());
    }

    /**
     * Tests {@link HamburgerImpl#copyOf()}.
     */
    @Test
    void testCopyOf() {
        final Ingredient ingredient = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        hamburger.addIngredient(ingredient);

        final Hamburger copy = hamburger.copyOf();
        assertNotSame(hamburger, copy);
        assertEquals(hamburger.getIngredients(), copy.getIngredients());

        copy.removeLastIngredient();
        assertEquals(1, hamburger.getIngredients().size());
        assertEquals(0, copy.getIngredients().size());
    }
}
