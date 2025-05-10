package it.unibo.papasburgeria.utils.api.scene;

/**
 * Service responsible for the switching of scenes.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface SceneService {

    /**
     * Switch to a scene having the provided {@code sceneName} as name.
     * Hides the currently-playing scene.
     *
     * @param sceneName Name of the scene to switch to
     */
    void switchTo(String sceneName);
}
