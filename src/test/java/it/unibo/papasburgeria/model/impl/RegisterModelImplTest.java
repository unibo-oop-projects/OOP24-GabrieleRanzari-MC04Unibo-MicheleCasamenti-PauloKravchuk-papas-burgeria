package it.unibo.papasburgeria.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import it.unibo.papasburgeria.model.api.CustomerModel;

/**
 * Test class for {@link RegisterModelImpl}.
 */
class RegisterModelImplTest {
    private RegisterModelImpl registerModel;
    private CustomerModel customer1;
    private CustomerModel customer2;

    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        registerModel = new RegisterModelImpl();
        customer1 = new CustomerModelImpl(new OrderModelImpl(new HamburgerModelImpl(), 0));
        customer2 = new CustomerModelImpl(new OrderModelImpl(new HamburgerModelImpl(), 1));
    }

    /**
     * Tests {@link RegisterModelImpl#addCustomerRegisterLine()}.
     */
    @Test
    public void testAddCustomerRegisterLine() {
        registerModel.addCustomerRegisterLine(customer1);
        final List<CustomerModel> line = registerModel.getRegisterLine();
        assertEquals(1, line.size());
        assertTrue(line.contains(customer1));
        assertTrue(customer1.isInRegisterLine());
    }

    /**
     * Tests {@link RegisterModelImpl#removeCustomerRegisterLine()}.
     */
    @Test
    public void testRemoveCustomerRegisterLine() {
        registerModel.addCustomerRegisterLine(customer1);
        registerModel.removeCustomerRegisterLine(customer1);
        final List<CustomerModel> line = registerModel.getRegisterLine();
        assertTrue(line.isEmpty());
        assertFalse(customer1.isInRegisterLine());
    }

    /**
     * Tests {@link RegisterModelImpl#addCustomerRegisterLine()}.
     */
    @Test
    public void testAddCustomerWaitLine() {
        registerModel.addCustomerWaitLine(customer2);
        final List<CustomerModel> line = registerModel.getWaitLine();
        assertEquals(1, line.size());
        assertTrue(line.contains(customer2));
        assertTrue(customer2.isInWaitLine());
    }

    /**
     * Tests {@link RegisterModelImpl#removeCustomerWaitLine()}.
     */
    @Test
    public void testRemoveCustomerWaitLine() {
        registerModel.addCustomerWaitLine(customer2);
        registerModel.removeCustomerWaitLine(customer2);
        final List<CustomerModel> line = registerModel.getWaitLine();
        assertTrue(line.isEmpty());
        assertFalse(customer2.isInWaitLine());
    }

    /**
     * Tests {@link RegisterModelImpl#clearRegisterLine()}.
     */
    @Test
    public void testClearRegisterLine() {
        registerModel.addCustomerRegisterLine(customer1);
        registerModel.addCustomerRegisterLine(customer2);
        registerModel.clearRegisterLine();
        final List<CustomerModel> line = registerModel.getRegisterLine();
        assertTrue(line.isEmpty());
        assertFalse(customer1.isInRegisterLine());
        assertFalse(customer2.isInRegisterLine());
    }

    /**
     * Tests {@link RegisterModelImpl#clearWaitLine()}.
     */
    @Test
    public void testClearWaitLine() {
        registerModel.addCustomerWaitLine(customer1);
        registerModel.addCustomerWaitLine(customer2);
        registerModel.clearWaitLine();
        final List<CustomerModel> line = registerModel.getWaitLine();
        assertTrue(line.isEmpty());
        assertFalse(customer1.isInWaitLine());
        assertFalse(customer2.isInWaitLine());
    }

    /**
     * Tests {@link RegisterModelImpl#clearLines()}.
     */
    @Test
    public void testClearLines() {
        registerModel.addCustomerRegisterLine(customer1);
        registerModel.addCustomerWaitLine(customer2);
        registerModel.clearLines();
        assertTrue(registerModel.getRegisterLine().isEmpty());
        assertTrue(registerModel.getWaitLine().isEmpty());
        assertFalse(customer1.isInRegisterLine());
        assertFalse(customer2.isInWaitLine());
    }

    /**
     * Tests {@link RegisterModelImpl#startCustomerThread()}, 
     * {@link RegisterModelImpl#killCustomerThread()} and
     * {@link RegisterModelImpl#isCustomerThreadStatus()}.
     *
     * @throws InterruptedException for testing the customer thread.
     */
    @Test
    public void testCustomerThreadStartAndKill() throws InterruptedException {
        final int delay = 50;
        registerModel.startCustomerThread(100, 1, List.of());
        Thread.sleep(delay);  // Let the thread start
        assertTrue(registerModel.isCustomerThreadStatus());

        registerModel.killCustomerThread();
        Thread.sleep(delay);  // Give time for thread to be interrupted
        assertFalse(registerModel.isCustomerThreadStatus());
    }
}
