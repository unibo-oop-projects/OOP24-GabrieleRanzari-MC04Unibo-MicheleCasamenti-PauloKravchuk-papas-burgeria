package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.ShopController;
import it.unibo.papasburgeria.model.api.GameModel;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class ShopControllerImpl implements ShopController {
    private final GameModel model;

    /**
     * Default constructor that saves the game model given via injection.
     *
     * @param model the game model
     */
    @Inject
    public ShopControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean buyUpgrade() {

        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isUpgradeUnlocked() {
        return false;
    }
}
