package it.unibo.papasburgeria.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import jakarta.inject.Inject;

/**
 * Implementation of GameController.
 *
 * <p>
 * See {@link GameController} for interface details.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final SceneService sceneService;
    private final ResourceService resourceService;
    private final PantryModel pantryModel;
    private final CustomerController customerController;

    /**
     * Constructs the controller with its model and several utility classes like for scene-switching or resource disposing.
     *
     * @param model           the GameModel manager
     * @param sceneService    service required to handle scenes
     * @param resourceService       service required to handle resources
     * @param pantryModel           the model that stores which ingredients are unlocked
     * @param customerController    used to kill customerThread when the game ends
     */
    @Inject
    public GameControllerImpl(final GameModel model, final SceneService sceneService, final ResourceService resourceService,
                              final PantryModel pantryModel, final CustomerController customerController) {
        this.model = model;
        this.sceneService = sceneService;
        this.resourceService = resourceService;
        this.pantryModel = pantryModel;
        this.customerController = customerController;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        sceneService.switchTo(SceneType.MENU);
        customerController.startClientThread();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        customerController.stopClientThread();
        resourceService.dispose();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void switchToScene(final SceneType sceneType) {
        sceneService.switchTo(sceneType);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        model.nextDay();
        customerController.startClientThread();
        pantryModel.unlockForDay(model.getCurrentDay());
        switchToScene(SceneType.DAY_CHANGE);
    }

    /**
     * Returns the current day number.
     *
     * @return the day number
     */
    @Override
    public int getCurrentDayNumber() {
        return model.getCurrentDay();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setDay(final int dayNumber) {
        model.setCurrentDay(dayNumber);
        pantryModel.resetUnlocks();
        pantryModel.unlockForDay(model.getCurrentDay());
    }
}
