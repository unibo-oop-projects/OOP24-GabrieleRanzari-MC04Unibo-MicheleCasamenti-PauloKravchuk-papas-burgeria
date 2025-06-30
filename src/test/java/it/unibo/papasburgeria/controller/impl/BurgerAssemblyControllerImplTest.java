package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.PantryModelImpl;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.model.impl.RegisterModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_X_POS_SCALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link BurgerAssemblyControllerImpl}.
 */
class BurgerAssemblyControllerImplTest {
    private BurgerAssemblyController controller;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        final GameModel gameModel = new GameModelImpl();
        final PantryModel pantryModel = new PantryModelImpl();
        final RegisterModel registerModel = new RegisterModelImpl();
        controller = new BurgerAssemblyControllerImpl(gameModel, pantryModel, registerModel);
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#addIngredient(Ingredient)}.
     */
    @Test
    void testAddIngredient() {
        final Ingredient ingredient = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        assertTrue(controller.addIngredient(ingredient));
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#removeLastIngredient()}.
     */
    @Test
    void testRemoveLastIngredient() {
        final Ingredient ingredient = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        controller.addIngredient(ingredient);
        controller.removeLastIngredient();
        assertTrue(controller.getHamburgerOnAssembly().getIngredients().isEmpty());
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#addCookedPatty(Patty)}
     * and {@link BurgerAssemblyControllerImpl#removeCookedPatty(Patty)}.
     */
    @Test
    void testAddAndRemoveCookedPatty() {
        final Patty patty = new PattyImpl();
        assertTrue(controller.addCookedPatty(patty));
        assertTrue(controller.getCookedPatties().contains(patty));

        controller.removeCookedPatty(patty);
        assertFalse(controller.getCookedPatties().contains(patty));
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#calculateAccuracy(double)}.
     */
    @Test
    void testCalculateAccuracy() {
        final double accuracy = controller.calculateAccuracy(HAMBURGER_X_POS_SCALE);
        assertEquals(0.0, accuracy);
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#changeIngredientAccuracy(Ingredient, double)}.
     */
    @Test
    void testChangeIngredientAccuracy() {
        final double accuracy = 0.3;

        Ingredient ingredient = new IngredientImpl(IngredientEnum.BOTTOM_BUN);
        controller.addIngredient(ingredient);
        controller.changeIngredientAccuracy(ingredient, accuracy);
        assertEquals(0.0, controller.getHamburgerOnAssembly().getIngredients().getFirst().getPlacementAccuracy());

        ingredient = new IngredientImpl(IngredientEnum.TOMATO);
        controller.addIngredient(ingredient);
        controller.changeIngredientAccuracy(ingredient, accuracy);
        assertEquals(accuracy, controller.getHamburgerOnAssembly().getIngredients().get(1).getPlacementAccuracy());
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#getUnlockedIngredients()}.
     */
    @Test
    void testGetUnlockedIngredients() {
        final List<IngredientEnum> unlockedIngredients = controller.getUnlockedIngredients();
        assertNotNull(unlockedIngredients);
        assertFalse(unlockedIngredients.isEmpty());
    }

    /**
     * Tests {@link BurgerAssemblyControllerImpl#isIngredientUnlocked}.
     */
    @Test
    void testIsIngredientUnlocked() {
        assertTrue(controller.isIngredientUnlocked(IngredientEnum.BOTTOM_BUN));
    }
}
