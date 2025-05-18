package it.unibo.papasburgeria.view.api;

import javax.swing.JLayeredPane;

/**
 * Manages the GUI.
 */
public interface GameView {

    /**
     * Adds the provided panel to the main frame.
     *
     * @param layeredPane the view's layeredPane to add
     */
    void addViewPanel(JLayeredPane layeredPane);

    /**
     * Shows the main frame, this should be called once all views are
     * constructed and added to the main frame.
     */
    void show();
}
