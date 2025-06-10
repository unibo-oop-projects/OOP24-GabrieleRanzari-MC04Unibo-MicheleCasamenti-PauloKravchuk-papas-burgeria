package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.impl.SceneServiceImpl;
import it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl;
import it.unibo.papasburgeria.utils.impl.resource.ResourceServiceImpl;
import it.unibo.papasburgeria.view.impl.MenuViewImpl;

/**
 * Guide module responsible for services/utils.
 */
class UtilsModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // sceneName to sceneView bindings
        final MapBinder<String, BaseScene> boundScenes = MapBinder.newMapBinder(binder(), String.class, BaseScene.class);
        boundScenes.addBinding("Menu").to(MenuViewImpl.class);
        boundScenes.addBinding("BurgerAssembly").to(BurgerAssemblyViewImpl.class);

        // API to implementation bindings
        bind(SceneService.class).to(SceneServiceImpl.class);
        bind(ResourceService.class).to(ResourceServiceImpl.class);
    }
}
