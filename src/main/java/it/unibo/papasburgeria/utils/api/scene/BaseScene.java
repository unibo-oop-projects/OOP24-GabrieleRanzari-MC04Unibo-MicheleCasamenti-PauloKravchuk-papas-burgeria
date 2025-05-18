package it.unibo.papasburgeria.utils.api.scene;

/**
 * Specifies base scene functionalities.
 */
public interface BaseScene {

    /**
     * Show this scene, using separately from the scene handler is possible but not advised.
     */
    void showScene();

    /**
     * Hides this scene, using separately from the scene handler is possible but not advised.
     */
    void hideScene();
}
