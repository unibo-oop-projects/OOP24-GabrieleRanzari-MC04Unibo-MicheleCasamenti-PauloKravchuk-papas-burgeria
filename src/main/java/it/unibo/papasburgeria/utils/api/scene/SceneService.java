package it.unibo.papasburgeria.utils.api.scene;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Service responsible for the switching of scenes.
 */
public interface SceneService {

    /**
     * Switch to a scene having the provided {@code sceneType} as enum type.
     * Hides the currently-playing scene.
     * Preferred to be used within controllers.
     *
     * @param sceneType Enum of the scene to switch to
     */
    void switchTo(SceneType sceneType);

    /**
     * Used to retrieve the stored scene map within the service.
     *
     * @return copy of the stored map
     */
    Map<SceneType, BaseScene> getScenes();

    /**
     * Used to add a callback to be executed for when a scene changes.
     *
     * @param callback callback to execute
     */
    void onSceneChanged(Consumer<SceneType> callback);
}
