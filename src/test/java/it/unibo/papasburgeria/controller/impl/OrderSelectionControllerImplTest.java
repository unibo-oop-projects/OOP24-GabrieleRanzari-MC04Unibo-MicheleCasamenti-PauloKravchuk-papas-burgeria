package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.impl.CustomerImpl;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.PantryModelImpl;
import it.unibo.papasburgeria.model.impl.RegisterModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Test class for {@link OrderSelectionControllerImpl}.
 */
class OrderSelectionControllerImplTest {
    private GameModel gameModel;
    private RegisterModel registerModel;
    private OrderSelectionControllerImpl controller;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        gameModel = new GameModelImpl();
        registerModel = new RegisterModelImpl();
        final PantryModelImpl pantryModel = new PantryModelImpl();

        final Customer customer = new CustomerImpl(new ArrayList<>(pantryModel.getUnlockedIngredients()), 1);
        registerModel.addCustomerWaitLine(customer);

        controller = new OrderSelectionControllerImpl(gameModel, registerModel);
    }

    /**
     * Tests {@link OrderSelectionControllerImpl#getOrders()}.
     */
    @Test
    void testGetOrders() {
        final List<Order> orders = controller.getOrders();
        assertEquals(1, orders.size());
        assertEquals(1, orders.getFirst().getOrderNumber());
    }

    /**
     * Tests {@link OrderSelectionControllerImpl#getHamburger()}
     * and ensures it returns a copy.
     */
    @Test
    void testGetHamburgerReturnsCopy() {
        final Hamburger original = gameModel.getHamburgerOnAssembly();
        original.addIngredient(new IngredientImpl(IngredientEnum.BOTTOM_BUN));
        gameModel.setHamburgerOnAssembly(original);

        final Hamburger copy = controller.getHamburger();
        assertEquals(original.getIngredients(), copy.getIngredients());
        assertNotSame(original, copy);
    }

    /**
     * Tests {@link OrderSelectionControllerImpl#setSelectedOrder(Order)}.
     */
    @Test
    void testSetSelectedOrder() {
        final Order order = registerModel.getWaitLine().getFirst().getOrder();
        controller.setSelectedOrder(order);
        assertEquals(order.getOrderNumber(), gameModel.getSelectedOrder().getOrderNumber());
        assertEquals(order.getHamburger().getIngredients(), gameModel.getSelectedOrder().getHamburger().getIngredients());
    }

    /**
     * Tests {@link OrderSelectionControllerImpl#removeTopBun()}.
     */
    @Test
    void testRemoveTopBun() {
        final Hamburger hamburger = new HamburgerImpl();
        hamburger.addIngredient(new IngredientImpl(IngredientEnum.BOTTOM_BUN));
        hamburger.addIngredient(new IngredientImpl(IngredientEnum.TOP_BUN));
        gameModel.setHamburgerOnAssembly(hamburger);

        controller.removeTopBun();

        final List<Ingredient> ingredients = gameModel.getHamburgerOnAssembly().getIngredients();
        assertEquals(1, ingredients.size());
        assertEquals(IngredientEnum.BOTTOM_BUN, ingredients.get(0).getIngredientType());
    }
}
