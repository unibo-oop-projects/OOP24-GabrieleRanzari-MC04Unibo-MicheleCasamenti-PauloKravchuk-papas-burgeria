package it.unibo.papasburgeria.model.api;

import it.unibo.papasburgeria.model.UpgradeEnum;

/**
 * models how the shop should function.
 */
public interface Shop {
    /**
     * @param upgrade unlocks the given upgrade.
     */
    void unlockUpgrade(UpgradeEnum upgrade);

    /**
     * @param upgrade locks the given upgrade.
     */
    void lockUpgrade(UpgradeEnum upgrade);

    /**
     * unlocks all upgrades.
     */
    void unlockAllUpgrades();

    /**
     * locks all upgrades.
     */
    void lockAllUpgrades();

    /**
     * @param upgrade gets the given upgrade's modifier.
     * @return either the upgrade modifier or zero if locked.
     */
    double getUpgradeModifier(UpgradeEnum upgrade);
}
