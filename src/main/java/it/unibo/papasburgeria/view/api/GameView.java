package it.unibo.papasburgeria.view.api;

/**
 * Acts as view handler and main game starting point.
 */

public interface GameView {

    /**
     * Starts the game's interface and backend.
     */
    void startGame();

    /**
     * Disposes of the game's interface and stops backend.
     */
    void endGame();
}
