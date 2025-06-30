package it.unibo.papasburgeria.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.ShopModel;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SaveService;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import it.unibo.papasburgeria.utils.impl.saving.SaveState;
import jakarta.inject.Inject;

import java.io.IOException;

/**
 * Implementation of GameController.
 *
 * <p>
 * See {@link GameController} for interface details.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class GameControllerImpl implements GameController {
    private final GameModel gameModel;
    private final PantryModel pantryModel;
    private final ShopModel shopModel;
    private final SceneService sceneService;
    private final ResourceService resourceService;
    private final SaveService saveService;
    private final CustomerController customerController;

    /**
     * Constructs the controller with its model and several utility classes like for scene-switching or resource disposing.
     *
     * @param gameModel          the GameModel manager
     * @param pantryModel        the model that stores which ingredients are unlocked
     * @param shopModel          the model that stores which upgrades are unlocked
     * @param sceneService       service required to handle scenes
     * @param resourceService    service required to handle resources
     * @param saveService        service responsible for saving slot data
     * @param customerController used to kill customerThread when the game ends
     */
    @Inject
    public GameControllerImpl(
            final GameModel gameModel,
            final PantryModel pantryModel,
            final ShopModel shopModel,
            final SceneService sceneService,
            final ResourceService resourceService,
            final SaveService saveService,
            final CustomerController customerController
    ) {
        this.gameModel = gameModel;
        this.pantryModel = pantryModel;
        this.shopModel = shopModel;
        this.sceneService = sceneService;
        this.resourceService = resourceService;
        this.saveService = saveService;
        this.customerController = customerController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        sceneService.switchTo(SceneType.MENU);
        customerController.startClientThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        customerController.stopClientThread();
        resourceService.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToScene(final SceneType sceneType) {
        sceneService.switchTo(sceneType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextDay() {
        gameModel.nextDay();
        customerController.startClientThread();
        pantryModel.unlockForDay(gameModel.getCurrentDay());
        this.processSave();
        switchToScene(SceneType.DAY_CHANGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean processSave() {
        final int slotIndex = this.gameModel.getCurrentSaveSlot();
        return slotIndex >= 0 && this.processSave(slotIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean processSave(final int slotNumber) {
        try {
            this.saveService.saveSlot(slotNumber,
                    new SaveState(
                            this.gameModel.getBalance(),
                            this.gameModel.getCurrentDay(),
                            this.shopModel.getUpgrades()
                    )
            );
            return true;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean processLoad(final int slotNumber) {
        try {
            final SaveState saveState = this.saveService.loadSlot(slotNumber);
            this.gameModel.reset();
            this.gameModel.setCurrentSaveSlot(slotNumber);
            this.gameModel.setCurrentDay(saveState.gameDay());
            this.pantryModel.resetUnlocks();
            this.pantryModel.unlockForDay(this.gameModel.getCurrentDay());
            this.gameModel.setBalance(saveState.playerBalance());
            saveState.upgrades().forEach((upgradeEnum, isUnlocked) -> {
                if (isUnlocked) {
                    this.shopModel.unlockUpgrade(upgradeEnum);
                }
            });
            this.customerController.startClientThread(); // is controller -> controller the only way?
            return true;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GameControllerImpl{"
                +
                "gameModel="
                + gameModel
                +
                ", pantryModel="
                + pantryModel
                +
                ", shopModel="
                + shopModel
                +
                ", sceneService="
                + sceneService
                +
                ", resourceService="
                + resourceService
                +
                ", saveService="
                + saveService
                +
                ", customerController="
                + customerController
                +
                '}';
    }
}
