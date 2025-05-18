package it.unibo.papasburgeria.utils.api.scene;

import java.util.List;
import java.util.function.Consumer;

/**
 * Service responsible for the switching of scenes.
 */
public interface SceneService {

    /**
     * Switch to a scene having the provided {@code sceneName} as name.
     * Hides the currently-playing scene.
     * Preferred to be used within controllers.
     *
     * @param sceneName Name of the scene to switch to
     */
    void switchTo(String sceneName);

    /**
     * Used to retrieve the stored scenes within the service.
     *
     * @return collection of stored scene instances
     */
    List<BaseScene> getScenes();

    /**
     * Used to add a callback to be executed for when a scene changes.
     *
     * @param callback callback to execute
     */
    void onSceneChanged(Consumer<BaseScene> callback);
}
