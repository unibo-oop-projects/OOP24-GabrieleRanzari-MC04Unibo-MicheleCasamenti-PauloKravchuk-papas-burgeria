package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.view.impl.GameViewImpl;

/**
 * @inheritDoc
 */
public class GameControllerImpl implements GameController {
    private final GameModelImpl model;
    private final GameViewImpl view;

    /**
     * Default constructor, initializes the game model and view.
     */
    public GameControllerImpl() {
        this.model = new GameModelImpl();
        this.view = new GameViewImpl();
    }

    /**
     * Secondary constructor.
     *
     * @param model the GameModel manager.
     * @param view the GameView manager.
     */
    public GameControllerImpl(final GameModelImpl model, final GameViewImpl view) {
        this.model = model;
        this.view = view;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        view.openGUI();
        endGame();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        view.closeGUI();
    }

    /**
     * @return a string containing the model and view state.
     */
    @Override
    public String toString() {
        return "[GameControllerImpl: " + model.toString() + ", " + view.toString() + "]";
    }
}
