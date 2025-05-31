package it.unibo.papasburgeria.utils.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementation of the {@link  it.unibo.papasburgeria.utils.api.scene.SceneService  SceneService} interface.
 */
@Singleton
public class SceneServiceImpl implements SceneService {
    private final Map<String, BaseScene> scenes;
    private final List<Consumer<BaseScene>> onSceneChangedCallbacks;
    private BaseScene currentScene;

    /**
     * Initializes the service with a list of scenes to handle.
     *
     * @param scenes Scenes to operate on
     */
    @Inject
    public SceneServiceImpl(final Map<String, BaseScene> scenes) {
        this.scenes = new HashMap<>(scenes);
        this.onSceneChangedCallbacks = new ArrayList<>();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void switchTo(final String sceneName) {
        if (sceneName == null || sceneName.isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty.");
        }

        final BaseScene view = scenes.get(sceneName);
        if (view == null) {
            throw new IllegalArgumentException("Scene " + sceneName + " not found.");
        }

        if (currentScene != null) {
            if (currentScene.equals(view)) {
                return;
            }

            currentScene.hideScene();
        }

        currentScene = view;
        currentScene.showScene();

        this.executeCallbacks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<BaseScene> getScenes() {
        return List.copyOf(scenes.values());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onSceneChanged(final Consumer<BaseScene> callback) {
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
        for (final Consumer<BaseScene> callback : this.onSceneChangedCallbacks) {
            callback.accept(this.currentScene);
        }
    }
}
