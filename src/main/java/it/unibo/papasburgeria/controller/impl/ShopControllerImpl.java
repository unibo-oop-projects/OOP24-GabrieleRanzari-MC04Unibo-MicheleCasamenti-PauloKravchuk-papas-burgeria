package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.ShopController;
import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Shop;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class ShopControllerImpl implements ShopController {
    private final GameModel model;
    private final Shop shop;

    /**
     * Default constructor that saves the game model given via injection.
     *
     * @param model the game model
     * @param shop  the shop model
     */
    @Inject
    public ShopControllerImpl(final GameModel model, final Shop shop) {
        this.model = model;
        this.shop = shop;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isUpgradePurchasable(final UpgradeEnum upgrade) {
        return upgrade.getCost() >= 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean buyUpgrade(final UpgradeEnum upgrade) {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isUpgradeUnlocked(final UpgradeEnum upgrade) {
        return shop.isUpgradeUnlocked(upgrade);
    }
}
