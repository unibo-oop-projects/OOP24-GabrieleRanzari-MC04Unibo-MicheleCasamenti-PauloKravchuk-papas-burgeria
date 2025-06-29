package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.utils.api.scene.SceneType;

/**
 * Manages the interactions between the game logic (GameModel) and the GUI (GameView).
 */
public interface GameController {
    /**
     * Handles the starting of the game backend.
     */
    void startGame();

    /**
     * Handles the ending of the game backend.
     */
    void endGame();

    /**
     * Called by views to switch to a certain scene, without having to go directly through SceneService,
     * maintaining the MVC aspect of the game.
     *
     * @param sceneType enum of the scene
     */
    void switchToScene(SceneType sceneType);

    /**
     * Advances the current game state to the next day.
     */
    void nextDay();

    /**
     * Processes data saving for current slot number, provides success feedback.
     *
     * @return whether the save is successful or not
     */
    boolean processSave();

    /**
     * Processes data saving for the given slot number, provides success feedback.
     *
     * @param slotNumber slot number
     * @return whether the save is successful or not
     */
    boolean processSave(int slotNumber);

    /**
     * Processes data loading for the given slot number, provides success feedback.
     *
     * @param slotNumber slot number
     * @return whether the loading is successful or not
     */
    boolean processLoad(int slotNumber);
}
