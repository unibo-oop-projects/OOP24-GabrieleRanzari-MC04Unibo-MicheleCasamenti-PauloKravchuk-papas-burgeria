package it.unibo.papasburgeria.utils.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.utils.api.scene.BaseView;
import it.unibo.papasburgeria.utils.api.scene.SceneService;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link  it.unibo.papasburgeria.utils.api.scene.SceneService  SceneService} interface.
 */
@Singleton
public class SceneServiceImpl implements SceneService {
    private final Map<String, BaseView> scenes;
    private BaseView currentView;

    /**
     * Initializes the service with a list of scenes to handle.
     *
     * @param scenes Scenes to operate on
     */
    @Inject
    public SceneServiceImpl(final Map<String, BaseView> scenes) {
        this.scenes = new HashMap<>(scenes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void switchTo(final String sceneName) {
        if (sceneName == null || sceneName.isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty.");
        }

        final BaseView view = scenes.get(sceneName);
        if (view == null) {
            throw new IllegalArgumentException("Scene " + sceneName + " not found.");
        }

        if (currentView == view) {
            return;
        }

        if (currentView != null) {
            currentView.hide();
        }
        currentView = view;
        currentView.show();
    }
}
