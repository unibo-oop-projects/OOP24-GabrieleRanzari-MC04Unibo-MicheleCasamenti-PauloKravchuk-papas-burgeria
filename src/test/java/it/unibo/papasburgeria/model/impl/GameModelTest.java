package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;
import it.unibo.papasburgeria.model.api.Patty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link GameModelImpl}.
 */
class GameModelTest {
    private GameModel model;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
    }

    /**
     * Tests {@link GameModelImpl#GameModelImpl()}.
     */
    @Test
    void testInitialState() {
        assertEquals(0, model.getBalance());
        assertEquals(-1, model.getCurrentSaveSlot());
        assertEquals(model.getCurrentDay(), GameModelImpl.START_DAY.getNumber());
        assertNotNull(model.getHamburgerOnAssembly());
        assertEquals(0, model.getCookedPatties().size());
        assertNull(model.getSelectedOrder());
    }

    /**
     * Tests {@link GameModelImpl#setBalance(int)}
     * and {@link GameModelImpl#getBalance()}.
     */
    @Test
    void testSetAndGetBalance() {
        final int balance = 100;
        model.setBalance(balance);
        assertEquals(balance, model.getBalance());
    }

    /**
     * Tests {@link GameModelImpl#nextDay()}.
     */
    @Test
    void testNextDay() {
        final int currentDay = model.getCurrentDay();
        model.nextDay();
        assertEquals(currentDay + 1, model.getCurrentDay());
    }

    /**
     * Tests {@link GameModelImpl#setHamburgerOnAssembly(HamburgerModel)}
     * and {@link GameModelImpl#getHamburgerOnAssembly()}.
     */
    @Test
    void testSetAndGetHamburgerOnAssembly() {
        final HamburgerModel hamburger = model.getHamburgerOnAssembly();
        hamburger.addIngredient(new IngredientModelImpl(IngredientEnum.BOTTOM_BUN));
        model.setHamburgerOnAssembly(hamburger);

        final HamburgerModel retrieved = model.getHamburgerOnAssembly();
        assertNotNull(retrieved);
        assertEquals(hamburger.getIngredients(), retrieved.getIngredients());
    }

    /**
     * Tests {@link GameModelImpl#setCookedPatties(List)}
     * and {@link GameModelImpl#getCookedPatties()}.
     */
    @Test
    void testSetAndGetCookedPatties() {
        final Patty patty = new PattyImpl();
        model.setCookedPatties(List.of(patty));
        assertEquals(1, model.getCookedPatties().size());
    }

    /**
     * Tests {@link GameModelImpl#setSelectedOrder(OrderModel)}
     * and {@link GameModelImpl#getSelectedOrder()}.
     */
    @Test
    void testSetAndGetSelectedOrder() {
        final OrderModel order = new OrderModelImpl(new HamburgerModelImpl(), 1);
        model.setSelectedOrder(order);

        final OrderModel retrieved = model.getSelectedOrder();
        assertNotNull(retrieved);
        assertEquals(order.getOrderNumber(), retrieved.getOrderNumber());
        assertEquals(order.getHamburger().getIngredients(), retrieved.getHamburger().getIngredients());
    }
}
