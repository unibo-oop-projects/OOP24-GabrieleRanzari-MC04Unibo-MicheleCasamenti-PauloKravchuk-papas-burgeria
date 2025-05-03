package it.unibo.papasburgeria.controller.api;

/**
 * Manages the interactions between the game logic (GameModel) and the GUI (GameView).
 */
public interface GameController {
    /**
     * Starts the game.
     */
    void startGame();

    /**
     * Exits the game.
     */
    void endGame();
}
