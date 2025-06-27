package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.UpgradeEnum;

/**
 * Manages the interaction between the View and the Model for the shop scene.
 */
public interface ShopController {

    /**
     * Returns whether the upgrade is purchasable.
     *
     * @param upgrade the upgrade to check
     * @return true if the upgrade is purchasable, false otherwise
     */
    boolean isUpgradePurchasable(UpgradeEnum upgrade);

    /**
     * Buys the upgrade if possible.
     *
     * @param upgrade the upgrade to check
     * @return true if the upgrade was bought, false otherwise
     */
    boolean buyUpgrade(UpgradeEnum upgrade);

    /**
     * Returns whether the upgrade is unlocked or not.
     *
     * @param upgrade the upgrade to check
     * @return true if the upgrade is unlocked, false otherwise
     */
    boolean isUpgradeUnlocked(UpgradeEnum upgrade);

    /**
     * Returns the balance of the player.
     *
     * @return the balance
     */
    int getBalance();
}
