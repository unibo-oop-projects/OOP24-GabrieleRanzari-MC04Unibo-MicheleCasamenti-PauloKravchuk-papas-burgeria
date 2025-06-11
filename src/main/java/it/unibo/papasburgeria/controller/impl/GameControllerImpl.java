package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.CustomerManager;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.impl.CustomerManagerImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import jakarta.inject.Inject;
import org.tinylog.Logger;

/**
 * Implementation of GameController.
 *
 * <p>
 * See {@link GameController} for interface details.
 */
public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final SceneService sceneService;
    private final ResourceService resourceService;
    private final PantryModel pantryModel;
    private final CustomerManager customerManager;


    /**
     * Constructs the controller with its model and several utility classes like for scene-switching or resource disposing.
     *
     * @param model           the GameModel manager
     * @param sceneService    service required to handle scenes
     * @param resourceService service required to handle resources
     * @param pantryModel     the model that stores witch ingredients are unlocked
     */
    @Inject
    public GameControllerImpl(final GameModel model, final SceneService sceneService, final ResourceService resourceService,
                              final PantryModel pantryModel) {
        this.model = model;
        this.sceneService = sceneService;
        this.resourceService = resourceService;
        this.pantryModel = pantryModel;
        this.customerManager = new CustomerManagerImpl();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        Logger.info("Game started" + model);
        sceneService.switchTo("BurgerAssembly");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        resourceService.dispose();
        Logger.info("Game ended");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        model.nextDay();
        pantryModel.unlockForDay(model.getCurrentDay());
        customerManager.clearLines();
    }
}
