package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import it.unibo.papasburgeria.utils.api.scene.BaseView;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.impl.SceneServiceImpl;
import it.unibo.papasburgeria.view.impl.MenuViewImpl;

/**
 * Guide module responsible for services/utils.
 */
public class UtilsModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(SceneService.class).to(SceneServiceImpl.class);

        // sceneName to sceneView bindings
        final MapBinder<String, BaseView> boundScenes = MapBinder.newMapBinder(binder(), String.class, BaseView.class);
        boundScenes.addBinding("Menu").to(MenuViewImpl.class);
    }
}
