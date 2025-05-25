package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.utils.impl.SceneServiceImpl;
import jakarta.inject.Inject;
import org.tinylog.Logger;

/**
 * @inheritDoc
 */
public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final SceneServiceImpl sceneService;

    /**
     * Secondary constructor.
     *
     * @param model        the GameModel manager
     * @param sceneService service requires to handle scenes
     */
    @Inject
    public GameControllerImpl(final GameModel model, final SceneServiceImpl sceneService) {
        this.model = model;
        this.sceneService = sceneService;
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
        Logger.info("Game ended");
    }
}
