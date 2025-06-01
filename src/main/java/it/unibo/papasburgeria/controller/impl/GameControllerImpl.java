package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import jakarta.inject.Inject;
import org.tinylog.Logger;

/**
 * @inheritDoc
 */
public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final SceneService sceneService;
    private final ResourceService resourceService;

    /**
     * Constructs the controller with its model and several utility classes like for scene-switching or resource disposing.
     *
     * @param model           the GameModel manager
     * @param sceneService    service required to handle scenes
     * @param resourceService service required to handle resources
     */
    @Inject
    public GameControllerImpl(final GameModel model, final SceneService sceneService, final ResourceService resourceService) {
        this.model = model;
        this.sceneService = sceneService;
        this.resourceService = resourceService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        Logger.info("Game started" + model);
        sceneService.switchTo("Menu");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        resourceService.dispose();
        Logger.info("Game ended");
    }
}
