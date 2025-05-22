package it.unibo.papasburgeria.view.api;

/**
 * Manages the GUI.
 */

@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface GameView {

    /**
     * Shows the main frame, this should be called once all views are
     * constructed and added to the main frame.
     */
    void show();
}
