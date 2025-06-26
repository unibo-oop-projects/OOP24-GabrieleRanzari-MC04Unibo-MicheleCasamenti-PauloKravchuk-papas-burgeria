package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.Shop;

import java.util.EnumMap;
import java.util.Map;

import com.google.inject.Singleton;

/**
 * models the shop and keeps track of the unlocked upgrades.
 */
@Singleton
public class ShopImpl implements Shop {
    private final Map<UpgradeEnum, Boolean> upgrades;

    ShopImpl() {
        this.upgrades = new EnumMap<>(UpgradeEnum.class);
        for (final UpgradeEnum currentUpgrade : UpgradeEnum.values()) {
            this.upgrades.put(currentUpgrade, false);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unlockUpgrade(final UpgradeEnum upgrade) {
        this.upgrades.remove(upgrade);
        this.upgrades.put(upgrade, true);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void lockUpgrade(final UpgradeEnum upgrade) {
        this.upgrades.remove(upgrade);
        this.upgrades.put(upgrade, false);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unlockAllUpgrades() {
        for (final UpgradeEnum currentUpgrade : UpgradeEnum.values()) {
            this.upgrades.remove(currentUpgrade);
            this.upgrades.put(currentUpgrade, true);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void lockAllUpgrades() {
        for (final UpgradeEnum currentUpgrade : UpgradeEnum.values()) {
            this.upgrades.remove(currentUpgrade);
            this.upgrades.put(currentUpgrade, false);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getUpgradeModifier(final UpgradeEnum upgrade) {
        if (this.upgrades.get(upgrade)) {
            return upgrade.getModifier();
        } else {
            return 0;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isUpgradeUnlocked(final UpgradeEnum upgrade) {
        return upgrades.get(upgrade);
    }
}
