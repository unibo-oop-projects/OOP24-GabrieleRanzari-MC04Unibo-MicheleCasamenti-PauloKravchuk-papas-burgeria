package it.unibo.papasburgeria.controller.api;

/**
 * Manages the interaction between the View and the Model for the shop scene.
 */
public interface ShopController {

    /**
     * Buys the upgrade if possible.
     *
     * @return true if the upgrade was bought, false otherwise
     */
    boolean buyUpgrade();

    /**
     * Returns whether the upgrade is unlocked.
     *
     * @return true whether the upgrade is unlocked, false otherwise
     */
    boolean isUpgradeUnlocked();
}
