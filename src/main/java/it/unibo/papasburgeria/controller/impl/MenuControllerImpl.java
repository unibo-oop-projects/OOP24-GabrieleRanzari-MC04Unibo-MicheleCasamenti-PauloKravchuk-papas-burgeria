package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.MenuController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Shop;
import it.unibo.papasburgeria.utils.api.SaveService;
import it.unibo.papasburgeria.utils.impl.saving.SaveInfo;
import it.unibo.papasburgeria.utils.impl.saving.SaveState;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of MenuController.
 *
 * <p>
 * See {@link MenuController} for interface details.
 */

public class MenuControllerImpl implements MenuController {
    private final SaveService saveService;
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Controllers intentionally hold references to mutable models."
    )
    private final GameModel gameModel;
    private final Shop shopModel;

    /**
     * Initializes the menu controller.
     *
     * @param saveService service used to save data
     * @param gameModel   game controller
     * @param shopModel   shop data model
     */
    @Inject
    public MenuControllerImpl(
            final SaveService saveService,
            final GameModel gameModel,
            final Shop shopModel
    ) {
        this.saveService = saveService;
        this.gameModel = gameModel;
        this.shopModel = shopModel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean processSave(final int slotNumber) {
        try {
            this.saveService.saveSlot(slotNumber,
                    new SaveState(
                            this.gameModel.getCurrentDay(),
                            this.gameModel.getBalance(),
                            this.shopModel.getUpgrades()
                    )
            );
            return true;
        } catch (final IOException e) {
            Logger.error(e, "Failed to save slot {}", slotNumber);
            return false;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean processLoad(final int slotNumber) {
        try {
            final SaveState saveState = this.saveService.loadSlot(slotNumber);
            this.gameModel.setCurrentDay(saveState.gameDay());
            this.gameModel.setBalance(saveState.playerBalance());
            saveState.upgrades().forEach((upgradeEnum, isUnlocked) -> {
                if (isUnlocked) {
                    this.shopModel.unlockUpgrade(upgradeEnum);
                }
            });
            return true;
        } catch (final IOException e) {
            Logger.error(e, "Failed to load slot {}", slotNumber);
            return false;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<SaveInfo> getSaves() {
        try {
            final List<SaveState> states = this.saveService.loadAllSlots();
            if (states != null && !states.isEmpty()) {
                final List<SaveInfo> transformed = new ArrayList<>();
                for (int i = 0; i < states.size(); i++) {
                    transformed.add(new SaveInfo(i, states.get(i))); // SaveInfo(i, a, b) otherwise SaveInfo(i, -1, -1)
                }

                return transformed;
            }
        } catch (final IOException e) {
            Logger.error(e, "Failed to fetch slots");
        }

        return Collections.emptyList();
    }
}
