package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.ShopController;
import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.ShopModel;

/**
 * Manages the interaction between the View and the Model for the shop scene.
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The models are injected and shared intentionally")
public class ShopControllerImpl implements ShopController {
    private final GameModel model;
    private final ShopModel shop;

    /**
     * Default constructor that saves the models given via injection.
     *
     * @param model the game model
     * @param shop  the shop model
     */
    @Inject
    public ShopControllerImpl(final GameModel model, final ShopModel shop) {
        this.model = model;
        this.shop = shop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUpgradePurchasable(final UpgradeEnum upgrade) {
        return upgrade.getCost() <= model.getBalance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buyUpgrade(final UpgradeEnum upgrade) {
        if (!isUpgradeUnlocked(upgrade) && isUpgradePurchasable(upgrade)) {
            shop.unlockUpgrade(upgrade);
            model.setBalance(model.getBalance() - upgrade.getCost());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUpgradeUnlocked(final UpgradeEnum upgrade) {
        return shop.isUpgradeUnlocked(upgrade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBalance() {
        return model.getBalance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ShopControllerImpl{"
                + "model=" + model
                + ", shop=" + shop
                + '}';
    }
}
