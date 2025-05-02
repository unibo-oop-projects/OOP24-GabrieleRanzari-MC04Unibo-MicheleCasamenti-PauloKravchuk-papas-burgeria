package it.unibo.papasburgeria.utils.api.scene;

/**
 * Base interface for MVC views, which also act as scenes.
 */
public interface BaseView {

    /**
     * Show this scene, avoid using separately from the scene handler.
     */
    void show();

    /**
     * Hides this scene, avoid using separately from the scene handler.
     */
    void hide();
}
