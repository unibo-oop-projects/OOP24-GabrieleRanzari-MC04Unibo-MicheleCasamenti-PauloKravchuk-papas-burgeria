package it.unibo.papasburgeria.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.CustomerModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;

/**
 * Test class for {@link CustomerModelImpl}.
 */
class CustomerModelImplTest {
    private CustomerModel customer;
    private OrderModelImpl order;
    private HamburgerModel hamburger;
    private final int orderNumber = 0;

    /**
     * Called before each test.
     */
    @BeforeAll
    void setUp() {
        this.hamburger = new HamburgerModelImpl(
            List.of(
                new IngredientModelImpl(IngredientEnum.BOTTOM_BUN),
                new IngredientModelImpl(IngredientEnum.PATTY),
                new IngredientModelImpl(IngredientEnum.PICKLE),
                new IngredientModelImpl(IngredientEnum.BBQ),
                new IngredientModelImpl(IngredientEnum.TOP_BUN)
            )
        );
        this.order = new OrderModelImpl(hamburger, orderNumber);
        this.customer = new CustomerModelImpl(order);
    }

    /**
     * Tests {@link CustomerModelImpl#getOrder()}.
     */
    void testGetOrder() {
        assertEquals(order, customer.getOrder());
    }
}
