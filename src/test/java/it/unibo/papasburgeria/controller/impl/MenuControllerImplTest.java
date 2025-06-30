package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.MenuController;
import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.utils.api.SaveService;
import it.unibo.papasburgeria.utils.impl.saving.SaveInfo;
import it.unibo.papasburgeria.utils.impl.saving.SaveState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link MenuControllerImpl}.
 */
class MenuControllerImplTest {

    private MenuController menuController;
    private MockGameModel gameModel;
    private MockSaveService saveService;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        this.gameModel = new MockGameModel();
        this.saveService = new MockSaveService();
        this.menuController = new MenuControllerImpl(this.saveService, this.gameModel);
    }

    /**
     * Tests {@link MenuController#getCurrentlyUsedSaveIndex()}.
     */
    @Test
    void testGetCurrentlyUsedSaveIndex() {
        final int tempSaveIndex = 2;
        this.gameModel.setCurrentSaveSlot(tempSaveIndex);
        assertEquals(tempSaveIndex, this.menuController.getCurrentlyUsedSaveIndex());
    }

    /**
     * Tests {@link MenuController#getSaves()}.
     */
    @Test
    void testGetSaves() throws IOException {
        final List<SaveState> saveStates = this.saveService.loadAllSlots();
        assertNotNull(saveStates);
        assertTrue(saveStates.isEmpty());

        final int balanceA = 1;
        final int balanceB = 3;
        final int dayA = 2;
        final int dayB = 4;
        final SaveState saveA = new SaveState(balanceA, dayA, new EnumMap<>(UpgradeEnum.class));
        final SaveState saveB = new SaveState(balanceB, dayB, new EnumMap<>(UpgradeEnum.class));
        this.saveService.setSaveStates(List.of(saveA, saveB));

        final List<SaveInfo> saveInfos = this.menuController.getSaves();
        assertNotNull(saveInfos);
        assertEquals(2, saveInfos.size());

        final SaveInfo saveInfoA = saveInfos.get(0);
        assertEquals(0, saveInfoA.slotNumber());
        assertEquals(balanceA, saveInfoA.playerBalance());
        assertEquals(dayA, saveInfoA.gameDay());
        final SaveInfo saveInfoB = saveInfos.get(1);
        assertEquals(1, saveInfoB.slotNumber());
        assertEquals(balanceB, saveInfoB.playerBalance());
        assertEquals(dayB, saveInfoB.gameDay());
    }

    /**
     * Mock {@link SaveService} class to be used within this test.
     */
    static class MockSaveService implements SaveService {

        private List<SaveState> saveStates;

        MockSaveService() {
            this.saveStates = new ArrayList<>();
        }

        /**
         * Helper method to set a SaveState list.
         *
         * @param list provided list
         */
        public void setSaveStates(final List<SaveState> list) {
            this.saveStates = List.copyOf(list);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SaveState loadSlot(final int slotNumber) throws IOException {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * <p>
         * Overridden for this test.
         * </p>
         */
        @Override
        public List<SaveState> loadAllSlots() throws IOException {
            return List.copyOf(this.saveStates);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void saveSlot(final int slotNumber, final SaveState saveState) throws IOException {
        }
    }

    /**
     * Mock {@link GameModel} class to be used within this test.
     */
    static class MockGameModel implements GameModel {

        private int currentSaveSlot;

        /**
         * Constructs this mock class.
         */
        MockGameModel() {
            this.currentSaveSlot = 0;
        }

        /**
         * {@inheritDoc}
         *
         * <p>
         * Overridden for this test.
         * </p>
         */
        @Override
        public int getCurrentSaveSlot() {
            return this.currentSaveSlot;
        }

        /**
         * {@inheritDoc}
         *
         * <p>
         * Overridden for this test.
         * </p>
         */
        @Override
        public void setCurrentSaveSlot(final int currentSaveSlot) {
            this.currentSaveSlot = currentSaveSlot;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void nextDay() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Hamburger getHamburgerOnAssembly() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setHamburgerOnAssembly(final Hamburger hamburger) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Patty[][] getPattiesOnGrill() {
            return new Patty[0][];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setPattiesOnGrill(final Patty[][] patties) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Patty> getCookedPatties() {
            return List.of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setCookedPatties(final List<Patty> patties) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCurrentDay() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setCurrentDay(final int dayNumber) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getBalance() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setBalance(final int amount) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Order getSelectedOrder() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setSelectedOrder(final Order order) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() {
        }
    }
}
