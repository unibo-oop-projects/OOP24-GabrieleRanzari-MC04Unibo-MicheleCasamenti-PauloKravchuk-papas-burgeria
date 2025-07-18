package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SaveService;
import it.unibo.papasburgeria.utils.api.SfxService;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import it.unibo.papasburgeria.utils.impl.SceneServiceImpl;
import it.unibo.papasburgeria.utils.impl.SfxServiceImpl;
import it.unibo.papasburgeria.utils.impl.resource.ResourceServiceImpl;
import it.unibo.papasburgeria.utils.impl.saving.SaveServiceImpl;
import it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl;
import it.unibo.papasburgeria.view.impl.DayChangeViewImpl;
import it.unibo.papasburgeria.view.impl.EvaluateBurgerViewImpl;
import it.unibo.papasburgeria.view.impl.GrillViewImpl;
import it.unibo.papasburgeria.view.impl.MenuViewImpl;
import it.unibo.papasburgeria.view.impl.OrderSelectionViewImpl;
import it.unibo.papasburgeria.view.impl.RegisterViewImpl;
import it.unibo.papasburgeria.view.impl.ShopViewImpl;

/**
 * Guide module responsible for services/utils.
 */
class UtilsModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // sceneName to sceneView bindings
        final MapBinder<SceneType, BaseScene> boundScenes = MapBinder.newMapBinder(binder(), SceneType.class, BaseScene.class);
        boundScenes.addBinding(SceneType.REGISTER).to(RegisterViewImpl.class);
        boundScenes.addBinding(SceneType.BURGER_ASSEMBLY).to(BurgerAssemblyViewImpl.class);
        boundScenes.addBinding(SceneType.GRILL).to(GrillViewImpl.class);
        boundScenes.addBinding(SceneType.MENU).to(MenuViewImpl.class);
        boundScenes.addBinding(SceneType.SHOP).to(ShopViewImpl.class);
        boundScenes.addBinding(SceneType.DAY_CHANGE).to(DayChangeViewImpl.class);
        boundScenes.addBinding(SceneType.ORDER_SELECTION).to(OrderSelectionViewImpl.class);
        boundScenes.addBinding(SceneType.EVALUATE_BURGER).to(EvaluateBurgerViewImpl.class);

        // API to implementation bindings
        bind(SceneService.class).to(SceneServiceImpl.class);
        bind(ResourceService.class).to(ResourceServiceImpl.class);
        bind(SfxService.class).to(SfxServiceImpl.class);
        bind(SaveService.class).to(SaveServiceImpl.class);
    }
}
