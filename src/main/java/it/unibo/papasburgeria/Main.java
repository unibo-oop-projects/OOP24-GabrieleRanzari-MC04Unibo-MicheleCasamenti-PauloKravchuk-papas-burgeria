package it.unibo.papasburgeria;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import it.unibo.papasburgeria.di.MainModule;
import it.unibo.papasburgeria.utils.api.scene.SceneService;

/**
 * Main class.
 */
public final class Main {

    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        // Using production stage to construct services before the game logic starts
        // No need to instantiate the controller here, the injector handles that
        final Injector injector = Guice.createInjector(Stage.PRODUCTION, new MainModule());
        final SceneService sceneService = injector.getInstance(SceneService.class);
        sceneService.switchTo("Menu");

        /*
        Hamburger burger1 = new HamburgerImpl();
        try {
            burger1.addIngredient(new IngredientImpl(IngredientEnum.BOTTOMBUN));
            burger1.addIngredient(new MeatImpl());
            burger1.addIngredient(new IngredientImpl(IngredientEnum.BBQ));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.KETCHUP));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.MAYO));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.TOMATO));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.LETTUCE));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.TOPBUN));
            Logger.debug(burger1);
        } catch (Exception e) {
            Logger.trace(e);
        }
        */
    }
}
