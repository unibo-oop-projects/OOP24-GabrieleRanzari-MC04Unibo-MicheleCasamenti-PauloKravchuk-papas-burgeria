package it.unibo.papasburgeria.controller.api;

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
}
