package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import jakarta.inject.Inject;

/**
 * @inheritDoc
 */
public class GameControllerImpl implements GameController {
    private final GameModel model;

    /**
     * Secondary constructor.
     *
     * @param model the GameModel manager.
     */
    @Inject
    public GameControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
    }

    /**
     * @return a string containing the model and view state.
     */
    @Override
    public String toString() {
        return "[GameControllerImpl: " + model.toString() + "]";
    }
}
