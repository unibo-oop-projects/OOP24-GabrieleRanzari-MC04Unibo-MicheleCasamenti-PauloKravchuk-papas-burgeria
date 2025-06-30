package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.ShopModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link ShopControllerImpl}.
 */
class ShopControllerImplTest {
    private ShopControllerImpl controller;
    private GameModel model;
    private MockShopModel shop;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        model = new GameModelImpl();
        shop = new MockShopModel();
        controller = new ShopControllerImpl(model, shop);
    }

    /**
     * Tests {@link ShopControllerImpl#isUpgradePurchasable(UpgradeEnum)}.
     */
    @Test
    void testIsUpgradePurchasable() {
        model.setBalance(UpgradeEnum.CUSTOMER_TIP.getCost());
        assertTrue(controller.isUpgradePurchasable(UpgradeEnum.CUSTOMER_TIP));

        model.setBalance(UpgradeEnum.CUSTOMER_TIP.getCost() - 1);
        assertFalse(controller.isUpgradePurchasable(UpgradeEnum.CUSTOMER_TIP));
    }

    /**
     * Tests {@link ShopControllerImpl#buyUpgrade(UpgradeEnum)}.
     */
    @Test
    void testBuyUpgrade() {
        final UpgradeEnum upgrade = UpgradeEnum.CUSTOMER_TIP;
        final int cost = upgrade.getCost();

        shop.lockUpgrade(upgrade);
        model.setBalance(cost);
        controller.buyUpgrade(upgrade);
        assertTrue(shop.isUpgradeUnlocked(upgrade));
        assertEquals(0, model.getBalance());

        assertTrue(shop.isUpgradeUnlocked(upgrade));
        model.setBalance(cost);
        controller.buyUpgrade(upgrade);
        assertTrue(shop.isUpgradeUnlocked(upgrade));
        assertEquals(cost, model.getBalance());

        shop.lockUpgrade(upgrade);
        final int lowBalance = cost - 1;
        model.setBalance(lowBalance);
        controller.buyUpgrade(upgrade);
        assertFalse(shop.isUpgradeUnlocked(upgrade));
        assertEquals(lowBalance, model.getBalance());
    }

    /**
     * Tests {@link ShopControllerImpl#isUpgradeUnlocked(UpgradeEnum)}.
     */
    @Test
    void testIsUpgradeUnlocked() {
        assertFalse(controller.isUpgradeUnlocked(UpgradeEnum.CUSTOMER_TIP));
        shop.unlockUpgrade(UpgradeEnum.CUSTOMER_TIP);
        assertTrue(controller.isUpgradeUnlocked(UpgradeEnum.CUSTOMER_TIP));
    }

    /**
     * Tests {@link ShopControllerImpl#getBalance()}.
     */
    @Test
    void testGetBalance() {
        final int balance = 100;
        model.setBalance(balance);
        assertEquals(balance, controller.getBalance());
    }

    /**
     * Mock {@link ShopModel} class to be used within this test.
     */
    static class MockShopModel implements ShopModel {
        private final Map<UpgradeEnum, Boolean> upgrades = new EnumMap<>(UpgradeEnum.class);

        MockShopModel() {
            for (final UpgradeEnum upgrade : UpgradeEnum.values()) {
                upgrades.put(upgrade, false);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unlockUpgrade(final UpgradeEnum upgrade) {
            upgrades.put(upgrade, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void lockUpgrade(final UpgradeEnum upgrade) {
            upgrades.put(upgrade, false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unlockAllUpgrades() {
            for (final UpgradeEnum upgrade : UpgradeEnum.values()) {
                upgrades.put(upgrade, true);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void lockAllUpgrades() {
            for (final UpgradeEnum upgrade : UpgradeEnum.values()) {
                upgrades.put(upgrade, false);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getUpgradeModifier(final UpgradeEnum upgrade) {
            return upgrades.get(upgrade) ? upgrade.getModifier() : 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isUpgradeUnlocked(final UpgradeEnum upgrade) {
            return upgrades.get(upgrade);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<UpgradeEnum, Boolean> getUpgrades() {
            return new EnumMap<>(upgrades);
        }
    }
}
