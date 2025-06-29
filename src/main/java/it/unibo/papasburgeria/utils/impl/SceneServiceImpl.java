package it.unibo.papasburgeria.utils.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementation of SceneService.
 *
 * <p>
 * See {@link SceneService} for interface details.
 */
@Singleton
public class SceneServiceImpl implements SceneService {
    private final Map<SceneType, BaseScene> scenes;
    private final List<Consumer<SceneType>> onSceneChangedCallbacks;
    private SceneType currentSceneType;

    /**
     * Initializes the service with a list of scenes to handle.
     *
     * @param scenes Scenes to operate on
     */
    @Inject
    public SceneServiceImpl(final Map<SceneType, BaseScene> scenes) {
        this.scenes = new EnumMap<>(scenes);
        this.onSceneChangedCallbacks = new ArrayList<>();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void switchTo(final SceneType sceneType) {
        if (sceneType == null) {
            throw new IllegalArgumentException("Scene name cannot be null or empty.");
        }

        final BaseScene newScene = scenes.get(sceneType);
        if (newScene == null) {
            throw new IllegalArgumentException("Scene for " + sceneType + " not found.");
        }

        if (currentSceneType != null) {
            if (currentSceneType.equals(sceneType)) {
                return;
            }

            // supposedly exists, otherwise it wouldn't have passed the previous checks to be set
            scenes.get(currentSceneType).hideScene();
        }

        currentSceneType = sceneType;
        newScene.showScene();

        this.executeCallbacks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<SceneType, BaseScene> getScenes() {
        return Map.copyOf(scenes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onSceneChanged(final Consumer<SceneType> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null.");
        }

        // this is only useful if they use the same callback instance, but still just in case
        if (this.onSceneChangedCallbacks.contains(callback)) {
            throw new IllegalArgumentException("Callback " + callback + " has already been added.");
        }

        this.onSceneChangedCallbacks.add(callback);
    }

    /**
     * Executes added callbacks.
     */
    private void executeCallbacks() {
        for (final Consumer<SceneType> callback : this.onSceneChangedCallbacks) {
            callback.accept(this.currentSceneType);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "SceneServiceImpl{"
                +
                "scenes="
                + scenes
                +
                ", onSceneChangedCallbacks="
                + onSceneChangedCallbacks
                +
                ", currentSceneType="
                + currentSceneType
                +
                '}';
    }
}
