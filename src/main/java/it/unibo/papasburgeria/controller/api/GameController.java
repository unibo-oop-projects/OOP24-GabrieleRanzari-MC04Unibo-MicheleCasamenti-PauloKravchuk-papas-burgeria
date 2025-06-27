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
     * Returns the current day number.
     *
     * @return the day number
     */
    int getCurrentDayNumber();

    /**
     * Sets the current day to a new day and updates the PantryModel.
     *
     * @param dayNumber the day number
     */
    void setDay(int dayNumber);
}
