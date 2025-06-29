package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.UpgradeEnum;

import java.util.Map;

/**
 * Models how the shop should function.
 */
public interface Shop {
    /**
     * Unlocks the selected upgrade.
     *
     * @param upgrade unlocks the given upgrade.
     */
    void unlockUpgrade(UpgradeEnum upgrade);

    /**
     * Locks the selected upgrade.
     *
     * @param upgrade locks the given upgrade.
     */
    void lockUpgrade(UpgradeEnum upgrade);

    /**
     * Unlocks all upgrades.
     */
    void unlockAllUpgrades();

    /**
     * Locks all upgrades.
     */
    void lockAllUpgrades();

    /**
     * Gets the upgrade modifier.
     *
     * @param upgrade gets the given upgrade's modifier.
     * @return either the upgrade modifier or zero if locked.
     */
    double getUpgradeModifier(UpgradeEnum upgrade);

    /**
     * Return whether the upgrade is unlocked or not.
     *
     * @param upgrade the upgrade to check
     * @return true if the upgrade is unlocked, false otherwise
     */
    boolean isUpgradeUnlocked(UpgradeEnum upgrade);

    /**
     * Used to obtain a copy of the current upgrades status.
     *
     * @return map copy instance
     */
    Map<UpgradeEnum, Boolean> getUpgrades();
}
